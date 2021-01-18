<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Bem Subtraido" :data="allBemSubtraidosFromPecaProcesso" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="descricao" :props="props">
              {{ props.row.descricao }}
              <q-popup-edit v-model="props.row.descricao">
                <q-input v-model="props.row.descricao" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="valorPrejuizo" :props="props">
              {{ props.row.valorPrejuizo }}
              <q-popup-edit v-model="props.row.valorPrejuizo">
                <q-input v-model="props.row.valorPrejuizo" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <q-btn round glossy icon="edit" color="blue" @click="editaBemSubtraido(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeBemSubtraido(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
   <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
         <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Bem Subtraido!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
            <q-separator />
            <q-card-section style="max-height: 70vh" class="scroll">
                    <q-form @submit.prevent="createBemSubtraido" class="q-gutter-md">
                        <create-edit-form :descricao.sync="bemSubtraido.descricao"
                                          :valorPrejuizo.sync="bemSubtraido.valorPrejuizo"/>
                      </q-form>
            </q-card-section>
            <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createBemSubtraido" color="teal" label="Gravar" />
            <q-btn label="Cancelar" type="reset" @click="close" color="negative" v-close-popup />
        </q-card-actions>
        </q-card>
    </q-dialog>
   </div>
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
  name: 'BemSubtraido',
  data () {
    return {
      listErrors: [],
      bemSubtraido_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      bemSubtraido: {
        descricao: '',
        valorPrejuizo: '',
        pecaProcesso: {}
      },
      columns: [
        { name: 'descricao', required: true, label: 'Descrição', align: 'left', field: row => row.descricao, format: val => `${val}`, sortable: true },
        { name: 'valorPrejuizo', align: 'left', label: 'Valor do Prejuizo (MZ)', field: row => row.valorPrejuizo, format: val => `${val}`, sortable: true },
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
    return store.dispatch('bemSubtraido/getAllBemSubtraido')
  },
  props: [
    'autoEntrada'
  ],
  mounted () {
    this.$store.dispatch('bemSubtraido/getAllBemSubtraido')
  },
  components: {
    'create-edit-form': require('components/bemSubtraido/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allBemSubtraidos () {
      return this.$store.getters['bemSubtraido/allBemSubtraido'].filter(bemSubtraido => bemSubtraido.pecaProcesso != null)
    },
    allBemSubtraidosFromPecaProcesso () {
      return this.allBemSubtraidos.filter(bemSubtraido => bemSubtraido.pecaProcesso.id === this.autoEntrada.id)
    }
  },
  methods: {
    ...mapActions('bemSubtraido', ['getAllBemSubtraido', 'addNewBemSubtraido', 'updateBemSubtraido', 'deleteBemSubtraido']),
    createBemSubtraido () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.bemSubtraido.pecaProcesso = this.autoEntrada
      console.log('BemSubtraido' + this.bemSubtraido[0])
      if (this.editedIndex > -1) {
        this.updateBemSubtraido(this.bemSubtraido).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.bemSubtraido.nome + ' ]'
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
        this.addNewBemSubtraido(this.bemSubtraido).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.bemSubtraido.nome + ' ]'
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
      this.$store.dispatch('bemSubtraido/getAllBemSubtraido')
      this.show_dialog = false
      this.bemSubtraido = {}
      this.props = this.bemSubtraido
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeBemSubtraido (bemSubtraido) {
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
          message: 'A informação foi Removida com successo! [ ' + bemSubtraido.nome + ' ]'
        })
        this.deleteBemSubtraido(bemSubtraido)
      })
    },
    editaBemSubtraido (bemSubtraido) {
      this.editedIndex = this.allBemSubtraidos.indexOf(bemSubtraido)
      this.bemSubtraido = Object.assign({}, bemSubtraido)
      this.show_dialog = true
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.bemSubtraido.bemSubtraidos.map(row =>
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
      const status = exportFile('bemSubtraido_list.csv', content, 'text/csv')
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
