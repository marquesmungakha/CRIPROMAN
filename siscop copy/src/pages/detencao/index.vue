<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allDetencaos" :filter="filter" binary-state-sort row-key="name" title="Detencao">

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
          <q-td key="localDetencao" :props="props">
            <div class="text-pre-wrap">{{ props.row.localDetencao }}</div>
            <q-popup-edit v-model="props.row.localDetencao">
              <q-input v-model="props.row.localDetencao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="motivoDetencao" :props="props">
            <div class="text-pre-wrap">{{ props.row.motivoDetencao.designacao }}</div>
            <q-popup-edit v-model="props.row.motivoDetencao.designacao">
              <q-input v-model="props.row.motivoDetencao.designacao" autofocus dense></q-input>
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
              <router-link :to="`/detencao/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm />
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaDetencao(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeDetencao(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :anexo.sync="detencao.anexo"
                      :close="close"
                      :createDetencao="createDetencao"
                      :dataAbertura.sync="detencao.dataAbertura"
                      :descricao.sync="detencao.descricao"
                      :inspector.sync="inspector"
                      :inspectors.sync="allInspectors"
                      :listErrors="listErrors"
                      :localDetencao.sync="detencao.localDetencao"
                      :motivoDetencao.sync="motivoDetencao"
                      :motivoDetencaos.sync="allMotivoDetencaos"
                      :numero.sync="detencao.numero"
                      :removeDetencao="removeDetencao"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Inspector from 'src/store/models/inspector/inspector'
import MotivoDetencao from 'src/store/models/motivoDetencao/motivoDetencao'
import Detencao from 'src/store/models/detencao/detencao'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Detencao',
  data() {
    return {
      listErrors: [],
      options: [],
      detencao_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      detencao: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        localDetencao: '',
        anexo: [],
        motivoDetencao: {},
        inspector: {},
        processo: {},
        orgao: {}
      },
      motivoDetencao: {
        designacao: ''
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
          name: 'localDetencao',
          align: 'left',
          label: 'Local Detenção',
          field: row => row.localDetencao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'motivoDetencao',
          align: 'left',
          label: 'Motivo de Detenção',
          field: row => row.motivoDetencao.id,
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
    return this.getAllDetencao()
  },
  props: ['processoInvestigacao'],
  mounted() {
    this.getAllDetencao()
    this.getAllMotivoDetencao()
    this.getAllInspector()
  },
  components: {
    'create-edit-form': require('components/detencao/createEditForm.vue').default
  },
  metaInfo: {},
  computed: {
    allInspectors() {
      return Inspector.query().all()
    },
    allMotivoDetencaos() {
      return MotivoDetencao.query().all()
    },
    allDetencaos() {
      return Detencao.query().with('inspector').with('motivoDetencao').where('processo_id',this.processoInvestigacao.id).get()
    }
  },
  methods: {
    createDetencao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.detencao.motivoDetencao = this.motivoDetencao
      this.detencao.motivoDetencao_ID = this.motivoDetencao.id
      this.detencao.inspector_id = this.inspector
      this.detencao.inspector = this.inspector
      this.detencao.processo_id = this.processoInvestigacao.id
      this.detencao.processo = this.processoInvestigacao
      //  const image = new Blob([this.detencao.anexo])
      this.detencao.anexo = null
      if (this.editedIndex > -1) {
         Detencao.api().patch("/detencao/" + this.detencao.id, this.detencao).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.detencao.numero + ' ]'
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
       Detencao.api().post("/detencao/", this.detencao).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.detencao.numero + ' ]'
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
      this.getAllDetencao()
      this.getAllMotivoDetencao()
      this.getAllInspector()
      this.listErrors = {}
      this.show_dialog = false
      this.detencao = {}
      this.props = this.detencao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeDetencao(detencao) {
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
          message: 'A informação foi Removida com successo! [ ' + detencao.numero + ' ]'
        })
         Detencao.api().delete("/detencao/" + this.detencao.id)
      })
    },
    editaDetencao(detencao) {
      this.editedIndex = 0
      this.detencao = Object.assign({}, detencao)
      this.motivoDetencao = MotivoDetencao.query().find(detencao.motivoDetencao_id)
      this.inspector = Inspector.query().find(detencao.inspector.id) 
      this.show_dialog = true
    },
    getAllDetencao() {
      Detencao.api().get("/detencao?offset=0&max=1000000")
    },
    getAllMotivoDetencao() {
      MotivoDetencao.api().get("/motivoDetencao?offset=0&max=1000000")
    },
    getAllInspector() {
      Inspector.api().get("/inspector?offset=0&max=1000000")
    },
    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.detencao.detencaos.map(row =>
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
      const status = exportFile('detencao_list.csv', content, 'text/csv')
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
