<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Auto de Corpo de Delito Indirecto" :data="allCorpoDelitos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="inspector" :props="props">
              <div class="text-pre-wrap">{{  getInspector(props.row.inspector.id).numero  }} - {{  getInspector(props.row.inspector.id).nome  }} {{  getInspector(props.row.inspector.id).apelido  }}</div>
              <q-popup-edit v-model="props.row.inspector">
                <q-input v-model="props.row.inspector" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/corpoDelito/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaCorpoDelito(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeCorpoDelito(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="corpoDelito.numero"
                    :dataAbertura.sync="corpoDelito.dataAbertura"
                    :descricao.sync="corpoDelito.descricao"
                    :inspector.sync="inspector"
                    :anexo.sync="corpoDelito.anexo"
                    :inspectors.sync="allInspectors"
                    :submitting="submitting"
                    :close="close"
                    :createCorpoDelito="createCorpoDelito"
                    :removeCorpoDelito="removeCorpoDelito"/>
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
  name: 'CorpoDelito',
  data () {
    return {
      listErrors: [],
      options: [],
      corpoDelito_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      corpoDelito: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        anexo: [],
        processo: {},
        inspector: {},
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
    return store.dispatch('corpoDelito/getAllCorpoDelito')
  },
  props: ['processo'],
  mounted () {
    this.$store.dispatch('corpoDelito/getAllCorpoDelito')
    this.$store.dispatch('inspector/getAllInspector')
    this.$store.dispatch('orgao/getAllOrgao')
  },
  components: {
    'create-edit-form': require('components/corpoDelito/createEditForm.vue').default
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
    allCorpoDelitos () {
      return this.$store.getters['corpoDelito/allCorpoDelito'] // .filter(corpoDelito => corpoDelito.processo.id === this.processo.id)
    }
  },
  methods: {
    ...mapActions('corpoDelito', ['getAllCorpoDelito', 'addNewCorpoDelito', 'updateCorpoDelito', 'deleteCorpoDelito']),
    createCorpoDelito () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.corpoDelito.processo = this.processo
      this.corpoDelito.inspector = this.inspector
      this.corpoDelito.orgao = this.$store.state.orgao.orgaos[0]
      //  const image = new Blob([this.corpoDelito.anexo])
      this.corpoDelito.anexo = null
      if (this.editedIndex > -1) {
        this.updateCorpoDelito(this.corpoDelito).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.corpoDelito.designacao + ' ]'
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
        this.addNewCorpoDelito(this.corpoDelito).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.corpoDelito.designacao + ' ]'
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
      this.$store.dispatch('corpoDelito/getAllCorpoDelito')
      this.$store.dispatch('inspector/getAllInspector')
      this.$store.dispatch('orgao/getAllOrgao')
      this.show_dialog = false
      this.corpoDelito = {}
      this.props = this.corpoDelito
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeCorpoDelito (corpoDelito) {
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
          message: 'A informação foi Removida com successo! [ ' + corpoDelito.designacao + ' ]'
        })
        this.deleteCorpoDelito(corpoDelito)
      })
    },
    editaCorpoDelito (corpoDelito) {
      this.editedIndex = this.allCorpoDelitos.indexOf(corpoDelito)
      this.corpoDelito = Object.assign({}, corpoDelito)
      this.inspector = this.getInspector(corpoDelito.inspector.id)
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
          this.$store.state.corpoDelito.corpoDelitos.map(row =>
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
      const status = exportFile('corpoDelito_list.csv', content, 'text/csv')
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
