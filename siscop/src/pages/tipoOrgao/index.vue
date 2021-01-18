<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Tipo de Orgão" :data="allTipoOrgaos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
              <q-popup-edit v-model="props.row.designacao" title="Update designação">
                <q-input v-model="props.row.designacao" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/tipoOrgao/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click.stop="editaTipoOrgao(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click.stop="removeTipoOrgao(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="tipoOrgao.codigo"
                    :designacao.sync="tipoOrgao.designacao"
                    :submitting="submitting"
                    :close="close"
                    :createTipoOrgao="createTipoOrgao"
                    :removeTipoOrgao="removeTipoOrgao"/>
  </q-page>
</template>

<script>
import { mapGetters, mapActions } from 'vuex'
import { exportFile } from 'quasar'

function wrapCsvValue (val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'TipoOrgao',
  data () {
    return {
      listErrors: [],
      tipoOrgao_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      tipoOrgao: {
        codigo: '',
        designacao: ''
      },
      columns: [
        { name: 'codigo', required: true, label: 'Código', align: 'left', field: row => row.codigo, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designação', field: row => row.designacao, format: val => `${val}`, sortable: true },
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
    return store.dispatch('tipoOrgao/getAllTipoOrgao', currentRoute.params.id)
  },
  mounted () {
  },
  components: {
    'create-edit-form': require('components/tipoOrgao/createEditForm.vue').default
  },
  created () {
  },
  metaInfo: {
  },
  computed: {
    ...mapGetters('tipoOrgao', ['allTipoOrgao']),
    allTipoOrgaos () {
      return this.$store.state.tipoOrgao.tipoOrgaos
    }
  },
  methods: {
    ...mapActions('tipoOrgao', ['getAllTipoOrgao', 'addNewTipoOrgao', 'updateTipoOrgao', 'deleteTipoOrgao']),
    createTipoOrgao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      if (this.editedIndex > -1) {
        this.updateTipoOrgao(this.tipoOrgao).then(resp => {
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
            message: 'A informação foi actualizada com successo!! [ ' + this.tipoOrgao.designacao + ' ]'
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
        this.addNewTipoOrgao(this.tipoOrgao).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi inserida com successo! [ ' + this.tipoOrgao.designacao + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log(error)
          // if (error.request.status !== 0) {
          //   const arrayErrors = JSON.parse(error.request.response)
          //   if (arrayErrors.total == null) {
          //     this.listErrors.push(arrayErrors.message)
          //   } else {
          //     arrayErrors._embedded.errors.forEach(element => {
          //       this.listErrors.push(element.message)
          //     })
          //   }
          //   console.log(this.listErrors)
          // }
        })
      }
    },
    close () {
      this.$store.dispatch('tipoOrgao/getAllTipoOrgao')
      this.show_dialog = false
      this.tipoOrgao = {}
      this.props = this.tipoOrgao
      setTimeout(() => {
        // this.tipoOrgao = Object.assign({}, null)
        this.editedIndex = -1
      }, 300)
    },
    removeTipoOrgao (tipoOrgao) {
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
          message: 'A informação foi Removida com successo! [ ' + tipoOrgao.designacao + ' ]'
        })
        this.deleteTipoOrgao(tipoOrgao)
      })
    },
    editaTipoOrgao (tipoOrgao) {
      this.editedIndex = this.$store.state.tipoOrgao.tipoOrgaos.indexOf(tipoOrgao)
      this.tipoOrgao = Object.assign({}, tipoOrgao)
      this.show_dialog = true
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.tipoOrgao.tipoOrgaos.map(row =>
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
      const status = exportFile('tipoOrgao_list.csv', content, 'text/csv')
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
