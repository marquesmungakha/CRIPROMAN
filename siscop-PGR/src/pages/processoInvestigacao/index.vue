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
            <q-td key="numeroAuto" :props="props">
              <div class="text-pre-wrap">{{ props.row.numeroAuto.numero}}</div>
              <q-popup-edit v-model="props.row.numeroAuto.numero">
                <q-input v-model="props.row.numeroAuto.numero" dense autofocus ></q-input>
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
           <q-td key="inspector" :props="props">
              <div class="text-pre-wrap">{{  props.row.inspector.numero  }} - {{  props.row.inspector.nome  }} {{ props.row.inspector.apelido  }}</div>
              <q-popup-edit v-model="props.row.inspector">
                <q-input v-model="props.row.inspector" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="magistrado" :props="props">
              <div class="text-pre-wrap">{{ props.row.magistrado.numero  }} - {{ props.row.magistrado.nome  }} {{ props.row.magistrado.apelido  }}</div>
              <q-popup-edit v-model="props.row.inspector">
                <q-input v-model="props.row.magistrado" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="estado" :props="props">
            <q-chip :color="(props.row.despachos.length !== 0)?'green':(props.row.despachos.length === 0?'red':'grey')"
              text-color="white" dense class="text-weight-bolder" square
              style="width: 130px" :icon="(props.row.despachos.length !== 0)?'published_with_changes':(props.row.despachos.length === 0?'unpublished':'do_disturb_on')">
              <span v-if="props.row.despachos.length !== 0"> {{props.row.despachos[0].tipoParecer.codigo}}</span>
              <span v-else>Sem despacho</span>
            </q-chip>
            <q-popup-edit v-model="props.row.activo" title="Update estado">
              <q-input v-model="props.row.activo" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/processoInvestigacao/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaProcesso(props.row)" size=sm no-caps >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn round glossy icon="delete_forever" color="red" @click="removeProcesso(props.row)" size=sm no-caps>
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
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numeroProcesso.sync="processoInvestigacao.numeroProcesso"
                    :numeroAuto.sync="numeroAuto"
                    :proveniencia.sync="processoInvestigacao.proveniencia"
                    :formaProcesso.sync="formaProcesso"
                    :dataEntrada.sync="processoInvestigacao.dataEntrada"
                    :magistrado.sync="magistrado"
                    :inspector.sync="inspector"
                    :anexo.sync="processoInvestigacao.anexo"
                    :inspectors.sync="allInspectors"
                    :formaProcessos.sync="allFormaProcessos"
                    :autoEntradas.sync="allAutoEntradas"
                    :magistrados.sync="allMagistrados"
                    :submitting="submitting"
                    :close="close"
                    :createProcesso="createProcesso"
                    :removeProcesso="removeProcesso"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Inspector from 'src/store/models/inspector/inspector'
