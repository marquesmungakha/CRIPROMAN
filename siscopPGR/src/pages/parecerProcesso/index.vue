<template>
<q-page style="min-height: 300px;">
<q-table :columns="columns" :data="allParecerProcessos" :filter="filter" binary-state-sort row-key="name" title="Despacho">

      <template v-slot:top-right>
        <q-input v-if="show_filter" v-model="filter" borderless debounce="300" dense filled placeholder="Pesquisa">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>

        <div class="q-pa-md q-gutter-sm">
          <q-btn class="q-ml-sm" flat icon="filter_list" @click="show_filter=!show_filter"/>
          <q-btn color="primary" label="Adicionar Novo" :disabled="allParecerProcessos.length === 0 ? false : true" no-caps outline rounded @click="show_dialog = true"/>
        </div>
      </template>
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td key="parecer" :props="props">
            {{ props.row.parecer }}
            <q-popup-edit v-model="props.row.parecer">
              <q-input v-model="props.row.parecer" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataRegisto" :props="props">
            {{ props.row.dataRegisto }}
            <q-popup-edit v-model="props.row.dataRegisto" >
              <q-input v-model="props.row.dataRegisto" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoParecer" :props="props">
            <div class="text-pre-wrap">{{ props.row.tipoParecer.designacao }}</div>
            <q-popup-edit v-model="props.row.tipoParecer.designacao">
              <q-input v-model="props.row.tipoParecer.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="destino" :props="props">
            <div class="text-pre-wrap">{{ props.row.destino.designacao }}</div>
            <q-popup-edit v-model="props.row.destino.designacao">
              <q-input v-model="props.row.destino.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="magistrado" :props="props">
           <div class="text-pre-wrap">{{ props.row.magistrado.numero }} - {{ props.row.magistrado.nome }}
              {{ props.row.magistrado.apelido }}
            </div>
            <q-popup-edit v-model="props.row.magistrado.numero">
              <q-input v-model="props.row.magistrado.numero" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <!--router-link :to="`/parecerProcesso/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              </router-link-->
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaParecerProcesso(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeParecerProcesso(props.row)">
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
    <div class="no-margin no-padding">
    <create-edit-form :anexo.sync="parecerProcesso.anexo"
                      :close="close"
                      :createParecerProcesso="createParecerProcesso"
                      :dataRegisto.sync="parecerProcesso.dataRegisto"
                      :parecer.sync="parecerProcesso.parecer"
                      :magistrado.sync="magistrado"
                      :magistrados.sync="allMagistrados"
                      :listErrors="listErrors"
                      :tipoParecer.sync="tipoParecer"
                      :tiposParecer.sync="allTiposParecer"
                      :destino.sync="destino"
                      :destinos.sync="allDestinos"
                      :removeParecerProcesso="removeParecerProcesso"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
    </div>
</q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Magistrado from 'src/store/models/magistrado/magistrado'
import Orgao from 'src/store/models/orgao/orgao'
import ParecerProcesso from 'src/store/models/parecerProcesso/parecerProcesso'
import TipoParecer from 'src/store/models/tipoParecer/tipoParecer'
import Pais from 'src/store/models/pais/pais'
import TipoOrgao from 'src/store/models/tipoOrgao/tipoOrgao'
import Provincia from 'src/store/models/provincia/provincia'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'ParecerProcesso',
  data() {
    return {
      listErrors: [],
      options: [],
      parecerProcesso_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      parecerProcesso: {
        parecer: '',
        dataRegisto: '',
        tipoParecer: {},
        anexo: [],
        destino: {},
        magistrado: {},
        processo: {}
      },
       tipoParecer: {
        designacao: ''
      },
      magistrado: {
        numero: ''
      },
      destino: {
        codigo: '',
        designacao: ''
      },
      columns: [
        {
          name: 'parecer',
          align: 'left',
          label: 'Descrição do Despacho',
          field: row => row.numero,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'dataRegisto',
          align: 'left',
          label: 'Data de Registo',
          field: row => row.dataRegisto,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'tipoParecer',
          align: 'left',
          label: 'ParecerProcesso',
          field: row => row.tipoParecer,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'destino',
          align: 'left',
          label: 'Destino do Processo',
          field: row => row.destino,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'magistrado',
          align: 'left',
          label: 'Magistrado',
          field: row => row.magistrado,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'actions', align: 'left',label: 'Ações', field: 'actions'}
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
    return this.getAllParecerProcesso()
  },
  props: ['processoInstrucaoPreparatoria'],
  mounted() {
    let offset = 0
    this.getAllParecerProcesso(offset)
    this.getAllTipoParecer(offset)
    this.getAllDestino(offset)
    this.getAllMagistrado(offset)
    this.getAllPais(offset)
    this.getAllProvincia(offset)
    this.getAllTipoOrgao(offset)
  },
  components: {
    'create-edit-form': require('components/parecerProcesso/createEditForm.vue').default
  },
  metaInfo: {},
  computed: {
    allMagistrados() {
      return Magistrado.query().all()
    },
    allTiposParecer() {
      return TipoParecer.query().all()
    },
     allDestinos() {
      return Orgao.query().with('distrito.provincia.*').with('provincia.pais').with('tipoOrgao').all()
    },
    allParecerProcessos() {
      return ParecerProcesso.query().with('magistrado').with('tipoParecer').with('destino').where('processo_id',this.processoInstrucaoPreparatoria.id).get()
    }
  },
  methods: {
    createParecerProcesso() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.parecerProcesso.tipoParecer = this.tipoParecer
      this.parecerProcesso.tipoParecer_id = this.tipoParecer.id
      this.parecerProcesso.magistrado_id = this.magistrado.id
      this.parecerProcesso.magistrado = this.magistrado
      this.parecerProcesso.destino_id = this.destino.id
      this.parecerProcesso.destino = this.destino
      this.parecerProcesso.processo_id = this.processoInstrucaoPreparatoria.id
      this.parecerProcesso.processo = this.processoInstrucaoPreparatoria
      //  const image = new Blob([this.parecerProcesso.anexo])
      this.parecerProcesso.anexo = null
      if (this.editedIndex > -1) {
         ParecerProcesso.api().patch("/parecerProcesso/" + this.parecerProcesso.id, this.parecerProcesso).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.parecerProcesso.numero + ' ]'
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
       ParecerProcesso.api().post("/parecerProcesso/", this.parecerProcesso).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.parecerProcesso.numero + ' ]'
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
    this.getAllParecerProcesso(offset)
    this.getAllTipoParecer(offset)
    this.getAllDestino(offset)
    this.getAllMagistrado(offset)
    this.getAllPais(offset)
    this.getAllProvincia(offset)
    this.getAllTipoOrgao(offset)
      this.listErrors = {}
      this.show_dialog = false
      this.parecerProcesso = {}
      this.props = this.parecerProcesso
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeParecerProcesso(parecerProcesso) {
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
          message: 'A informação foi Removida com successo! [ ' + parecerProcesso.numero + ' ]'
        })
         ParecerProcesso.api().delete("/parecerProcesso/" + parecerProcesso.id)
          ParecerProcesso.delete(parecerProcesso.id)
      })
    },
    editaParecerProcesso(parecerProcesso) {
      this.editedIndex = 0
      this.parecerProcesso = Object.assign({}, parecerProcesso)
      this.tipoParecer = TipoParecer.query().find(parecerProcesso.tipoParecer_id)
      this.magistrado = Magistrado.query().find(parecerProcesso.magistrado.id) 
      this.destino = Orgao.query().find(parecerProcesso.destino_id) 
      this.show_dialog = true
    },
    getAllParecerProcesso(offset) {
      if(offset >= 0){
          ParecerProcesso.api().get("/parecerProcesso?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllParecerProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllTipoParecer(offset) {
      if(offset >= 0){
          TipoParecer.api().get("/tipoParecer?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTipoParecer(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllDestino(offset) {
      if(offset >= 0){
          Orgao.api().get("/orgao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllDestino(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllMagistrado(offset) {
      if(offset >= 0){
          Magistrado.api().get("/magistrado?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllMagistrado(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
  getAllProvincia(offset) {
      if(offset >= 0){
          Provincia.api().get("/provincia?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllProvincia(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
   getAllTipoOrgao(offset) {
        if(offset >= 0){
          TipoOrgao.api().get("/tipoOrgao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllTipoOrgao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
        }
    },
   getAllPais(offset) {
      if(offset >= 0){
          Pais.api().get("/pais?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllPais(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.allParecerProcessos.map(row =>
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
      const status = exportFile('parecerProcesso_list.csv', content, 'text/csv')
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
