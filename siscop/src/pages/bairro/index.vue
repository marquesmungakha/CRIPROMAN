<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allBairros" :filter="filter" binary-state-sort row-key="name" title="Bairro">

      <template v-slot:top-right>
        <q-input v-if="show_filter" v-model="filter" borderless debounce="300" dense filled placeholder="Pesquisa">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>

        <div class="q-pa-md q-gutter-sm">
          <q-btn class="q-ml-sm" flat icon="filter_list" @click="show_filter=!show_filter"/>
          <q-btn color="primary" label="Adicionar Novo" no-caps outline rounded @click="show_dialog = true"/>
          <q-btn color="primary" icon-right="archive" label="Imprimir em Excel" no-caps rounded @click="exportTable"/>
        </div>
      </template>
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td key="designacao" :props="props">
            {{ props.row.designacao }}
            <q-popup-edit v-model="props.row.designacao" title="Update designacao">
              <q-input v-model="props.row.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            <div class="text-pre-wrap">{{ props.row.distrito.provincia.designacao }}</div>
            <q-popup-edit v-model="props.row.distrito.provincia.designacao">
              <q-input v-model="props.row.distrito.provincia.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="distrito" :props="props">
            <div class="text-pre-wrap">{{ props.row.distrito.designacao }}</div>
            <q-popup-edit v-model="props.row.distrito.designacao">
              <q-input v-model="props.row.distrito.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="postoAdministrativo" :props="props">
            <div class="text-pre-wrap">{{ props.row.postoAdministrativo.designacao }}</div>
            <q-popup-edit v-model="props.row.postoAdministrativo.designacao">
              <q-input v-model="props.row.postoAdministrativo.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="localidade" :props="props">
            <div class="text-pre-wrap">{{ props.row.localidade.designacao }}</div>
            <q-popup-edit v-model="props.row.localidade.designacao">
              <q-input v-model="props.row.localidade.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/bairro/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm />
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaBairro(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeBairro(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :close="close"
                      :createBairro="createBairro"
                      :designacao.sync="bairro.designacao"
                      :distrito.sync="distrito"
                      :distritos.sync="allDistritosFromProvincia"
                      :listErrors="listErrors"
                      :localidade.sync="localidade"
                      :localidades="allLocalidadeFromDistritoOrPostoAdministrativo"
                      :postoAdministrativo.sync="postoAdministrativo"
                      :postoAdministrativos.sync="allPostoAdministrativosFromDistrito"
                      :provincia.sync="provincia"
                      :provincias.sync="allProvincias"
                      :removeBairro="removeBairro"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'
import Pais from 'src/store/models/pais/pais'
import PostoAdministrativo from 'src/store/models/postoAdministrativo/postoAdministrativo'
import Localidade from 'src/store/models/localidade/localidade'
import Bairro from 'src/store/models/bairro/bairro'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Bairro',
  data() {
    return {
      listErrors: [],
      options: [],
      bairro_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      bairro: {
        designacao: '',
        distrito: {},
        postoAdministrativo: {},
        localidade: {}
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      postoAdministrativo: {
        codigo: '',
        designacao: ''
      },
      localidade: {
        codigo: '',
        designacao: ''
      },
      columns: [
        {
          name: 'designacao',
          align: 'left',
          label: 'Designação',
          field: row => row.designacao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'provincia',
          align: 'left',
          label: 'Província',
          field: row => row.provincia,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'distrito',
          align: 'left',
          label: 'Distrito',
          field: row => row.distrito.id,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'postoAdministrativo',
          align: 'left',
          label: 'Posto Administrativo',
          field: row => row.postoAdministrativo.id,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'localidade',
          align: 'left',
          label: 'Localidade',
          field: row => row.localidade.id,
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
    return this.getAllBairro()
  },
  mounted() {
    this.getAllPais()
    this.getAllPostoAdministrativo()
    this.getAllProvincia()
    this.getAllDistrito()
    this.getAllLocalidade()
    this.getAllBairro()
  },
  components: {
    'create-edit-form': require('components/bairro/createEditForm.vue').default
  },
  created() {
    this.$q.loading.show({
      message: "Carregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })

    setTimeout(() => {
      this.$q.loading.hide()
    }, 600)

  },
  metaInfo: {},
  computed: {
    allProvincias() {
      return Provincia.query().with('pais').all()
    },
    allDistritosFromProvincia() {
      return Distrito.query().with('provincia').where('provincia_id', this.provincia.id).get()
    },
    allDistritos() {
      return Distrito.query().with('provincia').all()
    },
    allPostoAdministrativos() {
      return PostoAdministrativo.query().with('distrito.provincia').all()
    },
    allPostoAdministrativosFromDistrito() {
      return PostoAdministrativo.query().with('distrito').where('distrito_id', this.distrito.id).get()
    },
    allLocalidades() {
      return Localidade.query().with('distrito.provincia').with('postoAdministrativo').all()
    },
    allLocalidadeFromDistritoOrPostoAdministrativo() {
      if (this.postoAdministrativo != null) {
        return Localidade.query().with('postoAdministrativo').where('postoAdministrativo_id', this.postoAdministrativo.id).get()
      } else {
        return Localidade.query().with('distrito').where('distrito_id', this.distrito.id).get()
      }
    },
    allBairros() {
      return Bairro.query().with('distrito.provincia').with('postoAdministrativo').with('localidade').all()
    }
  },
  methods: {
    createBairro() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.bairro.distrito = this.distrito
      this.bairro.postoAdministrativo = this.postoAdministrativo
      this.bairro.localidade = this.localidade
      this.bairro.distrito_id = this.distrito.id
      this.bairro.postoAdministrativo_id = this.postoAdministrativo.id
      this.bairro.localidade_id = this.localidade.id
      if (this.editedIndex > -1) {
        Bairro.api().patch("/bairro/" + this.bairro.id, this.bairro).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.bairro.designacao + ' ]'
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
        Bairro.api().post("/bairro/", this.bairro).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi inserida com successo! [ ' + this.bairro.designacao + ' ]'
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
      this.getAllBairro()
      this.getAllLocalidade()
      this.getAllPostoAdministrativo()
      this.getAllPais()
      this.getAllProvincia()
      this.getAllDistrito()
      this.show_dialog = false
      this.listErrors = {}
      this.bairro = {}
      this.localidade = {}
      this.distrito = {}
      this.provincia = {}
      this.postoAdministrativo = {}
      this.props = this.bairro
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeBairro(bairro) {
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
          message: 'A informação foi Removida com successo! [ ' + bairro.designacao + ' ]'
        })
        Bairro.api().delete("/bairro/" + bairro.id)
      })
    },
    editaBairro(bairro) {
      this.editedIndex = 0
      this.bairro = Object.assign({}, bairro)
      this.localidade = Localidade.query().find(bairro.localidade_id)
      this.postoAdministrativo = PostoAdministrativo.query().find(bairro.postoAdministrativo_id)
      this.distrito = Distrito.query().find(bairro.distrito_id)
      this.provincia = Provincia.query().find(this.distrito.provincia_id)
      this.show_dialog = true
    },
    getAllBairro() {
      Bairro.api().get('/bairro?offset=0&max=1000000')
    },
    getAllLocalidade() {
      Localidade.api().get('/localidade?offset=0&max=1000000')
    },
    getAllPais() {
      Pais.api().get('/pais?offset=0&max=1000000')
    },
    getAllProvincia() {
      return Provincia.api().get('/provincia?offset=0&max=1000000', {
        persistOptions: {
          insert: ['pais']
        }
      })
    },
    getAllDistrito() {
      Distrito.api().get('/distrito?offset=0&max=1000000')
    },
    getAllPostoAdministrativo() {
      PostoAdministrativo.api().get('/postoAdministrativo?offset=0&max=1000000')
    },
    abortFilterFn() {
      // console.log('delayed filter aborted')
    },
    setModel(val) {
      this.bairro.distrito = val
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.bairro.bairros.map(row =>
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
      const status = exportFile('bairro_list.csv', content, 'text/csv')
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
