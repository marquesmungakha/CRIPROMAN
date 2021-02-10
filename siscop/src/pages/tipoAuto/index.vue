<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allTipoAutos" :filter="filter" binary-state-sort row-key="name"
             title="Tipo de Auto">

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
            <q-popup-edit v-model="props.row.designacao" title="Update designação">
              <q-input v-model="props.row.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/tipoAuto/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm />
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click.stop="editaTipoAuto(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click.stop="removeTipoAuto(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :close="close"
                      :codigo.sync="tipoAuto.codigo"
                      :createTipoAuto="createTipoAuto"
                      :designacao.sync="tipoAuto.designacao"
                      :listErrors="listErrors"
                      :removeTipoAuto="removeTipoAuto"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import TipoAuto from 'src/store/models/tipoAuto/tipoAuto'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'TipoAuto',
  data() {
    return {
      listErrors: [],
      tipoAuto_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      tipoAuto: {
        codigo: '',
        designacao: ''
      },
      columns: [
        {
          name: 'codigo',
          required: true,
          label: 'Código',
          align: 'left',
          field: row => row.codigo,
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
    return this.getAllTipoAuto()
  },
  mounted() {
    this.getAllTipoAuto()
  },
  components: {
    'create-edit-form': require('components/tipoAuto/createEditForm.vue').default
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
    allTipoAutos() {
      return TipoAuto.query().all()
    }
  },
  methods: {
    createTipoAuto() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      if (this.editedIndex > -1) {
        TipoAuto.api().patch("/tipoAuto/" + this.tipoAuto.id, this.tipoAuto).then(resp => {
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
            message: 'A informação foi actualizada com successo!! [ ' + this.tipoAuto.designacao + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log(error)
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
        TipoAuto.api().post("/tipoAuto/", this.tipoAuto).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi inserida com successo! [ ' + this.tipoAuto.designacao + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log(error)
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
      this.getAllTipoAuto()
      this.show_dialog = false
      this.tipoAuto = {}
      this.props = this.tipoAuto
      setTimeout(() => {
        // this.tipoAuto = Object.assign({}, null)
        this.editedIndex = -1
      }, 300)
    },
    removeTipoAuto(tipoAuto) {
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
          message: 'A informação foi Removida com successo! [ ' + tipoAuto.designacao + ' ]'
        })
        TipoAuto.api().delete("/tipoAuto/" + tipoAuto.id)
      })
    },
    editaTipoAuto(tipoAuto) {
      this.editedIndex = 0
      this.tipoAuto = Object.assign({}, tipoAuto)
      this.show_dialog = true
    },
    getAllTipoAuto() {
      TipoAuto.api().get('/tipoAuto?offset=0&max=1000000')
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.tipoAuto.tipoAutos.map(row =>
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
      const status = exportFile('tipoAuto_list.csv', content, 'text/csv')
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
