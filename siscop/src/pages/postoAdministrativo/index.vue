<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="PostoAdministrativo" :data="allPostoAdministrativos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
             <q-td key="provincia" :props="props">
              <div class="text-pre-wrap">{{  getProvincia ( getDistrito (props.row.distrito.id ) ).designacao }}</div>
              <q-popup-edit v-model="props.row.distrito.id">
                <q-input v-model="props.row.distrito.id" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="distrito" :props="props">
              <div class="text-pre-wrap">{{  getDistrito (props.row.distrito.id ).designacao }}</div>
              <q-popup-edit v-model="props.row.distrito.id">
                <q-input v-model="props.row.distrito.id" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/postoAdministrativo/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaPostoAdministrativo(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removePostoAdministrativo(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="postoAdministrativo.codigo"
                    :designacao.sync="postoAdministrativo.designacao"
                    :distrito.sync="distrito"
                    :provincia.sync="provincia"
                    :provincias.sync="allProvincias"
                    :distritos="allDistritosFromProvincia"
                    :submitting="submitting"
                    :close="close"
                    :createPostoAdministrativo="createPostoAdministrativo"
                    :removePostoAdministrativo="removePostoAdministrativo"/>
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
  name: 'PostoAdministrativo',
  data () {
    return {
      listErrors: [],
      options: [],
      postoAdministrativo_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      postoAdministrativo: {
        codigo: '',
        designacao: '',
        distrito: {}
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'codigo', required: true, label: 'Código', align: 'left', field: row => row.codigo, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designação', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'provincia', align: 'left', label: 'Província', field: row => row.provincia, format: val => `${val}`, sortable: true },
        { name: 'distrito', align: 'left', label: 'Distrito', field: row => row.distrito.id, format: val => `${val}`, sortable: true },
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
    return store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
  },
  mounted () {
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
  },
  components: {
    'create-edit-form': require('components/postoAdministrativo/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allProvincias () {
      return this.$store.getters['provincia/allProvincia']
    },
    allDistritos () {
      return this.$store.getters['distrito/allDistrito']
    },
    allDistritosFromProvincia () {
      return this.allDistritos.filter(distrito => distrito.provincia.id === this.provincia.id)
    },
    allPostoAdministrativos () {
      return this.$store.getters['postoAdministrativo/allPostoAdministrativo']
    }
  },
  methods: {
    ...mapActions('postoAdministrativo', ['getAllPostoAdministrativo', 'addNewPostoAdministrativo', 'updatePostoAdministrativo', 'deletePostoAdministrativo']),
    createPostoAdministrativo () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.postoAdministrativo.distrito.id = this.distrito.id
      if (this.editedIndex > -1) {
        this.updatePostoAdministrativo(this.postoAdministrativo).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.postoAdministrativo.designacao + ' ]'
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
        this.addNewPostoAdministrativo(this.postoAdministrativo).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.postoAdministrativo.designacao + ' ]'
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
      this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
      this.$store.dispatch('distrito/getAllDistrito')
      this.show_dialog = false
      this.postoAdministrativo = {}
      this.props = this.postoAdministrativo
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removePostoAdministrativo (postoAdministrativo) {
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
          message: 'A informação foi Removida com successo! [ ' + postoAdministrativo.designacao + ' ]'
        })
        this.deletePostoAdministrativo(postoAdministrativo)
      })
    },
    editaPostoAdministrativo (postoAdministrativo) {
      this.editedIndex = this.allPostoAdministrativos.indexOf(postoAdministrativo)
      this.postoAdministrativo = Object.assign({}, postoAdministrativo)
      this.distrito = this.allDistritos.filter(distrito => distrito.id === postoAdministrativo.distrito.id)[0]
      this.provincia = this.getProvincia(this.distrito)
      this.show_dialog = true
    },
    getDistrito (id) {
      const lacalDistritos = this.allDistritos.filter(distrito => distrito.id === id)
      if (lacalDistritos.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return lacalDistritos[0] }
    },
    getProvincia (distrito) {
      const lacalProvinncias = this.allProvincias.filter(provincia => provincia.id === distrito.provincia.id)
      if (lacalProvinncias.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return lacalProvinncias[0] }
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.postoAdministrativo.postoAdministrativos.map(row =>
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
      const status = exportFile('postoAdministrativo_list.csv', content, 'text/csv')
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
