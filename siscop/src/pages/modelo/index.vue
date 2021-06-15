<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allModelos" :filter="filter" binary-state-sort row-key="name" title="Modelo">

      <template v-slot:top-right>
        <q-input v-if="show_filter" v-model="filter" borderless debounce="300" dense filled placeholder="Pesquisa">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>

        <div class="q-pa-md q-gutter-sm">
          <q-btn class="q-ml-sm" flat icon="filter_list" @click="show_filter=!show_filter"/>
          <q-btn color="primary" label="Adicionar Novo" no-caps outline rounded @click="show_dialog = true"/>
          <q-btn color="primary" icon-right="archive" label="Imprimir em Excel" no-caps rounded @click="exportTable"/>
        </div>
      </template>
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td key="codigo" :props="props">
            {{ props.row.codigo }}
            <q-popup-edit v-model="props.row.codigo">
              <q-input v-model="props.row.codigo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="designacao" :props="props">
            {{ props.row.designacao }}
            <q-popup-edit v-model="props.row.designacao" title="Update designacao">
              <q-input v-model="props.row.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="marca" :props="props">
            <div class="text-pre-wrap">{{ props.row.marca.designacao }}</div>
            <q-popup-edit v-model="props.row.marca.designacao">
              <q-input v-model="props.row.marca.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/modelo/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaModelo(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeModelo(props.row)">
                <q-tooltip content-class="bg-red text-white shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Remover
                </q-tooltip>
                </q-btn>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :close="close"
                      :codigo.sync="modelo.codigo"
                      :createModelo="createModelo"
                      :designacao.sync="modelo.designacao"
                      :listErrors="listErrors"
                      :marca.sync="marca"
                      :marcas="allMarcas"
                      :removeModelo="removeModelo"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Marca from 'src/store/models/marca/marca'
import Modelo from 'src/store/models/modelo/modelo'
import TipoMeio from 'src/store/models/tipoMeio/tipoMeio'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Modelo',
  data() {
    return {
      listErrors: [],
      options: [],
      modelo_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      modelo: {
        codigo: '',
        designacao: '',
        marca: {}
      },
      marca: {
        codigo: '',
        designacao: ''
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
          name: 'marca',
          align: 'left',
          label: 'Marca',
          field: row => row.marca.id,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'actions', align: 'left',label: 'Ações', field: 'actions'}
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
    return this.getAllModelo()
  },
  mounted() {
    this.marca = this.marcaLocal
    let offset = 0 
    this.getAllMarca(offset)
    this.getAllModelo(offset)
    this.getAllTipoMeio(offset)
  },
  components: {
    'create-edit-form': require('components/modelo/createEditForm.vue').default
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
    allMarcas() {
      return Marca.query().all()
    },
    allModelos() {

      if(this.$route.params.id !== undefined){
            return Modelo.query().with('marca').where('marca_id',Number(this.$route.params.id)).get()
        }
        else{
            return Modelo.query().with('marca').all()
        }
    }
  },
  methods: {
    createModelo() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.modelo.marca = this.marca
      this.modelo.marca.tipoMeio = TipoMeio.query().find(this.marca.tipoMeio_id)
      this.modelo.marca_id = this.marca.id
      if (this.editedIndex > -1) {
        Modelo.api().patch("/modelo/" + this.modelo.id, this.modelo).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.modelo.designacao + ' ]'
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
        Modelo.api().post("/modelo/", this.modelo).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.modelo.designacao + ' ]'
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
    close() {
      let offset = 0 
    this.getAllMarca(offset)
    this.getAllModelo(offset)
    this.getAllTipoMeio(offset)
      this.show_dialog = false
      this.modelo = {}
      this.props = this.modelo
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeModelo(modelo) {
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
          message: 'A informação foi Removida com successo! [ ' + modelo.designacao + ' ]'
        })
        Modelo.api().delete("/modelo/" + modelo.id)
      })
    },
    editaModelo(modelo) {
      this.editedIndex = 0
      this.modelo = Object.assign({}, modelo)
      this.marca = Marca.query().find(modelo.marca_id)
      this.show_dialog = true
    },
    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    setModel(val) {
      this.modelo.marca = val
    },
     getAllMarca(offset) {
       if(offset >=0){
      Marca.api().get("/marca?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllMarca(offset), 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllTipoMeio(offset) {
       if(offset >=0){
      TipoMeio.api().get("/tipoMeio?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllTipoMeio(offset), 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllModelo(offset) {
      if(offset >=0){
      Modelo.api().get("/modelo?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllModelo(offset), 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.modelo.modelos.map(row =>
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
      const status = exportFile('modelo_list.csv', content, 'text/csv')
      if (status !== true) {
        this.$q.notify({
          message: 'O navegador recusou o download...',
          color: 'negative',
          icon: 'warning'
        })
      }
    }
  },
  props:[
    'marcaLocal'
  ]
}
</script>
