<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="PostoAdministrativo" :data="allPostoAdministrativos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
              <div class="text-pre-wrap">{{ props.row.distrito.provincia.designacao }}</div>
              <q-popup-edit v-model="props.row.distrito.provincia.designacao">
                <q-input v-model="props.row.distrito.provincia.designacao" dense autofocus ></q-input>
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
              <router-link :to="`/postoAdministrativo/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaPostoAdministrativo(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removePostoAdministrativo(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="postoAdministrativo.codigo"
                    :designacao.sync="postoAdministrativo.designacao"
                    :distrito.sync="distrito"
                    :provincia.sync="provincia"
                    :provincias.sync="allProvincias"
                    :distritos="allDistritosFromProvincia"
                    :submitting="submitting"
                    :close="close"
                    :createPostoAdministrativo="createPostoAdministrativo"
                    :removePostoAdministrativo="removePostoAdministrativo"/>
  </q-page>
</template>

<script>
import { exportFile, QSpinnerBall } from 'quasar'
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'
import Pais from 'src/store/models/pais/pais'
import PostoAdministrativo from 'src/store/models/postoAdministrativo/postoAdministrativo'

function wrapCsvValue (val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'PostoAdministrativo',
  data () {
    return {
      listErrors: [],
      options: [],
      postoAdministrativo_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      postoAdministrativo: {
        codigo: '',
        designacao: '',
        distrito: {}
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'codigo', required: true, label: 'Código', align: 'left', field: row => row.codigo, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designação', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'provincia', align: 'left', label: 'Província', field: row => row.provincia, format: val => `${val}`, sortable: true },
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
    return this.getAllPostoAdministrativo()
  },
  mounted () {
   this.getAllPais()
   this.getAllPostoAdministrativo()
   this.getAllProvincia()
   this.getAllDistrito()
  },
  components: {
    'create-edit-form': require('components/postoAdministrativo/createEditForm.vue').default
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
    allProvincias () {
      return Provincia.query().with('pais').all()
    },
    allDistritosFromProvincia () {
      return Distrito.query().with('provincia').where('provincia_id', this.provincia.id).get()
    },
    allDistritos () {
      return Distrito.query().all()
    },
    allPostoAdministrativos () {
      return PostoAdministrativo.query().with('distrito.provincia').all()
    }
  },
  methods: {
    createPostoAdministrativo () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.postoAdministrativo.provincia =  this.provincia
      this.postoAdministrativo.distrito = this.distrito
      this.postoAdministrativo.provincia_id = this.provincia.id
      this.postoAdministrativo.distrito_id = this.distrito.id
      if (this.editedIndex > -1) {
        PostoAdministrativo.api().patch("/postoAdministrativo/"+this.postoAdministrativo.id,this.postoAdministrativo).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.postoAdministrativo.designacao + ' ]'
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
       PostoAdministrativo.api().post("/postoAdministrativo/",this.postoAdministrativo).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.postoAdministrativo.designacao + ' ]'
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
     this.getAllPostoAdministrativo()
      this.getAllPais()
      this.getAllProvincia()
      this.getAllDistrito()
      this.show_dialog = false
      this.postoAdministrativo = {}
      this.props = this.postoAdministrativo
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removePostoAdministrativo (postoAdministrativo) {
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
          message: 'A informação foi Removida com successo! [ ' + postoAdministrativo.designacao + ' ]'
        })
      PostoAdministrativo.api().delete("/postoAdministrativo/"+postoAdministrativo.id)
      })
    },
    editaPostoAdministrativo (postoAdministrativo) {
      this.editedIndex = 0
      this.postoAdministrativo = Object.assign({}, postoAdministrativo)
      this.distrito = Distrito.query().find(postoAdministrativo.distrito.id)
      this.provincia = Provincia.query().find(this.distrito.provincia_id)
      this.show_dialog = true
    },
    getAllPostoAdministrativo(){
      PostoAdministrativo.api().get('/postoAdministrativo?offset=0&max=1000000').then(resp => {
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
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.postoAdministrativo.postoAdministrativos.map(row =>
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
      const status = exportFile('postoAdministrativo_list.csv', content, 'text/csv')
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
