<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Detencao" :data="allDetencaos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="numero" :props="props">
              {{ props.row.numero }}
              <q-popup-edit v-model="props.row.numero" title="Update numero">
                <q-input v-model="props.row.numero" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="dataAbertura" :props="props">
              {{ props.row.dataAbertura }}
              <q-popup-edit v-model="props.row.dataAbertura" title="Update dataAbertura">
                <q-input v-model="props.row.dataAbertura" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
             <q-td key="localDetencao" :props="props">
              <div class="text-pre-wrap">{{ props.row.localDetencao }}</div>
              <q-popup-edit v-model="props.row.localDetencao">
                <q-input v-model="props.row.localDetencao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="motivoDetencao" :props="props">
              <div class="text-pre-wrap">{{ getMotivoDetencao(props.row.motivoDetencao.id).designacao }}</div>
              <q-popup-edit v-model="props.row.motivoDetencao">
                <q-input v-model="props.row.motivoDetencao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="inspector" :props="props">
              <div class="text-pre-wrap">{{  getInspector(props.row.inspector.id).numero  }} - {{  getInspector(props.row.inspector.id).nome  }} {{  getInspector(props.row.inspector.id).apelido  }}</div>
              <q-popup-edit v-model="props.row.inspector">
                <q-input v-model="props.row.inspector" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/detencao/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaDetencao(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeDetencao(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="detencao.numero"
                    :dataAbertura.sync="detencao.dataAbertura"
                    :descricao.sync="detencao.descricao"
                    :motivoDetencao.sync="motivoDetencao"
                    :localDetencao.sync="detencao.localDetencao"
                    :inspector.sync="inspector"
                    :anexo.sync="detencao.anexo"
                    :inspectors.sync="allInspectors"
                    :motivoDetencaos.sync="allMotivoDetencaos"
                    :submitting="submitting"
                    :close="close"
                    :createDetencao="createDetencao"
                    :removeDetencao="removeDetencao"/>
  </q-page>
</template>

<script>
import { mapActions } from 'vuex'
import { exportFile } from 'quasar'

function wrapCsvValue (val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Detencao',
  data () {
    return {
      listErrors: [],
      options: [],
      detencao_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      detencao: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        localDetencao: '',
        anexo: [],
        motivoDetencao: {},
        inspector: {},
        processo: {},
        orgao: {}
      },
      motivoDetencao: {
        designacao: ''
      },
      inspector: {
        numero: ''
      },
      orgao: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'numero', align: 'left', label: 'Número do Auto', field: row => row.numero, format: val => `${val}`, sortable: true },
        { name: 'dataAbertura', align: 'left', label: 'Data de Abertura', field: row => row.dataAbertura, format: val => `${val}`, sortable: true },
        { name: 'localDetencao', align: 'left', label: 'Local Detenção', field: row => row.localDetencao, format: val => `${val}`, sortable: true },
        { name: 'motivoDetencao', align: 'left', label: 'Motivo de Detenção', field: row => row.motivoDetencao.id, format: val => `${val}`, sortable: true },
        { name: 'inspector', align: 'left', label: 'Inspector', field: row => row.inspector.id, format: val => `${val}`, sortable: true },
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
    return store.dispatch('detencao/getAllDetencao')
  },
  props: ['processo'],
  mounted () {
    this.$store.dispatch('tipoAuto/getAllTipoAuto')
    this.$store.dispatch('motivoDetencao/getAllMotivoDetencao')
    this.$store.dispatch('inspector/getAllInspector')
    this.$store.dispatch('orgao/getAllOrgao')
  },
  components: {
    'create-edit-form': require('components/detencao/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allInspectors () {
      return this.$store.getters['inspector/allInspector']
    },
    allMotivoDetencaos () {
      return this.$store.getters['motivoDetencao/allMotivoDetencao']
    },
    allOrgaos () {
      return this.$store.getters['orgao/allOrgao']
    },
    allDetencaos () {
      return this.$store.getters['detencao/allDetencao']
    }
  },
  methods: {
    ...mapActions('detencao', ['getAllDetencao', 'addNewDetencao', 'updateDetencao', 'deleteDetencao']),
    createDetencao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.detencao.motivoDetencao = this.motivoDetencao
      this.detencao.processo = this.processo
      this.detencao.inspector = this.inspector
      this.detencao.orgao = this.$store.state.orgao.orgaos[0]
      //  const image = new Blob([this.detencao.anexo])
      this.detencao.anexo = null
      if (this.editedIndex > -1) {
        this.updateDetencao(this.detencao).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.detencao.designacao + ' ]'
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
        this.addNewDetencao(this.detencao).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.detencao.designacao + ' ]'
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
      this.$store.dispatch('detencao/getAllDetencao')
      this.$store.dispatch('motivoDetencao/getAllMotivoDetencao')
      this.$store.dispatch('inspector/getAllInspector')
      this.$store.dispatch('orgao/getAllOrgao')
      this.show_dialog = false
      this.detencao = {}
      this.props = this.detencao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeDetencao (detencao) {
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
          message: 'A informação foi Removida com successo! [ ' + detencao.designacao + ' ]'
        })
        this.deleteDetencao(detencao)
      })
    },
    editaDetencao (detencao) {
      this.editedIndex = this.allDetencaos.indexOf(detencao)
      this.detencao = Object.assign({}, detencao)
      this.motivoDetencao = this.getMotivoDetencao(detencao.motivoDetencao.id)
      this.inspector = this.getInspector(detencao.inspector.id)
      this.orgao = this.$store.state.orgao.orgaos[0]
      this.show_dialog = true
    },
    getMotivoDetencao (id) {
      const localMotivoDetencao = this.allMotivoDetencaos.filter(motivoDetencao => motivoDetencao.id === id)
      if (localMotivoDetencao.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localMotivoDetencao[0] }
    },
    getInspector (id) {
      const localInspector = this.allInspectors.filter(inspector => inspector.id === id)
      if (localInspector.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localInspector[0] }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.detencao.detencaos.map(row =>
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
      const status = exportFile('detencao_list.csv', content, 'text/csv')
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
