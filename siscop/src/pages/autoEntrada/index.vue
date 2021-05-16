<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allAutoEntradas" :filter="filter" binary-state-sort row-key="name"
             title="Auto de Entrada">

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
          <q-td key="tipoAuto" :props="props">
            <div class="text-pre-wrap">{{ props.row.tipoAuto.designacao }}</div>
            <q-popup-edit v-model="props.row.tipoAuto.designacao">
              <q-input v-model="props.row.tipoAuto.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="classeJudicial" :props="props">
            <div class="text-pre-wrap">{{ props.row.classeJudicial.designacao }}</div>
            <q-popup-edit v-model="props.row.classeJudicial.designacao">
              <q-input v-model="props.row.classeJudicial.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="crime" :props="props">
            <div class="text-pre-wrap">{{ props.row.crime.designacao }}</div>
            <q-popup-edit v-model="props.row.crime.designacao">
              <q-input v-model="props.row.crime.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="inspector" :props="props">
            <div class="text-pre-wrap">{{ props.row.inspector.numero }} - {{ props.row.inspector.nome }}
              {{ props.row.inspector.apelido }}
            </div>
            <q-popup-edit v-model="props.row.inspector">
              <q-input v-model="props.row.inspector" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/autoEntrada/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm />
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaAutoEntrada(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click="removeAutoEntrada(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <create-edit-form :anexo.sync="autoEntrada.anexo"
                      :classeJudicial.sync="classeJudicial"
                      :classeJudicials.sync="allclasseJudicials"
                      :close="close"
                      :contacto.sync="autoEntrada.contacto"
                      :createAutoEntrada="createAutoEntrada"
                      :crime.sync="crime"
                      :crimes.sync="allCrimesFromClasseJudicial"
                      :dataAbertura.sync="autoEntrada.dataAbertura"
                      :descricao.sync="autoEntrada.descricao"
                      :endereco.sync="autoEntrada.endereco"
                      :horaOcorrencia.sync="autoEntrada.horaOcorrencia"
                      :infraccao.sync="autoEntrada.infraccao"
                      :inspector.sync="inspector"
                      :inspectors.sync="allInspectors"
                      :listErrors="listErrors"
                      :modusOperandi.sync="autoEntrada.modusOperandi"
                      :numero.sync="autoEntrada.numero"
                      :removeAutoEntrada="removeAutoEntrada"
                      :responsavelLocal.sync="autoEntrada.responsavelLocal"
                      :show_dialog="show_dialog"
                      :submitting="submitting"
                      :tipoAuto.sync="tipoAuto"
                      :tipoAutos.sync="alltipoAutos"/>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Inspector from 'src/store/models/inspector/inspector'
import Crime from 'src/store/models/crime/crime'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
import TipoAuto from 'src/store/models/tipoAuto/tipoAuto'
import Orgao from 'src/store/models/orgao/orgao'
import AutoEntrada from 'src/store/models/autoEntrada/autoEntrada'
import Provincia from 'src/store/models/provincia/provincia'
import TipoOrgao from 'src/store/models/tipoOrgao/tipoOrgao'
import Pais from 'src/store/models/pais/pais'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'AutoEntrada',
  data() {
    return {
      listErrors: [],
      options: [],
      autoEntrada_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      autoEntrada: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        modusOperandi: '',
        horaOcorrencia: '',
        infraccao: '',
        endereco: '',
        responsavelLocal: '',
        contacto: '',
        anexo: [],
        tipoAuto: {},
        classeJudicial: {},
        crime: {},
        inspector: {},
        orgao: {}
      },
      tipoAuto: {
        codigo: '',
        designacao: ''
      },
      classeJudicial: {
        designacao: ''
      },
      crime: {
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
          name: 'tipoAuto',
          align: 'left',
          label: 'Tipo de Auto',
          field: row => row.tipoAuto,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'classeJudicial',
          align: 'left',
          label: 'Família Delitiva',
          field: row => row.classeJudicial,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'crime',
          align: 'left',
          label: 'Tipo Legal de Crime',
          field: row => row.crime,
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
    return this.getAllAutoEntrada()
  },
  mounted() {
    let offset = 0
    this.getAllAutoEntrada(offset)
    this.getAllTipoAuto(offset)
    this.getAllJurisdicao(offset)
    this.getAllCrime(offset)
    this.getAllInspector(offset)
    this.getAllOrgao(offset)
    this.getAllPais(offset)
    this.getAllProvincia(offset)
    this.getAllTipoOrgao(offset)
  },
  components: {
    'create-edit-form': require('components/autoEntrada/createEditForm.vue').default
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
    allInspectors() {
      return Inspector.query().all()
    },
    allCrimes() {
      return Crime.query().all()
    },
    allCrimesFromClasseJudicial() {
      return Crime.query().where('classeJudicial_id', this.classeJudicial.id).get()
    },
    allclasseJudicials() {
      return ClasseJudicial.query().all()
    },
    alltipoAutos() {
      return TipoAuto.query().all()
    },
    allOrgaos() {
      return Orgao.query().all()
    },
    allAutoEntradas() {
      return AutoEntrada.query().with('orgao').with('inspector').with('crime').with('classeJudicial').with('tipoAuto').get()
    }
  },
  methods: {
    createAutoEntrada() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)

      this.autoEntrada.tipoAuto = this.tipoAuto
      this.autoEntrada.classeJudicial = this.classeJudicial
      this.crime.classeJudicial = this.classeJudicial
      this.autoEntrada.crime = this.crime
      this.autoEntrada.inspector = this.inspector
      this.autoEntrada.tipoAuto_id = this.tipoAuto.id
      this.autoEntrada.classeJudicial_id = this.classeJudicial.id
      this.autoEntrada.crime_id = this.crime.id
      this.autoEntrada.inspector_id = this.inspector.id
      this.autoEntrada.orgao_id = Orgao.query().first().id
      this.autoEntrada.orgao = Orgao.query().with('provincia.pais').with('tipoOrgao').first()

      if (this.editedIndex > -1) {
        AutoEntrada.api().patch("/autoEntrada/" + this.autoEntrada.id,  this.autoEntrada, config).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.autoEntrada.numero + ' ]'
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
        AutoEntrada.api().post("/autoEntrada/",  this.autoEntrada).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.autoEntrada.numero + ' ]'
          })
          this.close()
          this.$router.push({path: '/autoEntrada/' + resp.response.data.id })
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
    this.getAllAutoEntrada(offset)
    this.getAllTipoAuto(offset)
    this.getAllJurisdicao(offset)
    this.getAllCrime(offset)
    this.getAllInspector(offset)
    this.getAllOrgao(offset)
    this.getAllPais(offset)
    this.getAllProvincia(offset)
    this.getAllTipoOrgao(offset)
    this.listErrors = {}
    this.show_dialog = false
    this.autoEntrada = {}
    this.props = this.autoEntrada
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeAutoEntrada(autoEntrada) {
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
          message: 'A informação foi Removida com successo! [ ' + autoEntrada.numero + ' ]'
        })
        AutoEntrada.api().delete("/autoEntrada/" + this.autoEntrada.id)
      })
    },
    editaAutoEntrada(autoEntrada) {
      this.editedIndex = 0
      this.autoEntrada = Object.assign({}, autoEntrada)
      this.tipoAuto = TipoAuto.query().find(autoEntrada.tipoAuto_id)
      this.classeJudicial = ClasseJudicial.query().find(autoEntrada.classeJudicial_id)
      this.crime = Crime.query().find(autoEntrada.crime_id)
      this.inspector = Inspector.query().find(autoEntrada.inspector_id)
      this.orgao = Orgao.query().first()
      this.show_dialog = true
    },
    getAllAutoEntrada(offset) {
       if(offset >= 0){
          AutoEntrada.api().get("/autoEntrada?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllAutoEntrada(offset), 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllTipoAuto(offset) {
       if(offset >= 0){
          TipoAuto.api().get("/tipoAuto?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTipoAuto(offset), 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllJurisdicao(offset) {
       if(offset >= 0){
          ClasseJudicial.api().get("/classeJudicial?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllJurisdicao(offset), 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllCrime(offset) {
       if(offset >= 0){
          Crime.api().get("/crime?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllCrime(offset), 2)

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
    getAllOrgao(offset) {
       if(offset >= 0){
          Orgao.api().get("/orgao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllOrgao(offset), 2)

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
          if(resp.response.data.length > 0) 
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
          this.$store.state.autoEntrada.autoEntradas.map(row =>
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
      const status = exportFile('autoEntrada_list.csv', content, 'text/csv')
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
