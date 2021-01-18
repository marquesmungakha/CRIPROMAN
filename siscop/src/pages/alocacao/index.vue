<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Alocacao" :data="allAlocacaos" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="data" :props="props">
              {{ props.row.data }}
              <q-popup-edit v-model="props.row.data">
                <q-input v-model="props.row.data" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="descricao" :props="props">
              {{ props.row.descricao }}
              <q-popup-edit v-model="props.row.descricao">
                <q-input v-model="props.row.descricao" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="funcao" :props="props">
              {{ getFuncao(props.row.funcao.id).designacao}}
              <q-popup-edit v-model="props.row.funcao.id" title="Update funcao">
                <q-input v-model="props.row.funcao.id" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
                <q-td key="quadro" :props="props">
              {{ getQuadro(props.row.quadro.id).numero }} - {{ getQuadro(props.row.quadro.id).nome }} {{ getQuadro(props.row.quadro.id).apelido }}
              <q-popup-edit v-model="props.row.quadro.id" title="Update quadro">
                <q-input v-model="props.row.quadro.id" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="activo" :props="props">
              {{ props.row.activo }}
              <q-popup-edit v-model="props.row.activo" title="Update activo">
                <q-input v-model="props.row.activo" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <q-btn round glossy icon="edit" color="blue" @click="editaAlocacao(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeAlocacao(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
   <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Alocação!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
            <q-separator />
            <q-card-section style="max-height: 70vh" class="scroll">
                    <q-form @submit.prevent="createAlocacao" class="q-gutter-md">
                        <create-edit-form :data.sync="alocacao.data"
                                          :descricao.sync="alocacao.descricao"
                                          :funcao.sync="funcao"
                                          :funcoes.sync="allFuncoes"
                                          :quadro.sync="quadro"
                                          :quadros.sync="allQuadros"
                                          :activo.sync="alocacao.activo"/>
                      </q-form>
            </q-card-section>
            <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createAlocacao" color="teal" label="Gravar" />
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
  name: 'Alocacao',
  data () {
    return {
      listErrors: [],
      alocacao_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      alocacao: {
        data: '',
        descricao: '',
        funcao: {},
        quadro: {},
        activo: true,
        apelido: ''
      },
      funcao: {
        codigo: '',
        designacao: ''
      },
      quadro: {
        numero: '',
        nome: '',
        apelido: ''
      },
      columns: [
        { name: 'data', required: true, label: 'Data da Alocação', align: 'left', field: row => row.data, format: val => `${val}`, sortable: true },
        { name: 'descricao', align: 'left', label: 'Descrição', field: row => row.descricao, format: val => `${val}`, sortable: true },
        { name: 'funcao', align: 'left', label: 'Função', field: row => row.funcao.id, format: val => `${val}`, sortable: true },
        { name: 'quadro', align: 'left', label: 'Membro', field: row => row.quadro.id, format: val => `${val}`, sortable: true },
        { name: 'activo', align: 'left', label: 'Activo', field: row => row.activo, format: val => `${val}`, sortable: true },
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
    return store.dispatch('alocacao/getAllAlocacao')
  },
  props: [
    'orgao'
  ],
  mounted () {
    this.$store.dispatch('funcao/getAllFuncao')
    this.$store.dispatch('inspector/getAllInspector')
  },
  components: {
    'create-edit-form': require('components/alocacao/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allAlocacaos () {
      return this.$store.getters['alocacao/allAlocacao'].filter(alocacao => alocacao.orgao.id === this.orgao.id)
    },
    allFuncoes () {
      return this.$store.getters['funcao/allFuncao']
    },
    allQuadros () {
      return this.$store.getters['inspector/allInspector']
    }
  },
  methods: {
    ...mapActions('alocacao', ['getAllAlocacao', 'addNewAlocacao', 'updateAlocacao', 'deleteAlocacao']),
    createAlocacao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.alocacao.funcao = this.funcao
      this.alocacao.quadro = this.quadro
      this.alocacao.orgao = this.orgao.id
      console.log('Alocacao' + this.alocacao[0])
      if (this.editedIndex > -1) {
        this.updateAlocacao(this.alocacao).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.alocacao.designacao + ' ]'
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
        this.addNewAlocacao(this.alocacao).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.alocacao.designacao + ' ]'
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
      this.$store.dispatch('alocacao/getAllAlocacao')
      this.show_dialog = false
      this.alocacao = {}
      this.props = this.alocacao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeAlocacao (alocacao) {
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
          message: 'A informação foi Removida com successo! [ ' + alocacao.designacao + ' ]'
        })
        this.deleteAlocacao(alocacao)
      })
    },
    editaAlocacao (alocacao) {
      this.editedIndex = this.allAlocacaos.indexOf(alocacao)
      this.alocacao = Object.assign({}, alocacao)
      this.alocacao.funcao.id = this.funcao.id
      this.alocacao.quadro.id = this.quadro.id
      this.show_dialog = true
    },
    getFuncao (id) {
      const funcoes = this.allFuncoes.filter(funcao => funcao.id === id)
      if (funcoes.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return funcoes[0] }
    },
    getQuadro (id) {
      const quadros = this.allQuadros.filter(quadro => quadro.id === id)
      if (quadros.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return quadros[0] }
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.alocacao.alocacaos.map(row =>
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
      const status = exportFile('alocacao_list.csv', content, 'text/csv')
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
