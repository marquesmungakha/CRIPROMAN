<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allAlocacaos" :filter="filter" binary-state-sort row-key="name" title="Alocacao">

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
          <q-td key="data" :props="props">
            {{ props.row.data }}
            <q-popup-edit v-model="props.row.data">
              <q-input v-model="props.row.data" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="descricao" :props="props">
            {{ props.row.descricao }}
            <q-popup-edit v-model="props.row.descricao">
              <q-input v-model="props.row.descricao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="funcao" :props="props">
            {{ props.row.funcao.designacao }}
            <q-popup-edit v-model="props.row.funcao.designacao" title="Update funcao">
              <q-input v-model="props.row.funcao.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="quadro" :props="props">
            {{ props.row.quadro.numero }} - {{ props.row.quadro.nome }}
            {{ props.row.quadro.apelido }}
            <q-popup-edit v-model="props.row.quadro.numero" title="Update quadro">
              <q-input v-model="props.row.quadro.numero" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="activo" :props="props">
            {{ props.row.activo }}
            <q-popup-edit v-model="props.row.activo" title="Update activo">
              <q-input v-model="props.row.activo" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaAlocacao(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeAlocacao(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar Alocação!</div>
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
          <q-card-section class="scroll" style="max-height: 70vh">
            <q-form class="q-gutter-md" @submit.prevent="createAlocacao">
              <create-edit-form :activo.sync="alocacao.activo"
                                :data.sync="alocacao.data"
                                :descricao.sync="alocacao.descricao"
                                :funcao.sync="funcao"
                                :funcoes.sync="allFuncoes"
                                :quadro.sync="quadro"
                                :quadros.sync="allQuadros"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createAlocacao"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Funcao from 'src/store/models/funcao/funcao'
import Quadro from 'src/store/models/quadro/quadro'
import Alocacao from 'src/store/models/alocacao/alocacao'
import Orgao from 'src/store/models/orgao/orgao'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Alocacao',
  data() {
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
        {
          name: 'data',
          required: true,
          label: 'Data da Alocação',
          align: 'left',
          field: row => row.data,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'descricao',
          align: 'left',
          label: 'Descrição',
          field: row => row.descricao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'funcao',
          align: 'left',
          label: 'Função',
          field: row => row.funcao.id,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'quadro',
          align: 'left',
          label: 'Membro',
          field: row => row.quadro.id,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'activo',
          align: 'left',
          label: 'Activo',
          field: row => row.activo,
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
    return this.getAllAlocacao()
  },
  props: [
    'orgao'
  ],
  mounted() {
    this.getAllAlocacao()
    this.getAllFuncao()
    this.getAllQuadro()
  },
  components: {
    'create-edit-form': require('components/alocacao/createEditForm.vue').default
  },
  metaInfo: {},
  computed: {
    allAlocacaos() {
      return Alocacao.query().with('funcao').with('quadro').where('orgao_id',this.orgao.id).all()
    },
    allFuncoes() {
      return Funcao.query().all()
    },
    allQuadros() {
      return Quadro.query().all()
    }
  },
  methods: {
    createAlocacao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.orgao = Orgao.query().with('provincia.pais').with('distrito.provincia.pais').with('tipoOrgao').find(this.orgao.id)
      this.alocacao.funcao_id = this.funcao.id
      this.alocacao.quadro_id = this.quadro.id
      this.alocacao.orgao_id = this.orgao.id
      this.alocacao.funcao = this.funcao
      this.alocacao.quadro = this.quadro
      this.alocacao.orgao = this.orgao
      if (this.editedIndex > -1) {
        Alocacao.api().patch("/alocacao/" + this.alocacao.id, this.alocacao).then(resp => {
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
        Alocacao.api().post("/alocacao/", this.alocacao).then(resp => {
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
    close() { 
      this.getAllAlocacao()
      this.getAllFuncao()
      this.getAllQuadro()
      this.listErrors = {}
      this.show_dialog = false
      this.alocacao = {}
      this.props = this.alocacao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeAlocacao(alocacao) {
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
        Alocacao.api().delete("/alocacao/" + this.alocacao.id)
      })
    },
    editaAlocacao(alocacao) {
      this.editedIndex = this.allAlocacaos.indexOf(alocacao)
      this.alocacao = Object.assign({}, alocacao)
      this.alocacao.funcao.id = Funcao.query().find(this.funcao.id)
      this.alocacao.quadro.id = Quadro.query().find(this.quadro.id)
      this.show_dialog = true
    },
    getAllAlocacao() {
      Alocacao.api().get('/alocacao?offset=0&max=1000000')
    },
    getAllFuncao() {
      Funcao.api().get('/funcao?offset=0&max=1000000')
    },
     getAllQuadro() {
      Quadro.api().get('/quadro?offset=0&max=1000000')
    }
    },
    exportTable() {
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
</script>
