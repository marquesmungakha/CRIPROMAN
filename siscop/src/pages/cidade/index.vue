<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Cidade" :data="allCidades" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="provincia" :props="props">
              <div class="text-pre-wrap">{{ props.row.provincia.designacao }}</div>
              <q-popup-edit v-model="props.row.provincia.designacao">
                <q-input v-model="props.row.provincia.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="distrito" :props="props">
              <div class="text-pre-wrap">{{ props.row.distrito.designacao }}</div>
              <q-popup-edit v-model="props.row.distrito.designacao">
                <q-input v-model="props.row.distrito.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/cidade/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaCidade(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeCidade(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="cidade.codigo"
                    :designacao.sync="cidade.designacao"
                    :provincia.sync="provincia"
                    :distrito.sync="distrito"
                    :provincias="allProvincias"
                    :distritos="allDistritosFromProvincia"
                    :submitting="submitting"
                    :close="close"
                    :createCidade="createCidade"
                    :removeCidade="removeCidade"/>
  </q-page>
</template>

<script>
import { exportFile, QSpinnerBall } from 'quasar'
import Cidade from 'src/store/models/cidade/cidade'
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'
import Pais from 'src/store/models/pais/pais'

function wrapCsvValue (val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Cidade',
  data () {
    return {
      listErrors: [],
      options: [],
      cidade_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      cidade: {
        codigo: '',
        designacao: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'codigo', required: true, label: 'Código', align: 'left', field: row => row.codigo, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designação', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'provincia', align: 'left', label: 'Próvincia', field: row => row.provincia, format: val => `${val}`, sortable: true },
        { name: 'distrito', align: 'left', label: 'Distrito', field: row => row.distrito, format: val => `${val}`, sortable: true },
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
    return this.getAllCidade()
  },
  mounted () {
   this.getAllPais()
   this.getAllCidade()
   this.getAllProvincia()
   this.getAllDistrito()
  },
  components: {
    'create-edit-form': require('components/cidade/createEditForm.vue').default
  },
  created() {
  //  this.getAllPais()
  //  this.getAllCidade()
  //  this.getAllProvincia()
  //  this.getAllDistrito()
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
    allProvincias () {
      return Provincia.query().with('pais').all()
    },
    allDistritosFromProvincia () {
      return Distrito.query().with('provincia').where('provincia_id', this.provincia.id).get()
    },
    allDistritos () {
      return Distrito.query().all()
    },
    allCidades () {
      return Cidade.query().with('provincia').with('distrito').all()
    }
  },
  methods: {
    createCidade () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.cidade.provincia = this.provincia
      this.distrito.provincia =  this.provincia
      this.cidade.distrito = this.distrito
      this.cidade.provincia_id = this.provincia.id
      this.cidade.distrito_id = this.distrito.id
      console.log(this.cidade)
      if (this.editedIndex > -1) {
      Cidade.api().patch("/cidade/"+this.cidade.id,this.cidade).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.cidade.designacao + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log('erro update - ' + error)
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
       Cidade.api().post("/cidade/",this.cidade).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.cidade.designacao + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log('Erro no code ' + error)
          if (error.request.status !== 0) {
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
      this.getAllCidade()
      this.getAllPais()
      this.getAllProvincia()
      this.getAllDistrito()
      this.show_dialog = false
      this.cidade = {}
      this.provincia = {}
      this.distrito = {}
      this.listErrors = []
      this.props = this.cidade
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeCidade (cidade) {
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
          message: 'A informação foi Removida com successo! [ ' + cidade.designacao + ' ]'
        })
       Cidade.api().delete("/cidade/"+cidade.id)
      })
    },
    editaCidade (cidade) {
      this.editedIndex = 0
      this.cidade = Object.assign({}, cidade)
      this.provincia = Provincia.query().find(cidade.provincia.id)
      this.distrito = Distrito.query().find(cidade.distrito.id)
      this.show_dialog = true
    },
     getAllCidade(){
      Cidade.api().get('/cidade?offset=0&max=1000000').then(resp => {
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
      Pais.api().get('/pais?offset=0&max=1000000')
    },
    getAllProvincia () {
      return Provincia.api().get('/provincia?offset=0&max=1000000', { 
        persistOptions: {
          insert: ['pais']
        }
    })
    },
    getAllDistrito () {
     Distrito.api().get('/distrito?offset=0&max=1000000')
    },
    filterFn (val, update, abort) {
      const stringOptions = this.allProvincias
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(provincia => provincia)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(provincia => provincia)
            .filter(provincia => {
              return provincia &&
                   provincia.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.cidade.provincia = val
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.cidade.cidades.map(row =>
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
      const status = exportFile('cidade_list.csv', content, 'text/csv')
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
