<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allSituacaoPrisionals" :filter="filter" binary-state-sort
             row-key="name" title="Situacão Prisional">

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
          <q-td key="activo" :props="props">
          <q-chip :color="(props.row.activo == true)?'green':(props.row.activo == false?'grey':'red')"
              text-color="white" dense class="text-weight-bolder" square
              style="width: 100px" :icon="(props.row.activo == true)?'published_with_changes':(props.row.activo == false?'unpublished':'do_disturb_on')">
              <span v-if="props.row.activo == true">Activo</span>
              <span v-else>Desactivo</span>
            </q-chip>
            <q-popup-edit v-model="props.row.activo" title="Update estado">
              <q-input v-model="props.row.activo" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/situacaoPrisional/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm
                     @click.stop="editaSituacaoPrisional(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round
                     size=sm @click.stop="removeSituacaoPrisional(props.row)">
                <q-tooltip content-class="bg-red text-white shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Remover
                </q-tooltip>
                </q-btn>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :close="close"
                      :codigo.sync="situacaoPrisional.codigo"
                      :createSituacaoPrisional="createSituacaoPrisional"
                      :designacao.sync="situacaoPrisional.designacao"
                      :activo.sync="situacaoPrisional.activo"
                      :listErrors="listErrors"
                      :removeSituacaoPrisional="removeSituacaoPrisional"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import SituacaoPrisional from 'src/store/models/situacaoPrisional/situacaoPrisional'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'SituacaoPrisional',
  data() {
    return {
      listErrors: [],
      situacaoPrisional_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      situacaoPrisional: {
        codigo: '',
        designacao: '',
        activo: false
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
        {
          name: "activo",
          align: "left",
          label: "Estado",
          field: row => row.activo,
          sortable: true
        },
        { 
          name: 'actions', 
          label: 'Movimento', 
          field: 'actions'
        }
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
    return this.getAllSituacaoPrisional()
  },
  mounted() {
    let offset = 0
    this.getAllSituacaoPrisional(offset)
  },
  components: {
    'create-edit-form': require('components/situacaoPrisional/createEditForm.vue').default
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
    allSituacaoPrisionals() {
      return SituacaoPrisional.query().all()
    }
  },
  methods: {
    createSituacaoPrisional() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)
      if (this.editedIndex > -1) {
        SituacaoPrisional.api().patch("/situacaoPrisional/" + this.situacaoPrisional.id, this.situacaoPrisional).then(resp => {
        this.submitting = false
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
            message: 'A informação foi actualizada com successo!! [ ' + this.situacaoPrisional.designacao + ' ]'
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
        SituacaoPrisional.api().post("/situacaoPrisional/", this.situacaoPrisional).then(resp => {
        this.submitting = false
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi inserida com successo! [ ' + this.situacaoPrisional.designacao + ' ]'
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
      let offset = 0
    this.getAllSituacaoPrisional(offset)
      this.show_dialog = false
      this.situacaoPrisional = {}
      this.props = this.situacaoPrisional
      setTimeout(() => {
        // this.situacaoPrisional = Object.assign({}, null)
        this.editedIndex = -1
      }, 300)
    },
    removeSituacaoPrisional(situacaoPrisional) {
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
          message: 'A informação foi Removida com successo! [ ' + situacaoPrisional.designacao + ' ]'
        })
        SituacaoPrisional.api().delete("/situacaoPrisional/" + situacaoPrisional.id)
        SituacaoPrisional.delete(situacaoPrisional.id)
      })
    },
    getAllSituacaoPrisional(offset) {
        if(offset >= 0){
          SituacaoPrisional.api().get("/situacaoPrisional?offset="+offset+"&max=100").then(resp => {
        this.submitting = false
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllSituacaoPrisional(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    editaSituacaoPrisional(situacaoPrisional) {
      this.editedIndex = 0
      this.situacaoPrisional = Object.assign({}, situacaoPrisional)
      this.show_dialog = true
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.allSituacaoPrisionals.map(row =>
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
      const status = exportFile('situacaoPrisional_list.csv', content, 'text/csv')
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
