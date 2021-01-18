<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Crime" :data="allCrimes" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="codigo" :props="props">
              {{ props.row.codigo }}
              <q-popup-edit v-model="props.row.codigo">
                <q-input v-model="props.row.codigo" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="designacao" :props="props">
              {{ props.row.designacao }}
              <q-popup-edit v-model="props.row.designacao" title="Update designacao">
                <q-input v-model="props.row.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="classeJudicial" :props="props">
              <div class="text-pre-wrap">{{  getJurisdicao (props.row.classeJudicial.id ).designacao }}</div>
              <q-popup-edit v-model="props.row.classeJudicial.id">
                <q-input v-model="props.row.classeJudicial.id" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/crime/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaCrime(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeCrime(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :designacao.sync="crime.designacao"
                    :classeJudicial.sync="classeJudicial"
                    :jurisdicoes="allJurisdicoes"
                    :submitting="submitting"
                    :close="close"
                    :createCrime="createCrime"
                    :removeCrime="removeCrime"/>
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
  name: 'Crime',
  data () {
    return {
      listErrors: [],
      options: [],
      crime_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      crime: {
        designacao: '',
        classeJudicial: {}
      },
      classeJudicial: {
        designacao: ''
      },
      columns: [
        { name: 'designacao', align: 'left', label: 'Designação', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'classeJudicial', align: 'left', label: 'Família Delitiva', field: row => row.classeJudicial.id, format: val => `${val}`, sortable: true },
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
    return store.dispatch('crime/getAllCrime')
  },
  mounted () {
    this.$store.dispatch('jurisdicao/getAllJurisdicao')
  },
  components: {
    'create-edit-form': require('components/crime/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allJurisdicoes () {
      return this.$store.getters['jurisdicao/allJurisdicao']
    },
    allCrimes () {
      return this.$store.getters['crime/allCrime']
    }
  },
  methods: {
    ...mapActions('crime', ['getAllCrime', 'addNewCrime', 'updateCrime', 'deleteCrime']),
    createCrime () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.crime.classeJudicial.id = this.classeJudicial.id
      if (this.editedIndex > -1) {
        this.updateCrime(this.crime).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.crime.designacao + ' ]'
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
        this.addNewCrime(this.crime).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.crime.designacao + ' ]'
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
      this.$store.dispatch('crime/getAllCrime')
      this.show_dialog = false
      this.crime = {}
      this.props = this.crime
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeCrime (crime) {
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
          message: 'A informação foi Removida com successo! [ ' + crime.designacao + ' ]'
        })
        this.deleteCrime(crime)
      })
    },
    editaCrime (crime) {
      this.editedIndex = this.allCrimes.indexOf(crime)
      this.crime = Object.assign({}, crime)
      this.classeJudicial = this.allJurisdicoes.filter(classeJudicial => classeJudicial.id === crime.classeJudicial.id)[0]
      this.show_dialog = true
    },
    getJurisdicao (id) {
      if (this.allJurisdicoes.filter(classeJudicial => classeJudicial.id === id).length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return this.allJurisdicoes.filter(classeJudicial => classeJudicial.id === id)[0] }
    },
    filterFn (val, update, abort) {
      const stringOptions = this.allJurisdicoes
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(classeJudicial => classeJudicial)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(classeJudicial => classeJudicial)
            .filter(classeJudicial => {
              return classeJudicial &&
                   classeJudicial.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.crime.classeJudicial = val
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.crime.crimes.map(row =>
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
      const status = exportFile('crime_list.csv', content, 'text/csv')
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
