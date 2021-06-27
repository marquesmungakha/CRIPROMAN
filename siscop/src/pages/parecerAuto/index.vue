<template>
<q-page style="min-height: 300px;">
<q-table :columns="columns" :data="allParecerAutos" :filter="filter" binary-state-sort row-key="name" title="Despacho">

      <template v-slot:top-right>
        <q-input v-if="show_filter" v-model="filter" borderless debounce="300" dense filled placeholder="Pesquisa">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>

        <div class="q-pa-md q-gutter-sm">
          <q-btn class="q-ml-sm" flat icon="filter_list" @click="show_filter=!show_filter"/>
          <q-btn color="primary" label="Adicionar Novo" :disabled="allParecerAutos.length === 0 ? false : true" no-caps outline rounded @click="show_dialog = true"/>
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
              <q-td key="seccao" :props="props">
            <div class="text-pre-wrap">{{ props.row.seccao }} - {{ props.row.brigada }}</div>
            <q-popup-edit v-model="props.row.seccao">
              <q-input v-model="props.row.seccao" autofocus dense></q-input>
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
              <!--router-link :to="`/parecerAuto/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              </router-link-->
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaParecerAuto(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeParecerAuto(props.row)">
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
    <create-edit-form :close="close"
                      :createParecerAuto="createParecerAuto"
                      :dataRegisto.sync="parecerAuto.dataRegisto"
                      :parecer.sync="parecerAuto.parecer"
                      :seccao.sync="parecerAuto.seccao"
                      :brigada.sync="parecerAuto.brigada"
                      :inspector.sync="inspector"
                      :inspectors.sync="allInspectors"
                      :listErrors="listErrors"
                      :tipoParecer.sync="tipoParecer"
                      :tiposParecer.sync="allTiposParecer"
                      :removeParecerAuto="removeParecerAuto"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
    </div>
</q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Inspector from 'src/store/models/inspector/inspector'
import ParecerAuto from 'src/store/models/parecerAuto/parecerAuto'
import TipoParecer from 'src/store/models/tipoParecer/tipoParecer'
import TipoParecerAuto from 'src/store/models/tipoParecerAuto/tipoParecerAuto'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'ParecerAuto',
  data() {
    return {
      listErrors: [],
      options: [],
      parecerAuto_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      parecerAuto: {
        parecer: '',
        dataRegisto: '',
        seccao: '',
        brigada: '',
        tipoParecer: {},
        inspector: {},
        auto: {}
      },
       tipoParecer: {
        designacao: ''
      },
      inspector: {
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
          label: 'Observação',
          field: row => row.parecer,
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
          label: 'Parecer',
          field: row => row.tipoParecer,
          format: val => `${val}`,
          sortable: true
        },
           {
          name: 'seccao',
          align: 'left',
          label: 'Secção-Brigada',
          field: row => row.seccao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'inspector',
          align: 'left',
          label: 'Inspector',
          field: row => row.inspector,
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
    return this.getAllParecerAuto()
  },
  props: ['autoEntrada'],
  mounted() {
    let offset = 0
    this.getAllParecerAuto(offset)
    this.getAllTipoParecer(offset)
    this.getAllInspector(offset)
  },
  components: {
    'create-edit-form': require('components/parecerAuto/createEditForm.vue').default
  },
  metaInfo: {},
  computed: {
    allInspectors() {
      return Inspector.query().all()
    },
    allTiposParecer() {
      return TipoParecerAuto.query().all()
    },
    allParecerAutos() {
      return ParecerAuto.query().with('inspector').with('tipoParecer').where('auto_id',this.autoEntrada.id).get()
    }
  },
  methods: {
    createParecerAuto() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.parecerAuto.tipoParecer = this.tipoParecer
      this.parecerAuto.tipoParecer_id = this.tipoParecer.id
      this.parecerAuto.inspector_id = this.inspector.id
      this.parecerAuto.inspector = this.inspector
      this.parecerAuto.auto_id = this.autoEntrada.id
      this.parecerAuto.auto = this.autoEntrada
      console.log(this.autoEntrada)
      if (this.editedIndex > -1) {
         ParecerAuto.api().patch("/parecerAuto/" + this.parecerAuto.id, this.parecerAuto).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.parecerAuto.parecer + ' ]'
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
       ParecerAuto.api().post("/parecerAuto/", this.parecerAuto).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.parecerAuto.parecer + ' ]'
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
    this.getAllParecerAuto(offset)
    this.getAllTipoParecer(offset)
    this.getAllInspector(offset)
      this.listErrors = {}
      this.show_dialog = false
      this.parecerAuto = {}
      this.props = this.parecerAuto
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeParecerAuto(parecerAuto) {
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
          message: 'A informação foi Removida com successo! [ ' + parecerAuto.parecer + ' ]'
        })
         ParecerAuto.api().delete("/parecerAuto/" + parecerAuto.id)
      })
    },
    editaParecerAuto(parecerAuto) {
      this.editedIndex = 0
      this.parecerAuto = Object.assign({}, parecerAuto)
      this.tipoParecer = TipoParecer.query().find(parecerAuto.tipoParecer_id)
      this.inspector = Inspector.query().find(parecerAuto.inspector.id) 
      this.show_dialog = true
    },
    getAllParecerAuto(offset) {
      if(offset >= 0){
          ParecerAuto.api().get("/parecerAuto?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllParecerAuto(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllTipoParecer(offset) {
      if(offset >= 0){
          TipoParecerAuto.api().get("/tipoParecerAuto?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTipoParecer(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    getAllInspector(offset) {
      if(offset >= 0){
          Inspector.api().get("/inspector?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllInspector(offset), 2)
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
          this.allParecerAutos.map(row =>
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
      const status = exportFile('parecerAuto_list.csv', content, 'text/csv')
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
