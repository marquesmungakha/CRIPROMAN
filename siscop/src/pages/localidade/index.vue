<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Localidade" :data="allLocalidades" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
              <div class="text-pre-wrap">{{  props.row.distrito.provincia.designacao }}</div>
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
            <q-td key="postoAdministrativo" :props="props">
              <div class="text-pre-wrap">{{ props.row.postoAdministrativo.designacao }}</div>
              <q-popup-edit v-model="props.row.postoAdministrativo.designacao">
                <q-input v-model="props.row.postoAdministrativo.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/localidade/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaLocalidade(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeLocalidade(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localidade.codigo"
                    :designacao.sync="localidade.designacao"
                    :postoAdministrativo.sync="postoAdministrativo"
                    :distrito.sync="distrito"
                    :provincia.sync="provincia"
                    :provincias.sync="allProvincias"
                    :distritos.sync="allDistritosFromProvincia"
                    :postoAdministrativos.sync="allPostoAdministrativosFromDistrito"
                    :submitting="submitting"
                    :close="close"
                    :createLocalidade="createLocalidade"
                    :removeLocalidade="removeLocalidade"/>
  </q-page>
</template>

<script>
import { exportFile, QSpinnerBall } from 'quasar'
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'
import Pais from 'src/store/models/pais/pais'
import PostoAdministrativo from 'src/store/models/postoAdministrativo/postoAdministrativo'
import Localidade from 'src/store/models/localidade/localidade'

function wrapCsvValue (val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Localidade',
  data () {
    return {
      listErrors: [],
      options: [],
      localidade_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      localidade: {
        codigo: '',
        designacao: '',
        distrito: {},
        postoAdministrativo: {}
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      postoAdministrativo: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'codigo', required: true, label: 'Código', align: 'left', field: row => row.codigo, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designação', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'provincia', align: 'left', label: 'Província', field: row => row.provincia, format: val => `${val}`, sortable: true },
        { name: 'distrito', align: 'left', label: 'Distrito', field: row => row.distrito, format: val => `${val}`, sortable: true },
        { name: 'postoAdministrativo', align: 'left', label: 'Posto Administrativo', field: row => row.postoAdministrativo, format: val => `${val}`, sortable: true },
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
    return this.getAllLocalidade()
  },
  mounted () {
   this.getAllPais()
   this.getAllPostoAdministrativo()
   this.getAllProvincia()
   this.getAllDistrito()
   this.getAllLocalidade()
  },
  components: {
    'create-edit-form': require('components/localidade/createEditForm.vue').default
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
      return Distrito.query().with('provincia').all()
    },
    allPostoAdministrativos () {
      return PostoAdministrativo.query().with('distrito.provincia').all()
    },
    allPostoAdministrativosFromDistrito () {
      return PostoAdministrativo.query().with('distrito').where('distrito_id', this.distrito.id).get()
    },
    allLocalidades () {
      return Localidade.query().with('distrito.provincia').with('postoAdministrativo').all()
    }
  },
  methods: {
    createLocalidade () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localidade.distrito = this.distrito
      this.localidade.postoAdministrativo = this.postoAdministrativo
      this.localidade.postoAdministrativo_id = this.postoAdministrativo.id
      this.localidade.distrito_id = this.distrito.id
      if (this.editedIndex > -1) {
      Localidade.api().patch("/localidade/"+this.localidade.id,this.localidade).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.localidade.designacao + ' ]'
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
       Localidade.api().post("/localidade/",this.localidade).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.localidade.designacao + ' ]'
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
      this.getAllLocalidade()
      this.getAllPostoAdministrativo()
      this.getAllPais()
      this.getAllProvincia()
      this.getAllDistrito()
      this.show_dialog = false
      this.localidade = {}
      this.distrito = {}
      this.provincia = {}
      this.postoAdministrativo = {}
      this.props = this.localidade
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeLocalidade (localidade) {
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
          message: 'A informação foi Removida com successo! [ ' + localidade.designacao + ' ]'
        })
       Localidade.api().delete("/localidade/"+localidade.id)
      })
    },
    editaLocalidade (localidade) {
      this.editedIndex = 0
      this.localidade = Object.assign({}, localidade)
      this.postoAdministrativo = PostoAdministrativo.query().find(localidade.postoAdministrativo_id)
      this.distrito = Distrito.query().find(localidade.distrito.id)
      this.provincia = Provincia.query().find(this.distrito.provincia_id)
      this.show_dialog = true
    },
      getAllLocalidade(){
      Localidade.api().get('/localidade?offset=0&max=1000000').then(resp => {
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
    getAllPostoAdministrativo () {
       PostoAdministrativo.api().get('/postoAdministrativo?offset=0&max=1000000')
    },
    filterFn (val, update, abort) {
      const stringOptions = this.allDistritos
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(distrito => distrito)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(distrito => distrito)
            .filter(distrito => {
              return distrito &&
                   distrito.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.localidade.distrito = val
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.localidade.localidades.map(row =>
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
      const status = exportFile('localidade_list.csv', content, 'text/csv')
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
