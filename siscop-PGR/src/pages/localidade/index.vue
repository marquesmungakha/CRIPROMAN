<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allLocalidades" :filter="filter" binary-state-sort row-key="name"
             title="Localidade">

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
          <!-- <q-td key="codigo" :props="props">
            {{ props.row.codigo }}
            <q-popup-edit v-model="props.row.codigo">
              <q-input v-model="props.row.codigo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td> -->
          <q-td key="designacao" :props="props">
            {{ props.row.designacao }}
            <q-popup-edit v-model="props.row.designacao" title="Update designacao">
              <q-input v-model="props.row.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            <div class="text-pre-wrap">{{ props.row.distrito.provincia.designacao }}</div>
            <q-popup-edit v-model="props.row.distrito.provincia.designacao">
              <q-input v-model="props.row.distrito.provincia.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="distrito" :props="props">
            <div class="text-pre-wrap">{{ props.row.distrito.designacao }}</div>
            <q-popup-edit v-model="props.row.distrito.designacao">
              <q-input v-model="props.row.distrito.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="postoAdministrativo" :props="props">
            <div class="text-pre-wrap">{{ props.row.postoAdministrativo.designacao }}</div>
            <q-popup-edit v-model="props.row.postoAdministrativo.designacao">
              <q-input v-model="props.row.postoAdministrativo.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/localidade/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaLocalidade(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click="removeLocalidade(props.row)">
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
                      :createLocalidade="createLocalidade"
                      :designacao.sync="localidade.designacao"
                      :distrito.sync="distrito"
                      :distritos.sync="allDistritosFromProvincia"
                      :listErrors="listErrors"
                      :postoAdministrativo.sync="postoAdministrativo"
                      :postoAdministrativos.sync="allPostoAdministrativosFromDistrito"
                      :provincia.sync="provincia"
                      :provincias.sync="allProvincias"
                      :removeLocalidade="removeLocalidade"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'
import Pais from 'src/store/models/pais/pais'
import PostoAdministrativo from 'src/store/models/postoAdministrativo/postoAdministrativo'
import Localidade from 'src/store/models/localidade/localidade'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Localidade',
  data() {
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
        // {
        //   name: 'codigo',
        //   required: true,
        //   label: 'Código',
        //   align: 'left',
        //   field: row => row.codigo,
        //   format: val => `${val}`,
        //   sortable: true
        // },
        {
          name: 'designacao',
          align: 'left',
          label: 'Designação',
          field: row => row.designacao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'provincia',
          align: 'left',
          label: 'Província',
          field: row => row.provincia,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'distrito',
          align: 'left',
          label: 'Distrito',
          field: row => row.distrito,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'postoAdministrativo',
          align: 'left',
          label: 'Posto Administrativo',
          field: row => row.postoAdministrativo,
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
    return this.getAllLocalidade()
  },
  mounted() {
        if(this.localDistrito !== null && this.localDistrito !== undefined && 
        this.localProvincia !== null &&  this.localProvincia !== undefined && 
        this.localPostoAdministrativo !== null &&  this.localPostoAdministrativo !== undefined ){
        this.distrito = this.localDistrito
        this.provincia = this.localProvincia
        this.postoAdministrativo = this.localPostoAdministrativo
    }
   
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
  metaInfo: {},
  computed: {
    allProvincias() {
      return Provincia.query().with('pais').all()
    },
    allDistritosFromProvincia() {
      return Distrito.query().with('provincia').where('provincia_id', this.provincia.id).get()
    },
    allDistritos() {
      return Distrito.query().with('provincia').all()
    },
    allPostoAdministrativos() {
      return PostoAdministrativo.query().with('distrito.provincia').all()
    },
    allPostoAdministrativosFromDistrito() {
      return PostoAdministrativo.query().with('distrito').where('distrito_id', this.distrito.id).get()
    },
    allLocalidades() {

        if(this.$route.params.id !== undefined){
            return Localidade.query().with('distrito.provincia').with('postoAdministrativo').where('postoAdministrativo_id',Number(this.$route.params.id)).get()
        }
        else{
            return Localidade.query().with('distrito.provincia').with('postoAdministrativo').all()
        }

    }
  },
  methods: {
    createLocalidade() {
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
        Localidade.api().patch("/localidade/" + this.localidade.id, this.localidade).then(resp => {
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
        Localidade.api().post("/localidade/", this.localidade).then(resp => {
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
    close() {
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
    removeLocalidade(localidade) {
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
        Localidade.api().delete("/localidade/" + localidade.id)
      })
    },
    editaLocalidade(localidade) {
      this.editedIndex = 0
      this.localidade = Object.assign({}, localidade)
      this.postoAdministrativo = PostoAdministrativo.query().find(localidade.postoAdministrativo_id)
      this.distrito = Distrito.query().find(localidade.distrito.id)
      this.provincia = Provincia.query().find(this.distrito.provincia_id)
      this.show_dialog = true
    },
  getAllLocalidade(offset) {
          if(offset >= 0){
          Localidade.api().get("/localidade?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllLocalidade(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllPais(offset) {
      if(offset >= 0){
          Pais.api().get("/pais?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllPais(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllProvincia(offset) {
      if(offset >= 0){
          Provincia.api().get("/provincia?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllProvincia(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllDistrito(offset) {
      if(offset >= 0){
          Distrito.api().get("/distrito?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllDistrito(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllPostoAdministrativo(offset) {
      if(offset >= 0){
          PostoAdministrativo.api().get("/postoAdministrativo?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllPostoAdministrativo(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    filterFn(val, update, abort) {
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
    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    setModel(val) {
      this.localidade.distrito = val
    },
    exportTable() {
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
  }, props:
    [
      'localDistrito',
      'localProvincia',
      'localPostoAdministrativo'
    ]
}
</script>
