<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Meio Utilizado" :data="allMeioUtilizadosFromPecaProcesso" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="matricula" :props="props">
              {{ props.row.matricula }}
              <q-popup-edit v-model="props.row.matricula">
                <q-input v-model="props.row.matricula" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="cor" :props="props">
              {{ props.row.cor }}
              <q-popup-edit v-model="props.row.cor">
                <q-input v-model="props.row.cor" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="calibre" :props="props">
              {{ props.row.calibre }}
              <q-popup-edit v-model="props.row.calibre">
                <q-input v-model="props.row.calibre" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="designacao" :props="props">
              {{ props.row.designacao }}
              <q-popup-edit v-model="props.row.designacao">
                <q-input v-model="props.row.designacao" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="tipoMeio" :props="props">
              {{ getTipoMeio (props.row.tipoMeio.id).designacao }}
              <q-popup-edit v-model="props.row.tipoMeio">
                <q-input v-model="props.row.tipoMeio" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="marca" :props="props">
              {{ getMarca (props.row.marca.id).designacao }}
              <q-popup-edit v-model="props.row.marca">
                <q-input v-model="props.row.marca" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="modelo" :props="props">
              {{ getModelo(props.row.modelo.id).designacao  }}
              <q-popup-edit v-model="props.row.modelo">
                <q-input v-model="props.row.modelo" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <q-btn round glossy icon="edit" color="blue" @click="editaMeioUtilizado(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeMeioUtilizado(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
   <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
         <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  MeioUtilizado!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
            <q-separator />
            <q-card-section style="max-height: 70vh" class="scroll">
                    <q-form @submit.prevent="createMeioUtilizado" class="q-gutter-md">
                        <create-edit-form :matricula.sync="meioUtilizado.matricula"
                                          :cor.sync="meioUtilizado.cor"
                                          :calibre.sync="meioUtilizado.calibre"
                                          :designacao.sync="meioUtilizado.designacao"
                                          :tipoMeio.sync="tipoMeio"
                                          :marca.sync="marca"
                                          :modelo.sync="modelo"
                                          :modeloList.sync="allModeloFromMarca"
                                          :marcaList.sync="allMarcaFromTipoMeio"
                                          :tipoMeioList.sync="allTipoMeio"/>
                      </q-form>
            </q-card-section>
            <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createMeioUtilizado" color="teal" label="Gravar" />
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
  name: 'MeioUtilizado',
  data () {
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
        { name: 'matricula', required: true, label: 'Matrícula', align: 'left', field: row => row.matricula, format: val => `${val}`, sortable: true },
        { name: 'cor', align: 'left', label: 'Cor', field: row => row.cor, format: val => `${val}`, sortable: true },
        { name: 'calibre', align: 'left', label: 'Calibre', field: row => row.calibre, format: val => `${val}`, sortable: true },
        { name: 'designacao', align: 'left', label: 'Designação', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'tipoMeio', align: 'left', label: 'Tipo de Meio Utilizado', field: row => row.tipoMeio, format: val => `${val}`, sortable: true },
        { name: 'marca', align: 'left', label: 'Marca', field: row => row.marca, format: val => `${val}`, sortable: true },
        { name: 'modelo', align: 'left', label: 'Modelo', field: row => row.modelo, format: val => `${val}`, sortable: true },
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
    return store.dispatch('meioUtilizado/getAllMeioUtilizado')
  },
  props: [
    'autoEntrada'
  ],
  mounted () {
    this.$store.dispatch('meioUtilizado/getAllMeioUtilizado')
    this.$store.dispatch('tipoMeio/getAllTipoMeio')
    this.$store.dispatch('marca/getAllMarca')
    this.$store.dispatch('modelo/getAllModelo')
  },
  components: {
    'create-edit-form': require('components/meiosUtilizado/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allMeioUtilizados () {
      return this.$store.getters['meioUtilizado/allMeioUtilizado'].filter(meioUtilizado => meioUtilizado.pecaProcesso != null)
    },
    allMeioUtilizadosFromPecaProcesso () {
      return this.allMeioUtilizados.filter(meioUtilizado => meioUtilizado.pecaProcesso.id === this.autoEntrada.id)
    },
    allTipoMeio () {
      return this.$store.getters['tipoMeio/allTipoMeio']
    },
    allMarca () {
      return this.$store.getters['marca/allMarca']
    },
    allModelo () {
      return this.$store.getters['modelo/allModelo']
    },
    allMarcaFromTipoMeio () {
      return this.allMarca.filter(marca => marca.tipoMeio.id === this.tipoMeio.id)
    },
    allModeloFromMarca () {
      return this.allModelo.filter(modelo => modelo.marca.id === this.marca.id)
    }
  },
  methods: {
    ...mapActions('meioUtilizado', ['getAllMeioUtilizado', 'addNewMeioUtilizado', 'updateMeioUtilizado', 'deleteMeioUtilizado']),
    createMeioUtilizado () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.meioUtilizado.tipoMeio = this.tipoMeio
      this.meioUtilizado.marca = this.marca
      this.meioUtilizado.modelo = this.modelo
      this.meioUtilizado.pecaProcesso = this.autoEntrada
      console.log('MeioUtilizado' + this.meioUtilizado[0])
      if (this.editedIndex > -1) {
        this.updateMeioUtilizado(this.meioUtilizado).then(resp => {
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
        this.addNewMeioUtilizado(this.meioUtilizado).then(resp => {
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
    close () {
      this.$store.dispatch('meioUtilizado/getAllMeioUtilizado')
      this.$store.dispatch('tipoMeio/getAllTipoMeio')
      this.$store.dispatch('marca/getAllMarca')
      this.$store.dispatch('modelo/getAllModelo')
      this.show_dialog = false
      this.meioUtilizado = {}
      this.props = this.meioUtilizado
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeMeioUtilizado (meioUtilizado) {
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
        this.deleteMeioUtilizado(meioUtilizado)
      })
    },
    editaMeioUtilizado (meioUtilizado) {
      this.editedIndex = this.allMeioUtilizados.indexOf(meioUtilizado)
      this.meioUtilizado = Object.assign({}, meioUtilizado)
      this.meioUtilizado.tipoMeio = this.tipoMeio
      this.meioUtilizado.marca = this.marca
      this.meioUtilizado.modelo = this.modelo
      this.show_dialog = true
    },
    getTipoMeio (id) {
      const localTipoMeio = this.allTipoMeio.filter(tipoMeio => tipoMeio.id === id)
      if (localTipoMeio.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localTipoMeio[0] }
    },
    getMarca (id) {
      const localMarca = this.allMarca.filter(marca => marca.id === id)
      if (localMarca.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localMarca[0] }
    },
    getModelo (id) {
      const localModelo = this.allModelo.filter(modelo => modelo.id === id)
      if (localModelo.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localModelo[0] }
    },
    exportTable () {
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
