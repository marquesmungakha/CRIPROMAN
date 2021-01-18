<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Auto de Apreensão" :data="allApreensaos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
             <q-td key="localApreencao" :props="props">
              {{ props.row.dataAbertura }}
              <q-popup-edit v-model="props.row.localApreencao" title="Update localApreencao">
                <q-input v-model="props.row.localApreencao" dense autofocus ></q-input>
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
              <router-link :to="`/apreensao/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaApreensao(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeApreensao(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="apreensao.numero"
                    :dataAbertura.sync="apreensao.dataAbertura"
                    :descricao.sync="apreensao.descricao"
                    :localApreencao.sync="apreensao.localApreencao"
                    :inspector.sync="inspector"
                    :anexo.sync="apreensao.anexo"
                    :inspectors.sync="allInspectors"
                    :submitting="submitting"
                    :close="close"
                    :createApreensao="createApreensao"
                    :removeApreensao="removeApreensao"/>
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
  name: 'Apreensao',
  data () {
    return {
      listErrors: [],
      options: [],
      apreensao_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      apreensao: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        localApreencao: '',
        anexo: [],
        inspector: {},
        processo: {},
        orgao: {}
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
        { name: 'localApreencao', align: 'left', label: 'Local de Apreensão', field: row => row.localApreencao, format: val => `${val}`, sortable: true },
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
    return store.dispatch('apreensao/getAllApreensao')
  },
  props: ['processo'],
  mounted () {
    this.$store.dispatch('apreensao/getAllApreensao')
    this.$store.dispatch('inspector/getAllInspector')
    this.$store.dispatch('orgao/getAllOrgao')
  },
  components: {
    'create-edit-form': require('components/apreensao/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allInspectors () {
      return this.$store.getters['inspector/allInspector']
    },
    allOrgaos () {
      return this.$store.getters['orgao/allOrgao']
    },
    allApreensaos () {
      return this.$store.getters['apreensao/allApreensao'].filter(apreesao => apreesao.processo.id === this.processo.id)
    }
  },
  methods: {
    ...mapActions('apreensao', ['getAllApreensao', 'addNewApreensao', 'updateApreensao', 'deleteApreensao']),
    createApreensao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.apreensao.inspector = this.inspector
      this.apreensao.processo = this.processo
      this.apreensao.orgao = this.$store.state.orgao.orgaos[0]
      //  const image = new Blob([this.apreensao.anexo])
      this.apreensao.anexo = null
      if (this.editedIndex > -1) {
        this.updateApreensao(this.apreensao).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.apreensao.designacao + ' ]'
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
        this.addNewApreensao(this.apreensao).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.apreensao.designacao + ' ]'
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
      this.$store.dispatch('apreensao/getAllApreensao')
      this.$store.dispatch('inspector/getAllInspector')
      this.$store.dispatch('orgao/getAllOrgao')
      this.show_dialog = false
      this.apreensao = {}
      this.props = this.apreensao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeApreensao (apreensao) {
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
          message: 'A informação foi Removida com successo! [ ' + apreensao.designacao + ' ]'
        })
        this.deleteApreensao(apreensao)
      })
    },
    editaApreensao (apreensao) {
      this.editedIndex = this.allApreensaos.indexOf(apreensao)
      this.apreensao = Object.assign({}, apreensao)
      this.inspector = this.getInspector(apreensao.inspector.id)
      this.orgao = this.$store.state.orgao.orgaos[0]
      this.show_dialog = true
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
          this.$store.state.apreensao.apreensaos.map(row =>
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
      const status = exportFile('apreensao_list.csv', content, 'text/csv')
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
