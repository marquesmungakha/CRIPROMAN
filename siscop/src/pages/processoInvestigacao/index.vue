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
              <div class="text-pre-wrap">{{ props.row.numeroAuto_id }}</div>
              <q-popup-edit v-model="props.row.numeroAuto_id">
                <q-input v-model="props.row.numeroAuto_id" dense autofocus ></q-input>
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
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/processoInvestigacao/${props.row.id}`" >
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
import TipoAuto from 'src/store/models/tipoAuto/tipoAuto'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
import Crime from 'src/store/models/crime/crime'

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
        orgao: {}
      },
      numeroAuto: {
        numero: ''
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
        { name: 'proveniencia', align: 'left', label: 'Proveniência', field: row => row.proveniencia, format: val => `${val}`, sortable: true },
        { name: 'dataEntrada', align: 'left', label: 'Data de Entrada', field: row => row.dataEntrada, format: val => `${val}`, sortable: true },
        { name: 'formaProcesso', align: 'left', label: 'Forma de Processo', field: row => row.formaProcesso, format: val => `${val}`, sortable: true },
        { name: 'inspector', align: 'left', label: 'Inspector', field: row => row.inspector, format: val => `${val}`, sortable: true },
        { name: 'magistrado', align: 'left', label: 'Magistrado', field: row => row.magistrado, format: val => `${val}`, sortable: true },
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
      this.getAllProcesso()
      this.getAllAutoEntrada()
      this.getAllFormaProcesso()
      this.getAllTipoAuto()
      this.getAllJurisdicao()
      this.getAllCrime()
      this.getAllMagistrado()
      this.getAllInspector()
      this.getAllOrgao()
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
      return Inspector.query().all()
    },
    allAutoEntradas () {
      return AutoEntrada.query().with('orgao').with('inspector').with('crime.classeJudicial').with('classeJudicial').with('tipoAuto').hasNot('processoInvestigacao').get()
    },
    allFormaProcessos () {
      return FormaProcesso.query().all()
    },
    allMagistrados () {
      return Magistrado.query().all()
    },
    allOrgaos () {
      return Orgao.query().all()
    },
    allProcessos () {
      return ProcessoInvestigacao.query().with('orgao').with('inspector').with('magistrado').with('formaProcesso').with('autoEntrada').all()
    }
  },
  methods: {
    createProcesso () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.processoInvestigacao.numeroAuto_id = this.numeroAuto.id
      this.processoInvestigacao.formaProcesso_id = this.formaProcesso.id
      this.processoInvestigacao.magistrado_id = this.magistrado.id
      this.processoInvestigacao.inspector_id = this.inspector.id
      
      this.processoInvestigacao.formaProcesso = this.formaProcesso
      this.processoInvestigacao.magistrado = this.magistrado
      this.processoInvestigacao.inspector = this.inspector
      this.processoInvestigacao.orgao = Orgao.query().with('provincia.pais').with('tipoOrgao').first()
      this.processoInvestigacao.orgao_id = Orgao.query().first().id
      this.processoInvestigacao.numeroAuto = this.numeroAuto
      //  const image = new Blob([this.processoInvestigacao.anexo])
      this.processoInvestigacao.anexo = null
      if (this.editedIndex > -1) {
         ProcessoInvestigacao.api().patch("/processoInvestigacao/" + this.processoInvestigacao.id, this.processoInvestigacao).then(resp => {
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
            console.log(this.listErrors)
          }
        })
      } else {
        ProcessoInvestigacao.api().post("/processoInvestigacao/", this.processoInvestigacao).then(resp => {
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
            console.log(this.listErrors)
          }
        })
      }
    },
    close () {
      this.getAllProcesso()
      this.getAllAutoEntrada()
      this.getAllFormaProcesso()
      this.getAllTipoAuto()
      this.getAllJurisdicao()
      this.getAllCrime()
      this.getAllMagistrado()
      this.getAllInspector()
      this.getAllOrgao()
      this.listErrors = {}
      this.show_dialog = false
      this.processoInvestigacao = {}
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
      this.numeroAuto = AutoEntrada.query().find(processoInvestigacao.autoEntrada_id)
      this.formaProcesso = FormaProcesso.query().find(processoInvestigacao.formaProcesso_id)
      this.magistrado = Magistrado.query().find(processoInvestigacao.magistrado_id)
      this.inspector = Inspector.query().find(processoInvestigacao.inspector_id)
      this.show_dialog = true
    },
     getAllAutoEntrada() {
      AutoEntrada.api().get('/autoEntrada?offset=0&max=1000000')
    },
    getAllProcesso() {
      ProcessoInvestigacao.api().get('/processoInvestigacao?offset=0&max=1000000')
    },
    getAllFormaProcesso() {
      FormaProcesso.api().get('/formaProcesso?offset=0&max=1000000')
    },
    getAllTipoAuto() {
      TipoAuto.api().get('/tipoAuto?offset=0&max=1000000')
    },
    getAllJurisdicao() {
      ClasseJudicial.api().get('/classeJudicial?offset=0&max=1000000')
    },
    getAllCrime() {
      Crime.api().get('/crime?offset=0&max=1000000')
    },
    getAllMagistrado() {
      Magistrado.api().get('/magistrado?offset=0&max=1000000')
    },
    getAllInspector() {
      Inspector.api().get('/inspector?offset=0&max=1000000')
    },
    getAllOrgao() {
      Orgao.api().get('/orgao?offset=0&max=1000000')
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.processoInvestigacao.processos.map(row =>
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
