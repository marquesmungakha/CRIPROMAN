<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Auto de Entrada" :data="allAutoEntradas" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
             <q-td key="tipoAuto" :props="props">
              <div class="text-pre-wrap">{{ getTipoAuto(props.row.tipoAuto.id).designacao }}</div>
              <q-popup-edit v-model="props.row.tipoAuto">
                <q-input v-model="props.row.tipoAuto" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="classeJudicial" :props="props">
              <div class="text-pre-wrap">{{ getJurisdicao(props.row.classeJudicial.id).designacao }}</div>
              <q-popup-edit v-model="props.row.classeJudicial">
                <q-input v-model="props.row.classeJudicial" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="crime" :props="props">
              <div class="text-pre-wrap">{{  getCrime(props.row.crime.id).designacao }}</div>
              <q-popup-edit v-model="props.row.crime.id">
                <q-input v-model="props.row.crime.id" dense autofocus ></q-input>
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
              <router-link :to="`/autoEntrada/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaAutoEntrada(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeAutoEntrada(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="autoEntrada.numero"
                    :dataAbertura.sync="autoEntrada.dataAbertura"
                    :descricao.sync="autoEntrada.descricao"
                    :tipoAuto.sync="tipoAuto"
                    :classeJudicial.sync="classeJudicial"
                    :crime.sync="crime"
                    :modusOperandi.sync="autoEntrada.modusOperandi"
                    :horaOcorrencia.sync="autoEntrada.horaOcorrencia"
                    :infraccao.sync="autoEntrada.infraccao"
                    :endereco.sync="autoEntrada.endereco"
                    :responsavelLocal.sync="autoEntrada.responsavelLocal"
                    :contacto.sync="autoEntrada.contacto"
                    :inspector.sync="inspector"
                    :anexo.sync="autoEntrada.anexo"
                    :inspectors.sync="allInspectors"
                    :crimes.sync="allCrimesFromClasseJudicial"
                    :classeJudicials.sync="allclasseJudicials"
                    :tipoAutos.sync="alltipoAutos"
                    :submitting="submitting"
                    :close="close"
                    :createAutoEntrada="createAutoEntrada"
                    :removeAutoEntrada="removeAutoEntrada"/>
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
  name: 'AutoEntrada',
  data () {
    return {
      listErrors: [],
      options: [],
      autoEntrada_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      autoEntrada: {
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
      tipoAuto: {
        codigo: '',
        designacao: ''
      },
      classeJudicial: {
        designacao: ''
      },
      crime: {
        designacao: ''
      },
      inspector: {
        codigo: '',
        designacao: ''
      },
      orgao: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'numero', align: 'left', label: 'Número do Auto', field: row => row.numero, format: val => `${val}`, sortable: true },
        { name: 'dataAbertura', align: 'left', label: 'Data de Abertura', field: row => row.dataAbertura, format: val => `${val}`, sortable: true },
        { name: 'tipoAuto', align: 'left', label: 'Tipo de Auto', field: row => row.tipoAuto.id, format: val => `${val}`, sortable: true },
        { name: 'classeJudicial', align: 'left', label: 'Família Delitiva', field: row => row.classeJudicial.id, format: val => `${val}`, sortable: true },
        { name: 'crime', align: 'left', label: 'Tipo Legal de Crime', field: row => row.crime.id, format: val => `${val}`, sortable: true },
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
    return store.dispatch('autoEntrada/getAllAutoEntrada')
  },
  mounted () {
    this.$store.dispatch('tipoAuto/getAllTipoAuto')
    this.$store.dispatch('jurisdicao/getAllJurisdicao')
    this.$store.dispatch('crime/getAllCrime')
    this.$store.dispatch('inspector/getAllInspector')
    this.$store.dispatch('orgao/getAllOrgao')
  },
  components: {
    'create-edit-form': require('components/autoEntrada/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allInspectors () {
      return this.$store.getters['inspector/allInspector']
    },
    allCrimes () {
      return this.$store.getters['crime/allCrime']
    },
    allCrimesFromClasseJudicial () {
      return this.allCrimes.filter(crime => crime.classeJudicial.id === this.classeJudicial.id)
    },
    allclasseJudicials () {
      return this.$store.getters['jurisdicao/allJurisdicao']
    },
    alltipoAutos () {
      return this.$store.getters['tipoAuto/allTipoAuto']
    },
    allOrgaos () {
      return this.$store.getters['orgao/allOrgao']
    },
    allAutoEntradas () {
      return this.$store.getters['autoEntrada/allAutoEntrada']
    }
  },
  methods: {
    ...mapActions('autoEntrada', ['getAllAutoEntrada', 'addNewAutoEntrada', 'updateAutoEntrada', 'deleteAutoEntrada']),
    createAutoEntrada () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.autoEntrada.tipoAuto = this.tipoAuto
      this.autoEntrada.classeJudicial = this.classeJudicial
      this.autoEntrada.crime = this.crime
      this.autoEntrada.inspector = this.inspector
      this.autoEntrada.orgao = this.$store.state.orgao.orgaos[0]
      //  const image = new Blob([this.autoEntrada.anexo])
      this.autoEntrada.anexo = null
      if (this.editedIndex > -1) {
        this.updateAutoEntrada(this.autoEntrada).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.autoEntrada.designacao + ' ]'
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
        this.addNewAutoEntrada(this.autoEntrada).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.autoEntrada.designacao + ' ]'
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
      this.$store.dispatch('autoEntrada/getAllAutoEntrada')
      this.$store.dispatch('tipoAuto/getAllTipoAuto')
      this.$store.dispatch('jurisdicao/getAllJurisdicao')
      this.$store.dispatch('crime/getAllCrime')
      this.$store.dispatch('inspector/getAllInspector')
      this.$store.dispatch('orgao/getAllOrgao')
      this.show_dialog = false
      this.autoEntrada = {}
      this.props = this.autoEntrada
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeAutoEntrada (autoEntrada) {
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
          message: 'A informação foi Removida com successo! [ ' + autoEntrada.designacao + ' ]'
        })
        this.deleteAutoEntrada(autoEntrada)
      })
    },
    editaAutoEntrada (autoEntrada) {
      this.editedIndex = this.allAutoEntradas.indexOf(autoEntrada)
      this.autoEntrada = Object.assign({}, autoEntrada)
      this.tipoAuto = this.getTipoAuto(autoEntrada.tipoAuto.id)
      this.classeJudicial = this.getJurisdicao(autoEntrada.classeJudicial.id)
      this.crime = this.getCrime(autoEntrada.crime.id)
      this.inspector = this.getInspector(autoEntrada.inspector.id)
      this.orgao = this.$store.state.orgao.orgaos[0]
      this.show_dialog = true
    },
    getTipoAuto (id) {
      const localTipoAuto = this.alltipoAutos.filter(tipoAuto => tipoAuto.id === id)
      if (localTipoAuto.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localTipoAuto[0] }
    },
    getJurisdicao (id) {
      const localJurisdicao = this.allclasseJudicials.filter(jurisdicao => jurisdicao.id === id)
      if (localJurisdicao.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localJurisdicao[0] }
    },
    getCrime (id) {
      const localCrime = this.allCrimes.filter(crime => crime.id === id)
      if (localCrime.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localCrime[0] }
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
          this.$store.state.autoEntrada.autoEntradas.map(row =>
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
      const status = exportFile('autoEntrada_list.csv', content, 'text/csv')
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
