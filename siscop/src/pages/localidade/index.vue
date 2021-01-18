<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Localidade" :data="allLocalidades" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="postoAdministrativo" :props="props">
              <div class="text-pre-wrap">{{  getPostoAdministrativo (props.row.postoAdministrativo.id ).designacao }}</div>
              <q-popup-edit v-model="props.row.postoAdministrativo.id">
                <q-input v-model="props.row.postoAdministrativo.id" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/localidade/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaLocalidade(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeLocalidade(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localidade.codigo"
                    :designacao.sync="localidade.designacao"
                    :postoAdministrativo.sync="postoAdministrativo"
                    :distrito.sync="distrito"
                    :provincia.sync="provincia"
                    :provincias.sync="allProvincias"
                    :distritos.sync="allDistritosFromProvincia"
                    :postoAdministrativos.sync="allPostoAdministrativosFromDistrito"
                    :submitting="submitting"
                    :close="close"
                    :createLocalidade="createLocalidade"
                    :removeLocalidade="removeLocalidade"/>
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
  name: 'Localidade',
  data () {
    return {
      listErrors: [],
      options: [],
      localidade_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      localidade: {
        codigo: '',
        designacao: '',
        distrito: {},
        postoAdministrativo: {}
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      postoAdministrativo: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'codigo', required: true, label: 'Código', align: 'left', field: row => row.codigo, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designação', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'provincia', align: 'left', label: 'Província', field: row => row.provincia, format: val => `${val}`, sortable: true },
        { name: 'distrito', align: 'left', label: 'Distrito', field: row => row.distrito.id, format: val => `${val}`, sortable: true },
        { name: 'postoAdministrativo', align: 'left', label: 'Posto Administrativo', field: row => row.postoAdministrativo.id, format: val => `${val}`, sortable: true },
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
    return store.dispatch('localidade/getAllLocalidade')
  },
  mounted () {
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
    this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
  },
  components: {
    'create-edit-form': require('components/localidade/createEditForm.vue').default
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
    allPostoAdministrativos () {
      return this.$store.getters['postoAdministrativo/allPostoAdministrativo']
    },
    allDistritosFromProvincia () {
      return this.allDistritos.filter(distrito => distrito.provincia.id === this.provincia.id)
    },
    allPostoAdministrativosFromDistrito () {
      return this.allPostoAdministrativos.filter(postoAdministrativo => postoAdministrativo.distrito.id === this.distrito.id)
    },
    allLocalidades () {
      return this.$store.getters['localidade/allLocalidade']
    }
  },
  methods: {
    ...mapActions('localidade', ['getAllLocalidade', 'addNewLocalidade', 'updateLocalidade', 'deleteLocalidade']),
    createLocalidade () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localidade.distrito.id = this.distrito.id
      this.localidade.postoAdministrativo.id = this.postoAdministrativo.id
      if (this.editedIndex > -1) {
        this.updateLocalidade(this.localidade).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.localidade.designacao + ' ]'
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
        this.addNewLocalidade(this.localidade).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.localidade.designacao + ' ]'
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
      this.$store.dispatch('localidade/getAllLocalidade')
      this.$store.dispatch('distrito/getAllDistrito')
      this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
      this.show_dialog = false
      this.localidade = {}
      this.props = this.localidade
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeLocalidade (localidade) {
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
          message: 'A informação foi Removida com successo! [ ' + localidade.designacao + ' ]'
        })
        this.deleteLocalidade(localidade)
      })
    },
    editaLocalidade (localidade) {
      this.editedIndex = this.allLocalidades.indexOf(localidade)
      this.localidade = Object.assign({}, localidade)
      this.postoAdministrativo = this.getPostoAdministrativo(localidade.postoAdministrativo.id)
      this.distrito = this.getDistrito(localidade.distrito.id)
      this.provincia = this.getProvincia(this.distrito)
      this.show_dialog = true
    },
    getProvincia (distrito) {
      const lacalProvinncias = this.allProvincias.filter(provincia => provincia.id === distrito.provincia.id)
      if (lacalProvinncias.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return lacalProvinncias[0] }
    },
    getDistrito (id) {
      const lacalDistritos = this.allDistritos.filter(distrito => distrito.id === id)
      if (lacalDistritos.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return lacalDistritos[0] }
    },
    getPostoAdministrativo (id) {
      const lacalPostoAdministrativo = this.allPostoAdministrativos.filter(postoAdministrativo => postoAdministrativo.id === id)
      if (lacalPostoAdministrativo.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return lacalPostoAdministrativo[0] }
    },
    filterFn (val, update, abort) {
      const stringOptions = this.allDistritos
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(distrito => distrito)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(distrito => distrito)
            .filter(distrito => {
              return distrito &&
                   distrito.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.localidade.distrito = val
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.localidade.localidades.map(row =>
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
      const status = exportFile('localidade_list.csv', content, 'text/csv')
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