import AutoEntrada from 'src/store/models/autoEntrada/autoEntrada'
import FormaProcesso from 'src/store/models/formaProcesso/formaProcesso'
import Magistrado from 'src/store/models/magistrado/magistrado'
import Orgao from 'src/store/models/orgao/orgao'
import ProcessoInvestigacao from 'src/store/models/processoInvestigacao/processoInvestigacao'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
import Crime from 'src/store/models/crime/crime'
import TipoAuto from 'src/store/models/tipoAuto/tipoAuto'
import Despacho from 'src/store/models/despacho/despacho'
import TipoParecerAuto from 'src/store/models/tipoParecerAuto/tipoParecerAuto'
import TipoParecer from 'src/store/models/tipoParecer/tipoParecer'
import ParecerAuto from 'src/store/models/parecerAuto/parecerAuto'

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
      processoInvestigacao: {
        numeroProcesso: '',
        proveniencia: '',
        dataEntrada: '',
        anexo: [],
        numeroAuto: {},
        formaProcesso: {},
        magistrado: {},
        inspector: {},
      },
      numeroAuto: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        modusOperandi: '',
        horaOcorrencia: '',
        infraccao: '',
        endereco: '',
        responsavelLocal: '',
        contacto: '',
        anexo: [],
        tipoAuto: {},
        classeJudicial: {},
        crime: {},
        inspector: {},
        orgao: {}
      },
      formaProcesso: {
        designacao: ''
      },
      inspector: {
        numero: ''
      },
      magistrado: {
        numero: ''
      },
      orgao: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'numeroProcesso', align: 'left', label: 'Número do Processo', field: row => row.numeroProcesso, format: val => `${val}`, sortable: true },
        { name: 'numeroAuto', align: 'left', label: 'Número do Auto', field: row => row.numeroAuto, format: val => `${val}`, sortable: true },
        { name: 'dataEntrada', align: 'left', label: 'Data de Entrada', field: row => row.dataEntrada, format: val => `${val}`, sortable: true },
        { name: 'proveniencia', align: 'left', label: 'Proveniência', field: row => row.proveniencia, format: val => `${val}`, sortable: true },
        { name: 'formaProcesso', align: 'left', label: 'Forma de Processo', field: row => row.formaProcesso, format: val => `${val}`, sortable: true },
        { name: 'inspector', align: 'left', label: 'Inspector', field: row => row.inspector, format: val => `${val}`, sortable: true },
        { name: 'magistrado', align: 'left', label: 'Magistrado', field: row => row.magistrado, format: val => `${val}`, sortable: true },
        { name: 'estado', align: 'left', label: 'Estado', format: val => `${val}`, sortable: true },
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
  async mounted () {
    let offset = 0
      this.getAllProcesso(offset)
      this.getAllAutoEntrada(offset)
      this.getAllFormaProcesso(offset)
      this.getAllTipoAuto(offset)
      this.getAllJurisdicao(offset)
      this.getAllCrime(offset)
      this.getAllMagistrado(offset)
      this.getAllInspector(offset)
      this.getAllOrgao(offset)
      this.getAllDespacho(offset)
      this.getAllTipoParecer(offset)
      this.getAllParecerAuto(offset)
      this.getAllTipoParecerAuto(offset)
  },
  components: {
    'create-edit-form': require('components/processoInvestigacao/createEditForm.vue').default
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
    allInspectors () {
      return Object.freeze(Inspector.query().all())
    },
    allAutoEntradas () {
      const orgaoPrincipal = Orgao.query().find(localStorage.getItem('orgaoId'))
          if(orgaoPrincipal.nivel === 0)
                return AutoEntrada.query()
                                          .with(['orgao.provincia.pais','orgao.distrito.provincia.pais','orgao.tipoOrgao'])
                                          .with('inspector')
                                          .with('crime.*')
                                          .with('classeJudicial')
                                          .with('tipoAuto')
                                          .hasNot('processoInvestigacao').get()
          else
               return AutoEntrada.query()
                                          .with(['orgao.provincia.pais','orgao.distrito.provincia.pais','orgao.tipoOrgao'])
                                          .with('inspector')
                                          .with('crime.*')
                                          .with('classeJudicial')
                                          .with('tipoAuto')
                                          .where('orgao_id',orgaoPrincipal.id)
                                          .hasNot('processoInvestigacao').get()

    },
    allFormaProcessos () {
      return Object.freeze(FormaProcesso.query().all())
    },
    allMagistrados () {
      return Object.freeze(Magistrado.query().all())
    },
    allOrgaos () {
      return Object.freeze(Orgao.query().all())
    },
    allProcessos () {
       const orgaoPrincipal = Orgao.query().find(localStorage.getItem('orgaoId'))
       
          if(orgaoPrincipal.nivel === 0)
                return Object.freeze(ProcessoInvestigacao.query()
                                                                .with('inspector')
                                                                .with('magistrado')
                                                                .with('formaProcesso')
                                                                .with('despachos.tipoParecer')
                                                                .with('numeroAuto.*').all())
            else
                return Object.freeze(ProcessoInvestigacao.query()
                                                                .with('inspector')
                                                                .with('magistrado')
                                                                .with('formaProcesso')
                                                                .with('despachos.tipoParecer')
                                                                .with('numeroAuto.*')
                                                                .whereHas('numeroAuto', (query) => {
                                                                        query.where('orgao_id', orgaoPrincipal.id)
                                                                })
                                                                .get())
    }
  },
  methods: {
    createProcesso () {
      this.listErrors = []
      this.submitting = true
      // setTimeout(() => {
      //   this.submitting = false
      // }, 300)
      
      this.processoInvestigacao.formaProcesso = this.formaProcesso
      this.processoInvestigacao.formaProcesso_id = this.formaProcesso.id

      this.processoInvestigacao.magistrado = this.magistrado
      this.processoInvestigacao.magistrado_id = this.magistrado.id

      this.processoInvestigacao.inspector = this.inspector
      this.processoInvestigacao.inspector_id = this.inspector.id

      this.processoInvestigacao.numeroAuto = this.numeroAuto
      this.processoInvestigacao.numeroAuto_id = this.numeroAuto.id

      if (this.editedIndex > -1) {
         ProcessoInvestigacao.api().patch("/processoInvestigacao/" + this.processoInvestigacao.id, this.processoInvestigacao).then(resp => {
          this.submitting = false
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.processoInvestigacao.designacao + ' ]'
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
            this.submitting = false
            console.log(this.listErrors)
          }
        })
      } else {
        ProcessoInvestigacao.api().post("/processoInvestigacao/", this.processoInvestigacao).then(resp => {
          this.submitting = false
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi inserida com successo! [ ' + this.processoInvestigacao.designacao + ' ]'
          })
          this.close()
           this.$router.push({path: '/processoInvestigacao/' + resp.response.data.id })
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
            this.submitting = false
            console.log(this.listErrors)
          }
        })
      }
    },
    close () {
      this.listErrors = {}
      this.show_dialog = false
      this.processoInvestigacao = {}
      this.numeroAuto = {}
      this.magistrado = {}
      this.formaProcesso = {}
      this.inspector = {}
      this.props = this.processoInvestigacao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeProcesso (processoInvestigacao) {
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
          message: 'A informação foi Removida com successo! [ ' + processoInvestigacao.designacao + ' ]'
        })
        ProcessoInvestigacao.api().delete("/processoInvestigacao/" + this.processoInvestigacao.id)
      })
    },
    editaProcesso (processoInvestigacao) {
      this.editedIndex = 0
      this.processoInvestigacao = Object.assign({}, processoInvestigacao)
      this.numeroAuto = AutoEntrada.query().find(processoInvestigacao.numeroAuto_id)
      this.formaProcesso = FormaProcesso.query().find(processoInvestigacao.formaProcesso_id)
      this.magistrado = Magistrado.query().find(processoInvestigacao.magistrado_id)
      this.inspector = Inspector.query().find(processoInvestigacao.inspector_id)
      this.show_dialog = true
    },
    async getAllAutoEntrada(offset) {
       if(offset >= 0) {
        await AutoEntrada.api().get("/autoEntrada?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllAutoEntrada(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
   async getAllProcesso(offset) {
      if(offset >= 0) {
        await ProcessoInvestigacao.api().get("/processoInvestigacao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
  async  getAllFormaProcesso(offset) {
       if(offset >= 0) {
        await FormaProcesso.api().get("/formaProcesso?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllFormaProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
  async  getAllTipoAuto(offset) {
       if(offset >= 0) {
        await TipoAuto.api().get("/tipoAuto?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllTipoAuto(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
  async getAllInspector(offset) {
       if(offset >= 0){
         await Inspector.api().get("/inspector?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllInspector(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
  async  getAllOrgao(offset) {
        if(offset >= 0){
          await Orgao.api().get("/orgao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllOrgao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
   async  getAllJurisdicao(offset) {
       if(offset >= 0) {
        await ClasseJudicial.api().get("/classeJudicial?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllJurisdicao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
   async getAllCrime(offset) {
       if(offset >= 0) {
         await Crime.api().get("/crime?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllCrime(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
  async  getAllMagistrado(offset) {
      if(offset >= 0){
        await Magistrado.api().get("/magistrado?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllMagistrado(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
    async getAllDespacho(offset) {
      if(offset >= 0){
          await Despacho.api().get("/despacho?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllDespacho(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    async getAllTipoParecer(offset) {
       if(offset >= 0){
          await TipoParecer.api().get("/tipoParecer?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTipoParecer(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    async getAllParecerAuto(offset) {
      if(offset >= 0){
        await ParecerAuto.api().get("/parecerAuto?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllParecerAuto(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    async getAllTipoParecerAuto(offset) {
      if(offset >= 0){
        await TipoParecerAuto.api().get("/tipoParecerAuto?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTipoParecer(offset), 2)
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
          this.allProcessos.map(row =>
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
