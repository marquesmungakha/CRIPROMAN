<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allAcarreacaos" :filter="filter" binary-state-sort row-key="name"
             title="Auto de Acarreação">

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
          <q-td key="numero" :props="props">
            {{ props.row.numero }}
            <q-popup-edit v-model="props.row.numero" title="Update numero">
              <q-input v-model="props.row.numero" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataAbertura" :props="props">
            {{ props.row.dataAbertura }}
            <q-popup-edit v-model="props.row.dataAbertura" title="Update dataAbertura">
              <q-input v-model="props.row.dataAbertura" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="inspector" :props="props">
            <div class="text-pre-wrap">{{ props.row.inspector.numero }} - {{ props.row.inspector.nome }}
              {{ props.row.inspector.apelido }}
            </div>
            <q-popup-edit v-model="props.row.inspector.numero">
              <q-input v-model="props.row.inspector.numero" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/acarreacao/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm />
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaAcarreacao(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click="removeAcarreacao(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :anexo.sync="acarreacao.anexo"
                      :close="close"
                      :createAcarreacao="createAcarreacao"
                      :dataAbertura.sync="acarreacao.dataAbertura"
                      :descricao.sync="acarreacao.descricao"
                      :inspector.sync="inspector"
                      :inspectors.sync="allInspectors"
                      :listErrors="listErrors"
                      :numero.sync="acarreacao.numero"
                      :removeAcarreacao="removeAcarreacao"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Acarreacao from 'src/store/models/acarreacao/acarreacao'
import Orgao from 'src/store/models/orgao/orgao'
import Inspector from 'src/store/models/inspector/inspector'


function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Acarreacao',
  data() {
    return {
      listErrors: [],
      options: [],
      acarreacao_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      acarreacao: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        anexo: [],
        inspector: {},
        orgao: {}
      },
      inspector: {
        numero: ''
      },
      orgao: {
        codigo: '',
        designacao: ''
      },
      columns: [
        {
          name: 'numero',
          align: 'left',
          label: 'Número do Auto',
          field: row => row.numero,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'dataAbertura',
          align: 'left',
          label: 'Data de Abertura',
          field: row => row.dataAbertura,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'inspector',
          align: 'left',
          label: 'Inspector',
          field: row => row.inspector.id,
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
    return this.getAllAcarreacaos()
  },
  props: [
    'processoInvestigacao'
  ],
  mounted() {
    let offset = 0
    this.getAllAcarreacaos(offset)
    this.getAllInspector(offset)
  },
  components: {
    'create-edit-form': require('components/acarreacao/createEditForm.vue').default
  },
  created() {

  },
  metaInfo: {},
  computed: {
    allInspectors() {
      return Inspector.query().all()
    },
    allOrgaos() {
      return Orgao.query().all()
    },
    allAcarreacaos() {
      return Acarreacao.query().with('inspector').where('processo_id',this.processoInvestigacao.id).get()
    }
  },
  methods: {
    createAcarreacao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.acarreacao.inspector_id = this.inspector
      this.acarreacao.processo_id = this.processoInvestigacao.id
      this.acarreacao.inspector = this.inspector
      this.acarreacao.processo = this.processoInvestigacao
      // this.acarreacao.orgao = this.$store.state.orgao.orgaos[0]
      //  const image = new Blob([this.acarreacao.anexo])
      this.acarreacao.anexo = null
      if (this.editedIndex > -1) {
        Acarreacao.api().patch("/acarreacao/" + this.acarreacao.id, this.acarreacao).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.acarreacao.designacao + ' ]'
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
        Acarreacao.api().post("/acarreacao/", this.acarreacao).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.acarreacao.numero + ' ]'
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
      this.getAllAcarreacaos()
      this.getAllInspector()
      this.show_dialog = false
      this.acarreacao = {}
      this.props = this.acarreacao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeAcarreacao(acarreacao) {
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
          message: 'A informação foi Removida com successo! [ ' + acarreacao.numero + ' ]'
        })
        Acarreacao.api().delete("/acarreacao/" + acarreacao.id)
      })
    },
    editaAcarreacao(acarreacao) {
      this.editedIndex = 0
      this.acarreacao = Object.assign({}, acarreacao)
      this.inspector = Inspector.query().find(acarreacao.inspector.id)
      this.show_dialog = true
    },
    getAllAcarreacaos(offset) {
      Acarreacao.api().get("/acarreacao?offset="+offset+"&max=1000").then(resp => {
          console.log(resp)
          offset = offset + 1

          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllAcarreacaos, 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
    },
    getAllInspector(offset) {
      Inspector.api().get("/inspector?offset="+offset+"&max=1000").then(resp => {
          console.log(resp)
          offset = offset + 1

          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllInspector, 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
    },
    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.acarreacao.acarreacaos.map(row =>
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
      const status = exportFile('acarreacao_list.csv', content, 'text/csv')
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
