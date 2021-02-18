<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allCrimes" :filter="filter" binary-state-sort row-key="name" title="Crime">

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
          <q-td key="codigo" :props="props">
            {{ props.row.codigo }}
            <q-popup-edit v-model="props.row.codigo">
              <q-input v-model="props.row.codigo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="designacao" :props="props">
            {{ props.row.designacao }}
            <q-popup-edit v-model="props.row.designacao" title="Update designacao">
              <q-input v-model="props.row.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="classeJudicial" :props="props">
            <div class="text-pre-wrap">{{ props.row.classeJudicial.designacao }}</div>
            <q-popup-edit v-model="props.row.classeJudicial.designacao">
              <q-input v-model="props.row.classeJudicial.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/crime/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm />
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaCrime(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeCrime(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :classeJudicial.sync="classeJudicial"
                      :close="close"
                      :createCrime="createCrime"
                      :designacao.sync="crime.designacao"
                      :jurisdicoes="allJurisdicoes"
                      :listErrors="listErrors"
                      :removeCrime="removeCrime"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
import Crime from 'src/store/models/crime/crime'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Crime',
  data() {
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
        {
          name: 'designacao',
          align: 'left',
          label: 'Designação',
          field: row => row.designacao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'classeJudicial',
          align: 'left',
          label: 'Família Delitiva',
          field: row => row.classeJudicial.id,
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
    return this.getAllCrime()
  },
  mounted() {
    let offset = 0
    this.getAllCrime(offset)
    this.getAllJurisdicao(offset)
  },
  components: {
    'create-edit-form': require('components/crime/createEditForm.vue').default
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
    allJurisdicoes() {
      return ClasseJudicial.query().all()
    },
    allCrimes() {
      return Crime.query().with('classeJudicial').all()
    }
  },
  methods: {
    createCrime() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.crime.classeJudicial_id = this.classeJudicial.id
      this.crime.classeJudicial = this.classeJudicial
      if (this.editedIndex > -1) {
        Crime.api().patch("/crime/" + this.crime.id, this.crime).then(resp => {
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
        Crime.api().post("/crime/", this.crime).then(resp => {
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
    close() {
      this.show_dialog = false
      this.crime = {}
      this.classeJudicial = {}
      this.props = this.crime
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeCrime(crime) {
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
        Crime.api().delete("/crime/" + crime.id)
      })
    },
    editaCrime(crime) {
      this.editedIndex = 0
      this.crime = Object.assign({}, crime)
      this.classeJudicial = ClasseJudicial.find(crime.classeJudicial_id)
      this.show_dialog = true
    },
    getAllCrime(offset) {
      Crime.api().get("/crime?offset="+offset+"&max=1000").then(resp => {
          console.log(resp)
          offset = offset + 1
          if(resp.response.data.items.length() > 0) 
              setTimeout(this.getAllCrime, 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
    },
    getAllJurisdicao(offset) {
      ClasseJudicial.api().get("/classeJudicial?offset="+offset+"&max=1000").then(resp => {
          console.log(resp)
          offset = offset + 1
          if(resp.response.data.items.length() > 0) 
              setTimeout(this.getAllJurisdicao, 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
    },
    filterFn(val, update, abort) {
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
    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    setModel(val) {
      this.crime.classeJudicial = val
    },
    exportTable() {
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
