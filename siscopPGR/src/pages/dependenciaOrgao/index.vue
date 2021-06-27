<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allOrgaos" :filter="filter" binary-state-sort row-key="name" title="Sub-Orgao">

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
          <q-td key="sigla" :props="props">
            {{ props.row.sigla }}
            <q-popup-edit v-model="props.row.sigla">
              <q-input v-model="props.row.sigla" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="designacao" :props="props">
            {{ props.row.designacao }}
            <q-popup-edit v-model="props.row.designacao" title="Update designacao">
              <q-input v-model="props.row.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nivel" :props="props">
            {{ props.row.nivel }}
            <q-popup-edit v-model="props.row.nivel" title="Update nivel">
              <q-input v-model="props.row.nivel" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoOrgao" :props="props">
            <div class="text-pre-wrap">{{ props.row.tipoOrgao.designacao }}</div>
            <q-popup-edit v-model="props.row.tipoOrgao.designacao">
              <q-input v-model="props.row.tipoOrgao.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            <div class="text-pre-wrap">{{ props.row.provincia.designacao }}</div>
            <q-popup-edit v-model="props.row.provincia.designacao">
              <q-input v-model="props.row.provincia.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="distrito" :props="props">
            <div class="text-pre-wrap">{{ props.row.distrito.designaca }}</div>
            <q-popup-edit v-model="props.row.distrito.designacao">
              <q-input v-model="props.row.distrito.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/orgao/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaOrgao(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeOrgao(props.row)">
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
                      :createOrgao="createOrgao"
                      :designacao.sync="dependenciaOrgao.designacao"
                      :distrito.sync="distrito"
                      :distritos="allDistritosFromProvincia"
                      :endereco.sync="dependenciaOrgao.endereco"
                      :listErrors="listErrors"
                      :nivel.sync="dependenciaOrgao.nivel"
                      :provincia.sync="provincia"
                      :provincias="allProvincias"
                      :removeOrgao="removeOrgao"
                      :show_dialog="show_dialog"
                      :sigla.sync="dependenciaOrgao.sigla"
                      :submitting="submitting"
                      :tipoOrgao.sync="tipoOrgao"
                      :tipoOrgaos="allTipoOrgaos"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import TipoOrgao from 'src/store/models/tipoOrgao/tipoOrgao'
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'
import Orgao from 'src/store/models/orgao/orgao'
import DependenciaOrgao from 'src/store/models/dependenciaOrgao/dependenciaOrgao'
import Pais from 'src/store/models/pais/pais'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Orgao',
  data() {
    return {
      listErrors: [],
      options: [],
      orgao_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      dependenciaOrgao: {
        sigla: '',
        designacao: '',
        nivel: 0,
        endereco: '',
        provincia: {},
        distrito: {}
      },
      provincia: {
        codigo: '',
        designacao: '',
        pais:{}
      },
      distrito: {
        codigo: '',
        designacao: '',
        provincia:{}
      },
      tipoOrgao: {
        codigo: '',
        designacao: ''
      },
      columns: [
        {
          name: 'sigla',
          required: true,
          label: 'Sigla',
          align: 'left',
          field: row => row.sigla,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'designacao',
          align: 'left',
          label: 'Designação',
          field: row => row.designacao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'nivel',
          align: 'left',
          label: 'Nível',
          field: row => row.nivel,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'tipoOrgao',
          align: 'left',
          label: 'Tipo de Orgão',
          field: row => row.tipoOrgao,
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
    return this.getAllOrgao()
  },
    props: [
    'orgao'
  ],
  mounted() {
    let offset = 0
    this.getAllPais(offset)
    this.getAllOrgao(offset)
    this.getAllTipoOrgao(offset)
    this.getAllProvincia(offset)
    this.getAllDistrito(offset)
  },
  components: {
    'create-edit-form': require('components/orgao/createEditForm.vue').default
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
    allTipoOrgaos() {
      return TipoOrgao.query().all()
    },
    allPais() {
      return Pais.query().all()
    },
    allProvincias() {
      return Provincia.query().with('pais').all()
    },
    allDistritos() {
      return Distrito.query().with('provincia.pais').all()
    },
    allDistritosFromProvincia() {
      return Distrito.query().with('provincia.pais').where('provincia_id', this.provincia.id).get()
    },
     allOrgaos() {
      return DependenciaOrgao.query().with('tipoOrgao').with('provincia.pais').with('distrito.provincia.pais').where('orgao_id',this.orgao.id).all()
    }
  },
  methods: {
    createOrgao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
     //  this.provincia.pais = this.orgao.provincia.pais
      this.dependenciaOrgao.provincia_id = this.provincia.id
      this.dependenciaOrgao.provincia = this.provincia
      this.dependenciaOrgao.tipoOrgao_id = this.tipoOrgao.id
      this.dependenciaOrgao.tipoOrgao = this.tipoOrgao
      this.dependenciaOrgao.orgao_id = this.orgao.id
      this.dependenciaOrgao.orgao = this.orgao
      this.dependenciaOrgao.distrito_id = this.distrito.id
      this.distrito.provincia = this.provincia
      this.dependenciaOrgao.distrito = this.distrito

      if (this.editedIndex > -1) {
        DependenciaOrgao.api().patch("/dependenciaOrgao/" + this.dependenciaOrgao.id, this.dependenciaOrgao).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.dependenciaOrgao.designacao + ' ]'
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
        DependenciaOrgao.api().post("/dependenciaOrgao/", this.dependenciaOrgao).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.dependenciaOrgao.designacao + ' ]'
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
      this.getAllPais(offset)
      this.getAllOrgao(offset)
      this.getAllTipoOrgao(offset)
      this.getAllProvincia(offset)
      this.getAllDistrito(offset)
      this.show_dialog = false
     // this.orgao = {}
      this.provincia = {}
      this.distrito = {}
      this.tipoOrgao = {}
      this.props = this.dependenciaOrgao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeOrgao(orgao) {
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
          message: 'A informação foi Removida com successo! [ ' + orgao.designacao + ' ]'
        })
        Orgao.api().delete("/dependenciaOrgao/" + orgao.id)
        Orgao.delete(orgao.id)
      })
    },
    editaOrgao(dependenciaOrgao) {
      console.log(dependenciaOrgao)
      this.editedIndex = 0
      this.dependenciaOrgao = Object.assign({}, dependenciaOrgao)
      this.tipoOrgao = TipoOrgao.query().find(dependenciaOrgao.tipoOrgao.id)
      this.provincia = Provincia.query().with('pais').find(dependenciaOrgao.provincia.id)
      this.distrito = Distrito.query().with('provincia.pais').find(dependenciaOrgao.distrito.id)
      this.show_dialog = true
    },
    filterFn(val, update, abort) {
      const stringOptions = this.allTipoOrgaos
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(tipoOrgao => tipoOrgao)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(tipoOrgao => tipoOrgao)
            .filter(tipoOrgao => {
              return tipoOrgao &&
                tipoOrgao.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    getAllOrgao(offset) {
        if(offset >= 0){
           DependenciaOrgao.api().get("/dependenciaOrgao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this. getAllOrgao(offset), 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
    getAllTipoOrgao(offset) {
        if(offset >= 0){
          TipoOrgao.api().get("/tipoOrgao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllTipoOrgao(offset), 2)

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
    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.allOrgaos.map(row =>
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
      const status = exportFile('orgao_list.csv', content, 'text/csv')
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
