<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allMeioUtilizadosFromPecaProcesso" :filter="filter" binary-state-sort
             row-key="name" title="Meio Empregue">

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
          <q-td key="matricula" :props="props">
            {{ props.row.matricula }}
            <q-popup-edit v-model="props.row.matricula">
              <q-input v-model="props.row.matricula" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="cor" :props="props">
            {{ props.row.cor }}
            <q-popup-edit v-model="props.row.cor">
              <q-input v-model="props.row.cor" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="calibre" :props="props">
            {{ props.row.calibre }}
            <q-popup-edit v-model="props.row.calibre">
              <q-input v-model="props.row.calibre" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="designacao" :props="props">
            {{ props.row.designacao }}
            <q-popup-edit v-model="props.row.designacao">
              <q-input v-model="props.row.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoMeio" :props="props">
            {{ props.row.tipoMeio.designacao }}
            <q-popup-edit v-model="props.row.tipoMeio.designacao">
              <q-input v-model="props.row.tipoMeio.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="marca" :props="props">
            {{ props.row.marca.designacao }}
            <q-popup-edit v-model="props.row.marca.designacao">
              <q-input v-model="props.row.marca.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="modelo" :props="props">
            {{ props.row.modelo.designacao }}
            <q-popup-edit v-model="props.row.modelo.designacao">
              <q-input v-model="props.row.modelo.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaMeioUtilizado(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click="removeMeioUtilizado(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar Meio Empregue!</div>
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
            <q-form class="q-gutter-md" @submit.prevent="createMeioUtilizado">
              <create-edit-form :calibre.sync="meioUtilizado.calibre"
                                :cor.sync="meioUtilizado.cor"
                                :chassi.sync="meioUtilizado.chassi"
                                :numeroMotor.sync="meioUtilizado.numeroMotor"
                                :designacao.sync="meioUtilizado.designacao"
                                :marca.sync="marca"
                                :marcaList.sync="allMarcaFromTipoMeio"
                                :matricula.sync="meioUtilizado.matricula"
                                :modelo.sync="modelo"
                                :modeloList.sync="allModeloFromMarca"
                                :tipoMeio.sync="tipoMeio"
                                :tipoMeioList.sync="allTipoMeio"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createMeioUtilizado"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import TipoMeio from 'src/store/models/tipoMeio/tipoMeio'
import Marca from 'src/store/models/marca/marca'
import Modelo from 'src/store/models/modelo/modelo'
import MeiosUtilizado from 'src/store/models/meiosUtilizado/meiosUtilizado'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'MeioUtilizado',
  data() {
    return {
      listErrors: [],
      meioUtilizado_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      meioUtilizado: {
        matricula: '',
        cor: '',
        calibre: '',
        designacao: '',
        chassi: '',
        numeroMotor: '',
        tipoMeio: {},
        marca: {},
        modelo: {},
        pecaProcesso: {}
      },
      tipoMeio: {
        designacao: ''
      },
      marca: {
        designacao: ''
      },
      modelo: {
        designacao: ''
      },
      columns: [
        {
          name: 'matricula',
          required: true,
          label: 'Matrícula',
          align: 'left',
          field: row => row.matricula,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'cor', align: 'left', label: 'Cor', field: row => row.cor, format: val => `${val}`, sortable: true},
        {
          name: 'calibre',
          align: 'left',
          label: 'Calibre',
          field: row => row.calibre,
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
          name: 'tipoMeio',
          align: 'left',
          label: 'Tipo de Meio Utilizado',
          field: row => row.tipoMeio,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'marca',
          align: 'left',
          label: 'Marca',
          field: row => row.marca,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'modelo',
          align: 'left',
          label: 'Modelo',
          field: row => row.modelo,
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
    return this.getAllMeioUtilizado()
  },
  props: [
    'pecaProcesso'
  ],
  mounted() {
    this.getAllMeioUtilizado()
    this.getAllTipoMeio()
    this.getAllMarca()
    this.getAllModelo()
  },
  components: {
    'create-edit-form': require('components/meiosUtilizado/createEditForm.vue').default
  },
    created() {
    },
  metaInfo: {},
  computed: {
    allMeioUtilizadosFromPecaProcesso() {
      return MeiosUtilizado.query().with('tipoMeio').with('marca').with('modelo').all()
    },
    allTipoMeio() {
      return TipoMeio.query().all()
    },
    allMarca() {
      return Marca.query().with('tipoMeio').all()
    },
    allModelo() {
      return Modelo.query().with('marca').all()
    },
    allMarcaFromTipoMeio() {
      return Marca.query().with('tipoMeio').where('tipoMeio_id', this.tipoMeio.id).get()
    },
    allModeloFromMarca() {
      return Modelo.query().with('marca').where('marca_id', this.marca.id).get()
    }
  },
  methods: {
    createMeioUtilizado() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.meioUtilizado.tipoMeio = this.tipoMeio
      this.marca.tipoMeio = this.tipoMeio
      this.modelo.marca = this.marca
      this.meioUtilizado.marca_id = this.marca.id
      this.meioUtilizado.modelo_id = this.modelo.id
      this.meioUtilizado.pecaProcesso_id = this.pecaProcesso.id
      this.meioUtilizado.marca = this.marca
      this.meioUtilizado.modelo = this.modelo
      this.meioUtilizado.pecaProcesso = this.pecaProcesso
    
      if (this.editedIndex > -1) {
         MeiosUtilizado.api().patch("/meioUtilizado/" + this.meioUtilizado.id, this.meioUtilizado).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.meioUtilizado.nome + ' ]'
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
        MeiosUtilizado.api().post("/meioUtilizado/", this.meioUtilizado).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.meioUtilizado.nome + ' ]'
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
      this.getAllMeioUtilizado()
    this.getAllTipoMeio()
    this.getAllMarca()
    this.getAllModelo()
      this.show_dialog = false
      this.meioUtilizado = {}
      this.props = this.meioUtilizado
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeMeioUtilizado(meioUtilizado) {
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
          message: 'A informação foi Removida com successo! [ ' + meioUtilizado.nome + ' ]'
        })
         MeiosUtilizado.api().delete("/meioUtilizado/" + this.meioUtilizado.id)
      })
    },
    editaMeioUtilizado(meioUtilizado) {
      this.editedIndex = 0
      this.meioUtilizado = Object.assign({}, meioUtilizado)
      this.tipoMeio = TipoMeio.query().find(meioUtilizado.tipoMeio_id)
      this.marca = Marca.query().find(meioUtilizado.marca_id)
      this.modelo = Modelo.query().find(meioUtilizado.modelo_id)
      this.show_dialog = true
    },
    getAllMeioUtilizado() {
      MeiosUtilizado.api().get('/meioUtilizado?offset=0&max=1000000')
    },
    getAllTipoMeio() {
      TipoMeio.api().get('/tipoMeio?offset=0&max=1000000')
    },
    getAllMarca() {
      Marca.api().get('/marca?offset=0&max=1000000')
    },
    getAllModelo() {
      Modelo.api().get('/modelo?offset=0&max=1000000')
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.meioUtilizado.meioUtilizados.map(row =>
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
      const status = exportFile('meioUtilizado_list.csv', content, 'text/csv')
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
