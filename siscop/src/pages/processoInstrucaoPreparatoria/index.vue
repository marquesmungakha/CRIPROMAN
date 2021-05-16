<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Processo" :data="allProcessos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="numeroProcesso" :props="props">
              {{ props.row.numeroProcesso }}
              <q-popup-edit v-model="props.row.numeroProcesso">
                <q-input v-model="props.row.numeroProcesso" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="dataEntrada" :props="props">
              {{ props.row.dataEntrada }}
              <q-popup-edit v-model="props.row.dataEntrada">
                <q-input v-model="props.row.dataEntrada" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
             <q-td key="proveniencia" :props="props">
              {{ props.row.proveniencia }}
              <q-popup-edit v-model="props.row.proveniencia">
                <q-input v-model="props.row.proveniencia" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="formaProcesso" :props="props">
              <div class="text-pre-wrap">{{ props.row.formaProcesso.designacao }}</div>
              <q-popup-edit v-model="props.row.formaProcesso.designacao">
                <q-input v-model="props.row.formaProcesso.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="classeJudicial" :props="props">
            <div class="text-pre-wrap">{{ props.row.classeJudicial.designacao }}</div>
            <q-popup-edit v-model="props.row.classeJudicial.designacao">
              <q-input v-model="props.row.classeJudicial.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="accoesCrimes" :props="props">
            <div class="text-pre-wrap">{{ props.row.accoesCrimes.designacao }}</div>
            <q-popup-edit v-model="props.row.accoesCrimes.designacao">
              <q-input v-model="props.row.accoesCrimes.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
            <q-td key="magistrado" :props="props">
              <div class="text-pre-wrap">{{ props.row.magistrado.numero  }} - {{ props.row.magistrado.nome  }} {{ props.row.magistrado.apelido  }}</div>
              <q-popup-edit v-model="props.row.inspector">
                <q-input v-model="props.row.magistrado" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>

          <q-td key="estado" :props="props">
          <q-chip :color="(props.row.pareceresProcesso.length > 0 )?'green': (props.row.pareceresProcesso.length === 0 || props.row.pareceresProcesso.length === null || props.row.pareceresProcesso.length === undefined ? 'red':'grey')"
              text-color="white" dense class="text-weight-bolder" square
              style="width: 120px" :icon="(props.row.pareceresProcesso.length > 0)?'published_with_changes':(props.row.pareceresProcesso.length == null || props.row.pareceresProcesso.length == undefined ?'unpublished':'do_disturb_on')">
              <span v-if="props.row.pareceresProcesso.length > 0 ">Despachado</span>
              <span v-else>Pendente</span>
            </q-chip>
            <!--q-popup-edit v-model="props.row.activo" title="Update estado">
              <q-input v-model="props.row.activo" autofocus dense></q-input>
            </q-popup-edit-->
          </q-td>

            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/processoInstrucaoPreparatoria/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaProcesso(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeProcesso(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numeroProcesso.sync="processoInstrucaoPreparatoria.numeroProcesso"
                    :proveniencia.sync="processoInstrucaoPreparatoria.proveniencia"
                    :historial.sync="processoInstrucaoPreparatoria.historial"
                    :formaProcesso.sync="formaProcesso"
                    :classeJudicial.sync="classeJudicial"
                    :classeJudicials.sync="allclasseJudicials"
                    :crime.sync="crime"
                    :crimes.sync="allCrimesFromClasseJudicial"
                    :dataEntrada.sync="processoInstrucaoPreparatoria.dataEntrada"
                    :magistrado.sync="magistrado"
                    :anexo.sync="processoInstrucaoPreparatoria.anexo"
                    :formaProcessos.sync="allFormaProcessos"
                    :magistrados.sync="allMagistrados"
                    :orgao.sync="orgao"
                    :orgaos.sync="allOrgaos"
                    :submitting="submitting"
                    :close="close"
                    :createProcesso="createProcesso"
                    :removeProcesso="removeProcesso"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import FormaProcesso from 'src/store/models/formaProcesso/formaProcesso'
import Magistrado from 'src/store/models/magistrado/magistrado'
import Orgao from 'src/store/models/orgao/orgao'
import ProcessoInstrucaoPreparatoria from 'src/store/models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoria'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
import Crime from 'src/store/models/crime/crime'
import Pais from 'src/store/models/pais/pais'
import TipoOrgao from 'src/store/models/tipoOrgao/tipoOrgao'
import Provincia from 'src/store/models/provincia/provincia'

function wrapCsvValue (val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Processo',
  data () {
    return {
      listErrors: [],
      options: [],
      processo_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      processoInstrucaoPreparatoria: {
        numeroProcesso: '',
        proveniencia: '',
        dataEntrada: '',
        anexo: [],
        historial: '',
        formaProcesso: {},
        magistrado: {},
        classeJudicial: {},
        accoesCrimes: {},
        orgao: {}
      },
      crime: {
        designacao: ''
      },
      formaProcesso: {
        designacao: ''
      },
      classeJudicial: {
        designacao: ''
      },
      magistrado: {
        numero: ''
      },
      orgao: {
        sigla: '',
        designacao: ''
      },
      columns: [
        { name: 'numeroProcesso', align: 'left', label: 'Número do Processo', field: row => row.numeroProcesso, format: val => `${val}`, sortable: true },
        { name: 'dataEntrada', align: 'left', label: 'Data de Entrada', field: row => row.dataEntrada, format: val => `${val}`, sortable: true },
        { name: 'proveniencia', align: 'left', label: 'Proveniência', field: row => row.proveniencia, format: val => `${val}`, sortable: true },
        { name: 'formaProcesso', align: 'left', label: 'Forma de Processo', field: row => row.formaProcesso, format: val => `${val}`, sortable: true },
        { name: 'classeJudicial', align: 'left', label: 'Famílias Diletivas', field: row => row.classeJudicial, format: val => `${val}`, sortable: true },
        { name: 'accoesCrimes', align: 'left', label: 'Crimes', field: row => row.accoesCrimes, format: val => `${val}`, sortable: true },
        { name: 'magistrado', align: 'left', label: 'Magistrado', field: row => row.magistrado, format: val => `${val}`, sortable: true },
        { name: "estado", align: "left", label: "Estado", field: row => row.estado, sortable: true  },
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
    return this.getAllProcesso()
  },
  mounted () {
    let offset = 0
      this.getAllProcesso(offset)
      this.getAllFormaProcesso(offset)
      this.getAllJurisdicao(offset)
      this.getAllCrime(offset)
      this.getAllMagistrado(offset)
      this.getAllOrgao(offset)
      this.getAllPais(offset)
      this.getAllProvincia(offset)
      this.getAllTipoOrgao(offset)
  },
  components: {
    'create-edit-form': require('components/processoInstrucaoPreparatoria/createEditForm.vue').default
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
    allFormaProcessos () {
      return FormaProcesso.query().where('activo', true).all()
    },
    allMagistrados () {
      return Magistrado.query().all()
    },
    allCrimes() {
      return Crime.query().all()
    },
    allCrimesFromClasseJudicial() {
      return Crime.query().where('classeJudicial_id', this.classeJudicial.id).get()
    },
    allclasseJudicials() {
      return ClasseJudicial.query().all()
    },
    allOrgaos () {
      return Orgao.query().all()
    },
    allProcessos () {
      return ProcessoInstrucaoPreparatoria.query().with('magistrado').with('formaProcesso').with('classeJudicial').with('accoesCrimes').with('pareceresProcesso').all()
    }
  },
  methods: {
    createProcesso () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.processoInstrucaoPreparatoria.formaProcesso_id = this.formaProcesso.id
      this.processoInstrucaoPreparatoria.magistrado_id = this.magistrado.id
      this.processoInstrucaoPreparatoria.classeJudicial = this.classeJudicial
      this.crime.classeJudicial = this.classeJudicial
      this.processoInstrucaoPreparatoria.accoesCrimes = this.crime
      this.processoInstrucaoPreparatoria.accoesCrimes_id = this.crime.id
      this.processoInstrucaoPreparatoria.formaProcesso = this.formaProcesso
      this.processoInstrucaoPreparatoria.magistrado = this.magistrado
      this.processoInstrucaoPreparatoria.orgao = Orgao.query().with('provincia.pais').with('tipoOrgao').find(this.orgao.id)
      this.processoInstrucaoPreparatoria.orgao_id = this.orgao.id //Orgao.query().first().id
      this.processoInstrucaoPreparatoria.anexo = null
      if (this.editedIndex > -1) {
         ProcessoInstrucaoPreparatoria.api().patch("/processoInstrucaoPreparatoria/" + this.processoInstrucaoPreparatoria.id, this.processoInstrucaoPreparatoria).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.processoInstrucaoPreparatoria.designacao + ' ]'
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
        ProcessoInstrucaoPreparatoria.api().post("/processoInstrucaoPreparatoria/", this.processoInstrucaoPreparatoria).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.processoInstrucaoPreparatoria.designacao + ' ]'
          })
          this.close()
           this.$router.push({path: '/processoInstrucaoPreparatoria/' + resp.response.data.id })
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
     let offset = 0
      this.getAllProcesso(offset)
      this.getAllFormaProcesso(offset)
      this.getAllJurisdicao(offset)
      this.getAllCrime(offset)
      this.getAllMagistrado(offset)
      this.getAllOrgao(offset)
      this.getAllPais(offset)
      this.getAllProvincia(offset)
      this.getAllTipoOrgao(offset)
      this.listErrors = {}
      this.show_dialog = false
      this.processoInstrucaoPreparatoria = {}
      this.props = this.processoInstrucaoPreparatoria
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeProcesso (processoInstrucaoPreparatoria) {
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
          message: 'A informação foi Removida com successo! [ ' + processoInstrucaoPreparatoria.designacao + ' ]'
        })
        ProcessoInstrucaoPreparatoria.api().delete("/processoInstrucaoPreparatoria/" + this.processoInstrucaoPreparatoria.id)
      })
    },
    editaProcesso (processoInstrucaoPreparatoria) {
      this.editedIndex = 0
      this.processoInstrucaoPreparatoria = Object.assign({}, processoInstrucaoPreparatoria)
      this.formaProcesso = FormaProcesso.query().find(processoInstrucaoPreparatoria.formaProcesso_id)
      this.magistrado = Magistrado.query().find(processoInstrucaoPreparatoria.magistrado_id)
      this.crime = Crime.query().find(processoInstrucaoPreparatoria.accoesCrimes_id)
      this.classeJudicial =  ClasseJudicial.query().find(processoInstrucaoPreparatoria.classeJudicial_id)
      this.show_dialog = true
    },
    getAllProcesso(offset) {
      if(offset >= 0) {
         ProcessoInstrucaoPreparatoria.api().get("/processoInstrucaoPreparatoria?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this. getAllProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
    getAllFormaProcesso(offset) {
       if(offset >= 0) {
         FormaProcesso.api().get("/formaProcesso?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this. getAllFormaProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
    getAllJurisdicao(offset) {
       if(offset >= 0) {
         ClasseJudicial.api().get("/classeJudicial?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this. getAllJurisdicao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
    getAllCrime(offset) {
       if(offset >= 0) {
         Crime.api().get("/crime?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this. getAllCrime(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
    getAllMagistrado(offset) {
      if(offset >= 0){
           Magistrado.api().get("/magistrado?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this. getAllMagistrado(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
   getAllOrgao(offset) {
        if(offset >= 0){
           Orgao.api().get("/orgao?offset="+offset+"&max=100").then(resp => {
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
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.processoInstrucaoPreparatoria.processos.map(row =>
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
      const status = exportFile('processo_list.csv', content, 'text/csv')
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
