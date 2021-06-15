<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allBemSubtraidosFromPecaProcesso" :filter="filter" binary-state-sort
             row-key="name" title="Prejuízos Causados">

      <template v-slot:top-right>
        <q-input v-if="show_filter" v-model="filter" borderless debounce="300" dense filled placeholder="Pesquisa">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>

        <div class="q-pa-md q-gutter-sm">
          <q-btn class="q-ml-sm" flat icon="filter_list" @click="show_filter=!show_filter"/>
          <q-btn color="primary" label="Adicionar Novo" no-caps outline rounded @click="show_dialog = true"/>
        </div>
      </template>
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td key="descricao" :props="props">
            {{ props.row.descricao }}
            <q-popup-edit v-model="props.row.descricao">
              <q-input v-model="props.row.descricao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="valorPrejuizo" :props="props">
            {{ props.row.valorPrejuizo }}
            <q-popup-edit v-model="props.row.valorPrejuizo">
              <q-input v-model="props.row.valorPrejuizo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaBemSubtraido(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click="removeBemSubtraido(props.row)">
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
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar/Actualizar Prejuízos Causados!</div>
          </q-card-section>
         <q-card-section>
          <div v-if="listErrors.length > 0" class="q-pa-sm q-gutter-sm" style="max-width: 550px; max-height: 150px;border-radius: 10px; border: 1px solid #cb4646; margin: 5px; background-color: #ead8da">
            <ul class="list-group alert alert-danger">
              <li class="list-group-item text-negative q-pl-xs text-weight-regular text-caption"
                  v-for="item in listErrors" :key="item">
                {{ item }}
              </li>
            </ul>
          </div>
        </q-card-section>
          <q-separator/>
          <q-card-section class="scroll" style="max-height: 80vh">
            <q-form class="q-gutter-md" @submit.prevent="createBemSubtraido">
              <create-edit-form :descricao.sync="bemSubtraido.descricao"
                                :valorPrejuizo.sync="bemSubtraido.valorPrejuizo"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createBemSubtraido"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import BemSubtraido from 'src/store/models/bemSubtraido/bemSubtraido'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'BemSubtraido',
  data() {
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
        {
          name: 'descricao',
          required: true,
          label: 'Descrição',
          align: 'left',
          field: row => row.descricao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'valorPrejuizo',
          align: 'left',
          label: 'Valor do Prejuizo (MZ)',
          field: row => row.valorPrejuizo,
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
    return this.getAllBemSubtraido()
  },
  props: [
    'pecaProcesso', 'autoEntrada'
  ],
  mounted() {
    let offset = 0
    this.getAllBemSubtraido(offset)
  },
  components: {
    'create-edit-form': require('components/bemSubtraido/createEditForm.vue').default
  },
  metaInfo: {},
  computed: {
    allBemSubtraidosFromPecaProcesso() {
    if(this.pecaProcesso !== undefined && this.pecaProcesso !== null)
        return BemSubtraido.query().where('pecaProcesso_id',this.pecaProcesso.id).get()
      else
       return BemSubtraido.query().where('autoEntrada_id',this.autoEntrada.id).get()

    }
  },
  methods: {
    createBemSubtraido() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
       if(this.pecaProcesso !== undefined && this.pecaProcesso !== null){
            this.bemSubtraido.pecaProcesso_id = this.pecaProcesso.id
            this.bemSubtraido.pecaProcesso = this.pecaProcesso
       }else{
         this.bemSubtraido.autoEntrada_id = this.autoEntrada.id
         this.bemSubtraido.autoEntrada = this.autoEntrada
       }
      if (this.editedIndex > -1) {
         BemSubtraido.api().patch("/bemSubtraido/" + this.bemSubtraido.id, this.bemSubtraido).then(resp => {
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
       BemSubtraido.api().post("/bemSubtraido/", this.bemSubtraido).then(resp => {
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
    close() {
      let offset = 0
      this.getAllBemSubtraido(offset)
      this.listErrors = {}
      this.show_dialog = false
      this.bemSubtraido = {}
      this.props = this.bemSubtraido
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeBemSubtraido(bemSubtraido) {
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
        BemSubtraido.api().delete("/bemSubtraido/" + this.bemSubtraido.id)
      })
    },
    editaBemSubtraido(bemSubtraido) {
      this.editedIndex = 0
      this.bemSubtraido = Object.assign({}, bemSubtraido)
      this.show_dialog = true
    },
     getAllBemSubtraido(offset) {
       if(offset >= 0){
          BemSubtraido.api().get("/bemSubtraido?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllBemSubtraido(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.allBemSubtraidosFromPecaProcesso.map(row =>
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
