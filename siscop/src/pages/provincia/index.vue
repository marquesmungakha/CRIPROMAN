<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Provincia" :data="allProvincias" :columns="columns" row-key="name" binary-state-sort :filter="filter">

      <template v-slot:top-right>
      <q-input v-if="show_filter" filled borderless dense debounce="300" v-model="filter" placeholder="Pesquisa">
              <template v-slot:append>
                <q-icon name="search"/>
              </template>
            </q-input>

      <div class="q-pa-md q-gutter-sm">
      <q-btn class="q-ml-sm" icon="filter_list" @click="show_filter=!show_filter" flat/>
        <q-btn outline rounded color="primary" label="Adicionar Novo" @click="show_dialog = true" no-caps/>
        <q-btn rounded color="primary" icon-right="archive" label="Imprimir em Excel" no-caps @click="exportTable"/>
      </div>
      </template>
      <template v-slot:body="props">
          <q-tr :props="props">
            <q-td key="codigo" :props="props">
              {{ props.row.codigo }}
              <q-popup-edit v-model="props.row.codigo">
                <q-input v-model="props.row.codigo" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="designacao" :props="props">
              {{ props.row.designacao }}
              <q-popup-edit v-model="props.row.designacao" title="Update designacao">
                <q-input v-model="props.row.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="pais" :props="props">
              <div class="text-pre-wrap">{{ props.row.pais.designacao }}</div>
              <q-popup-edit v-model="props.row.pais.designacao">
                <q-input v-model="props.row.pais.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/provincia/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaProvincia(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeProvincia(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="provincia.codigo"
                    :designacao.sync="provincia.designacao"
                    :pais.sync="pais"
                    :paises="allPaises"
                    :submitting="submitting"
                    :close="close"
                    :createProvincia="createProvincia"
                    :removeProvincia="removeProvincia"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Pais from "src/store/models/pais/pais";
import Provincia from "src/store/models/provincia/provincia";


function wrapCsvValue (val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Provincia',
  data () {
    return {
      listErrors: [],
      options: [],
      provincia_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      provincia: {
        codigo: '',
        designacao: '',
        pais: {}
      },
      pais: {
        codigo: '',
        designacao: '',
        nacionalidade: ''
      },
      columns: [
        { name: 'codigo', required: true, label: 'Codigo', align: 'left', field: row => row.codigo, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designacao', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'pais', align: 'left', label: 'Pais', field: row => row.pais, format: val => `${val}`, sortable: true },
        { name: 'actions', label: 'Movimento', field: 'actions' }
      ],
      data: []
    }
  },
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
  // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return this.getAllProvincia()
  },
  mounted() {
    this.getAllPais()
    this.getAllProvincia()
  },
  components: {
    'create-edit-form': require('components/provincia/createEditForm.vue').default
  },
  created() {
    this.$q.loading.show({
      message: "Carregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })

    setTimeout(() => {
      this.$q.loading.hide()
    }, 600)

  },
  metaInfo: {
  },
  computed: {
    allPaises() {
      return Pais.query().all()
    },
    allProvincias () {
      return Provincia.query().with('pais').all()
    }
  },
  methods: {
    createProvincia () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.provincia.pais = this.pais
      this.provincia.pais_id = this.pais.id
      console.log(this.provincia)
      if (this.editedIndex > -1) {
        Provincia.api().patch("/provincia/"+this.provincia.id,this.provincia).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.provincia.designacao + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log(error)
          if (error.request.response != null) {
            const arrayErrors = JSON.parse(error.request.response)
            if (arrayErrors.total == null) {
              this.listErrors.push(arrayErrors.message)
            } else {
              arrayErrors._embedded.errors.forEach(element => {
                this.listErrors.push(element.message)
              })
            }
            console.log(this.listErrors)
          }
        })
      } else {
        Provincia.api().post("/provincia/",this.provincia).then(resp => {
          console.log(resp)
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi inserida com successo! [ ' + this.provincia.designacao + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log(error)
          if (error.request.response != null) {
            const arrayErrors = JSON.parse(error.request.response)
            if (arrayErrors.total == null) {
              this.listErrors.push(arrayErrors.message)
            } else {
              arrayErrors._embedded.errors.forEach(element => {
                this.listErrors.push(element.message)
              })
            }
            console.log(this.listErrors)
          }
        })
      }
    },
    close () {
      this.getAllProvincia()
      this.show_dialog = false
      this.provincia = {}
      this.listErrors = []
      this.props = this.provincia
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeProvincia (provincia) {
      this.$q.dialog({
        title: 'Confirmação',
        message: 'Tem certeza que pretende remover?',
        cancel: true,
        persistent: true
      }).onOk(() => {
        this.$q.notify({
          type: 'warning',
          color: 'primary',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 2000,
          position: 'bottom',
          classes: 'glossy',
          progress: true,
          message: 'A informação foi Removida com successo! [ ' + provincia.designacao + ' ]'
        })
        Provincia.api().delete("/provincia/"+provincia.id)
      })
    },
    editaProvincia (provincia) {
      this.editedIndex = 0
      this.provincia = Object.assign({}, provincia)
      this.pais = Pais.query().find(provincia.pais.id)
      this.show_dialog = true
    },
    getAllProvincia(){
      Provincia.api().get('/provincia?offset=0&max=1000000', {
        persistOptions: {
          insert: ['pais']
        }
      }).then(resp => {
        console.log(resp)
      }).catch(error => {
        console.log(error)
        if (error.request.response != null) {
          const arrayErrors = JSON.parse(error.request.response)
          if (arrayErrors.total == null) {
            this.listErrors.push(arrayErrors.message)
          } else {
            arrayErrors._embedded.errors.forEach(element => {
              this.listErrors.push(element.message)
            })
          }
          console.log(this.listErrors)
        }
      })
    },
    getAllPais () {
        return Pais.api().get("/pais?offset=0&max=1000000")
    },
    filterFn (val, update, abort) {
      const stringOptions = Pais.query().all()
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(pais => pais)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(pais => pais)
            .filter(pais => {
              return pais &&
                   pais.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.provincia.pais = val
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.provincia.provincias.map(row =>
            this.columns
              .map(col =>
                wrapCsvValue(
                  typeof col.field === 'function'
                    ? col.field(row)
                    : row[col.field === undefined ? col.name : col.field],
                  col.format
                )
              )
              .join(',')
          )
        )
        .join('\r\n')
      const status = exportFile('provincia_list.csv', content, 'text/csv')
      if (status !== true) {
        this.$q.notify({
          message: 'O navegador recusou o download...',
          color: 'negative',
          icon: 'warning'
        })
      }
    }
  }
}
</script>
