<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table title="Pais" :data="allPaises" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
              <q-input v-model="props.row.codigo" dense autofocus counter></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="designacao" :props="props">
            {{ props.row.designacao }}
            <q-popup-edit v-model="props.row.designacao" title="Update designacao">
              <q-input v-model="props.row.designacao" dense autofocus></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nacionalidade" :props="props">
            <div class="text-pre-wrap">{{ props.row.nacionalidade }}</div>
            <q-popup-edit v-model="props.row.nacionalidade">
              <q-input v-model="props.row.nacionalidade" dense autofocus></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/pais/${props.row.id}`">
                <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps/>
              </router-link>
              <q-btn round glossy icon="edit" color="blue" @click.stop="editaPais(props.row)" size=sm no-caps/>
              <q-btn round glossy icon="delete_forever" color="red" @click.stop="removePais(props.row)" size=sm
                     no-caps/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :show_dialog="show_dialog"
                      :listErrors="listErrors"
                      :codigo.sync="pais.codigo"
                      :designacao.sync="pais.designacao"
                      :nacionalidade.sync="pais.nacionalidade"
                      :submitting="submitting"
                      :close="close"
                      :createPais="createPais"
                      :removePais="removePais"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Pais from "src/store/models/pais/pais";

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Pais',
  data() {
    return {
      listErrors: [],
      pais_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      pais: {
        codigo: '',
        designacao: '',
        nacionalidade: ''
      },
      columns: [
        {
          name: 'codigo',
          required: true,
          label: 'Codigo',
          align: 'left',
          field: row => row.codigo,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'designacao',
          align: 'left',
          label: 'Designacao',
          field: row => row.designacao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'nacionalidade',
          align: 'left',
          label: 'Nacionalidade',
          field: row => row.nacionalidade,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'actions', label: 'Movimento', field: 'actions'}
      ],
      data: []
    }
  },
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    // return store.dispatch('pais/getAllPais', currentRoute.params.id)

    return this.listAllPais()
  },
  mounted() {
    this.listAllPais()
  },
  components: {
    'create-edit-form': require('components/pais/createEditForm.vue').default
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
  metaInfo: {},
  computed: {
    allPaises() {
      return Pais.query().all()
    }
  },
  methods: {
    createPais() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      if (this.editedIndex > -1) {
        Pais.api().patch("/pais/" + this.pais.id, this.pais).then(resp => {
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
            message: 'A informação foi actualizada com successo!! [ ' + this.pais.designacao + ' ]'
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
        Pais.api().post("/pais", this.pais).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi inserida com successo! [ ' + this.pais.designacao + ' ]'
          })
          this.close()
        }).catch(error => {
          const arrayErrors = JSON.parse(error.request.response)
          if (arrayErrors.total == null) {
            this.listErrors.push(arrayErrors.message)
          } else {
            arrayErrors._embedded.errors.forEach(element => {
              this.listErrors.push(element.message)
            })
          }
        })
      }
    },
    close() {
      this.show_dialog = false
      this.pais = {}
      this.props = this.pais
      this.listErrors = []
      setTimeout(() => {
        // this.pais = Object.assign({}, null)
        this.editedIndex = -1
      }, 300)
    },
    removePais(pais) {
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
          message: 'A informação foi Removida com successo! [ ' + pais.designacao + ' ]'
        })
        Pais.api().delete("/pais/" + pais.id)
      })
    },
    editaPais(pais) {
      this.editedIndex = 0
      this.pais = Object.assign({}, pais)
      this.show_dialog = true
    },
    listAllPais() {
      Pais.api().get('/pais').then(resp => {
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
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.pais.paises.map(row =>
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
      const status = exportFile('pais_list.csv', content, 'text/csv')
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
