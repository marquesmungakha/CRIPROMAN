<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Marca" :data="allMarcas" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="tipoMeio" :props="props">
              <div class="text-pre-wrap">{{  getTipoMeio (props.row.tipoMeio.id ).designacao }}</div>
              <q-popup-edit v-model="props.row.tipoMeio.id">
                <q-input v-model="props.row.tipoMeio.id" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/marca/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaMarca(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeMarca(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="marca.codigo"
                    :designacao.sync="marca.designacao"
                    :tipoMeio.sync="tipoMeio"
                    :tipoMeios="allTipoMeios"
                    :submitting="submitting"
                    :close="close"
                    :createMarca="createMarca"
                    :removeMarca="removeMarca"/>
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
  name: 'Marca',
  data () {
    return {
      listErrors: [],
      options: [],
      marca_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      marca: {
        codigo: '',
        designacao: ''
      },
      tipoMeio: {
        designacao: ''
      },
      columns: [
        { name: 'codigo', required: true, label: 'Codigo', align: 'left', field: row => row.codigo, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designacao', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'tipoMeio', align: 'left', label: 'Tipo de Meio', field: row => row.tipoMeio.id, format: val => `${val}`, sortable: true },
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
    return store.dispatch('marca/getAllMarca')
  },
  mounted () {
    this.$store.dispatch('tipoMeio/getAllTipoMeio')
  },
  components: {
    'create-edit-form': require('components/marca/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allTipoMeios () {
      return this.$store.getters['tipoMeio/allTipoMeio']
    },
    allMarcas () {
      return this.$store.getters['marca/allMarca']
    }
  },
  methods: {
    ...mapActions('marca', ['getAllMarca', 'addNewMarca', 'updateMarca', 'deleteMarca']),
    createMarca () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.marca.tipoMeio = this.tipoMeio
      if (this.editedIndex > -1) {
        this.updateMarca(this.marca).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.marca.designacao + ' ]'
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
        this.addNewMarca(this.marca).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.marca.designacao + ' ]'
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
      this.$store.dispatch('marca/getAllMarca')
      this.$store.dispatch('tipoMeio/getAllTipoMeio')
      this.show_dialog = false
      this.marca = {}
      this.props = this.marca
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeMarca (marca) {
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
          message: 'A informação foi Removida com successo! [ ' + marca.designacao + ' ]'
        })
        this.deleteMarca(marca)
      })
    },
    editaMarca (marca) {
      this.editedIndex = this.allMarcas.indexOf(marca)
      this.marca = Object.assign({}, marca)
      this.tipoMeio = this.allTipoMeios.filter(tipoMeio => tipoMeio.id === marca.tipoMeio.id)[0]
      this.show_dialog = true
    },
    getTipoMeio (id) {
      const tipoMeio = this.allTipoMeios.filter(tipoMeio => tipoMeio.id === id)
      if (tipoMeio.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return tipoMeio[0] }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.marca.tipoMeio = val
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.marca.marcas.map(row =>
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
      const status = exportFile('marca_list.csv', content, 'text/csv')
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
