<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Orgao" :data="allOrgaos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="sigla" :props="props">
              {{ props.row.sigla }}
              <q-popup-edit v-model="props.row.sigla">
                <q-input v-model="props.row.sigla" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="designacao" :props="props">
              {{ props.row.designacao }}
              <q-popup-edit v-model="props.row.designacao" title="Update designacao">
                <q-input v-model="props.row.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
                <q-td key="nivel" :props="props">
              {{ props.row.nivel }}
              <q-popup-edit v-model="props.row.nivel" title="Update nivel">
                <q-input v-model="props.row.nivel" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="tipoOrgao" :props="props">
              <div class="text-pre-wrap">{{  getTipoOrgao (props.row.tipoOrgao.id ).designacao }}</div>
              <q-popup-edit v-model="props.row.tipoOrgao.id">
                <q-input v-model="props.row.tipoOrgao.id" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="provincia" :props="props">
              <div class="text-pre-wrap">{{  getProvincia (props.row.provincia.id ).designacao }}</div>
              <q-popup-edit v-model="props.row.provincia.id">
                <q-input v-model="props.row.provincia.id" dense autofocus ></q-input>
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
              <router-link :to="`/orgao/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaOrgao(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeOrgao(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :sigla.sync="orgao.sigla"
                    :designacao.sync="orgao.designacao"
                    :tipoOrgao.sync="tipoOrgao"
                    :nivel.sync="orgao.nivel"
                    :endereco.sync="orgao.endereco"
                    :provincia.sync="provincia"
                    :distrito.sync="distrito"
                    :tipoOrgaos="allTipoOrgaos"
                    :provincias="allProvincias"
                    :distritos="allDistritosFromProvincia"
                    :submitting="submitting"
                    :close="close"
                    :createOrgao="createOrgao"
                    :removeOrgao="removeOrgao"/>
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
  name: 'Orgao',
  data () {
    return {
      listErrors: [],
      options: [],
      orgao_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      orgao: {
        sigla: '',
        designacao: '',
        nivel: 0,
        endereco: '',
        provincia: {},
        distrito: {},
        class: 'org.devinthesky.unidadeorganica.Orgao'
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      tipoOrgao: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'sigla', required: true, label: 'Sigla', align: 'left', field: row => row.sigla, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designacao', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'nivel', align: 'left', label: 'Nível', field: row => row.nivel, format: val => `${val}`, sortable: true },
        { name: 'tipoOrgao', align: 'left', label: 'Tipo de Orgão', field: row => row.tipoOrgao.id, format: val => `${val}`, sortable: true },
        { name: 'provincia', align: 'left', label: 'Província', field: row => row.provincia.id, format: val => `${val}`, sortable: true },
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
    return store.dispatch('orgao/getAllOrgao')
  },
  mounted () {
    this.$store.dispatch('tipoOrgao/getAllTipoOrgao')
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
    // this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
    // this.$store.dispatch('cidade/getAllCidade')
    // this.$store.dispatch('bairro/getAllBairro')
    // this.$store.dispatch('localidade/getAllLocalidade')
  },
  components: {
    'create-edit-form': require('components/orgao/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allTipoOrgaos () {
      return this.$store.getters['tipoOrgao/allTipoOrgao']
    },
    allProvincias () {
      return this.$store.getters['provincia/allProvincia']
    },
    allDistritos () {
      return this.$store.getters['distrito/allDistrito']
    },
    allDistritosFromProvincia () {
      return this.allDistritos.filter(distrito => distrito.provincia.id === this.provincia.id)
    },
    // allPostoAdministrativos () {
    //   return this.$store.getters['postoAdministrativo/allPostoAdministrativo']
    // },
    // allCidades () {
    //   return this.$store.getters['cidade/allCidade']
    // },
    // allBairros () {
    //   return this.$store.getters['bairro/allBairro']
    // },
    // allLocalidades () {
    //   return this.$store.getters['localidade/allLocalidade']
    // },
    allOrgaos () {
      return this.$store.getters['orgao/allOrgao']
    }
  },
  methods: {
    ...mapActions('orgao', ['getAllOrgao', 'addNewOrgao', 'updateOrgao', 'deleteOrgao']),
    createOrgao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      if (this.distrito == null) { this.distrito = {} }
      this.orgao.distrito = this.distrito
      this.orgao.provincia = this.provincia
      this.orgao.tipoOrgao = this.tipoOrgao
      if (this.editedIndex > -1) {
        this.updateOrgao(this.orgao).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.orgao.designacao + ' ]'
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
        this.addNewOrgao(this.orgao).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.orgao.designacao + ' ]'
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
      this.$store.dispatch('orgao/getAllOrgao')
      this.$store.dispatch('tipoOrgao/getAllTipoOrgao')
      this.$store.dispatch('provincia/getAllProvincia')
      this.$store.dispatch('distrito/getAllDistrito')
      this.show_dialog = false
      this.orgao = {}
      this.props = this.orgao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeOrgao (orgao) {
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
          message: 'A informação foi Removida com successo! [ ' + orgao.designacao + ' ]'
        })
        this.deleteOrgao(orgao)
      })
    },
    editaOrgao (orgao) {
      this.editedIndex = this.allOrgaos.indexOf(orgao)
      this.orgao = Object.assign({}, orgao)
      this.tipoOrgao = this.allTipoOrgaos.filter(tipoOrgao => tipoOrgao.id === orgao.tipoOrgao.id)[0]
      this.provincia = this.allProvincias.filter(provincia => provincia.id === orgao.provincia.id)[0]
      this.distrito = this.allDistritos.filter(distrito => distrito.id === orgao.distrito.id)[0]
      this.show_dialog = true
    },
    getTipoOrgao (id) {
      const tipoOrgaos = this.allTipoOrgaos.filter(tipoOrgao => tipoOrgao.id === id)
      if (tipoOrgaos.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return tipoOrgaos[0] }
    },
    getProvincia (id) {
      const provincias = this.allProvincias.filter(provincia => provincia.id === id)
      if (provincias.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return provincias[0] }
    },
    getDistrito (id) {
      const distritos = this.allDistritos.filter(distrito => distrito.id === id)
      if (distritos.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return distritos[0] }
    },
    filterFn (val, update, abort) {
      const stringOptions = this.allTipoOrgaos
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(tipoOrgao => tipoOrgao)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(tipoOrgao => tipoOrgao)
            .filter(tipoOrgao => {
              return tipoOrgao &&
                   tipoOrgao.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.orgao.tipoOrgao = val
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.orgao.orgaos.map(row =>
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
      const status = exportFile('orgao_list.csv', content, 'text/csv')
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
