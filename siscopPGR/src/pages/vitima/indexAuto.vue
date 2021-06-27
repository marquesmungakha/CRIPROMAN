<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allVitimasFromAutoEntrada" :filter="filter" binary-state-sort
             row-key="name" title="Vitima">

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
          <q-td key="nome" :props="props">
            {{ props.row.vitima.nome}}
            <q-popup-edit v-model="props.row.vitima.nome">
              <q-input v-model="props.row.vitima.nome" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="apelido" :props="props">
            {{ props.row.vitima.apelido }}
            <q-popup-edit v-model="props.row.vitima.apelido">
              <q-input v-model="props.row.vitima.apelido" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="sexo" :props="props">
            {{ props.row.vitima.sexo }}
            <q-popup-edit v-model="props.row.vitima.sexo">
              <q-input v-model="props.row.vitima.sexo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataNascimento" :props="props">
            {{ props.row.vitima.dataNascimento }}
            <q-popup-edit v-model="props.row.vitima.dataNascimento">
              <q-input v-model="props.row.vitima.dataNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nacionalidade" :props="props">
            {{ props.row.vitima.nacionalidade.nacionalidade }}
            <q-popup-edit v-model="props.row.vitima.nacionalidade.nacionalidade">
              <q-input v-model="props.row.vitima.nacionalidade.nacionalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            {{ props.row.vitima.provincia.designacao }}
            <q-popup-edit v-model="props.row.vitima.provincia.designacao">
              <q-input v-model="props.row.vitima.provincia.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="estadoCivil" :props="props">
            {{ props.row.vitima.estadoCivil }}
            <q-popup-edit v-model="props.row.vitima.estadoCivil">
              <q-input v-model="props.row.vitima.estadoCivil" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoDocumento" :props="props">
            {{ props.row.vitima.tipoDocumento.designacao }}
            <q-popup-edit v-model="props.row.vitima.tipoDocumento.designacao">
              <q-input v-model="props.row.vitima.tipoDocumento.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="numDocumentoIndentificacao" :props="props">
            {{ props.row.vitima.numDocumentoIndentificacao }}
            <q-popup-edit v-model="props.row.vitima.numDocumentoIndentificacao">
              <q-input v-model="props.row.vitima.numDocumentoIndentificacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm @click="mostraVitima(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaVitima(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeVitima(props.row)">
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
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar/Actualizar Vitima!</div>
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
          <q-card-section class="scroll" style="max-height: 80vh">
            <q-form class="q-gutter-md" @submit.prevent="createVitima">
             <div class="q-pa-md">
                <q-stepper
                  v-model="step"
                  ref="stepper"
                  color="primary"
                  header-class="text-bold"
                  animated >
                    <q-step
                      :name="1"
                      title="Verificar Vitima Existente"
                      icon="settings"
                      :done="step > 1" >
                      <search-individuo :apelido.sync="vitima.apelido"
                            :nome.sync="vitima.nome"
                            :numDocumentoIndentificacao.sync="vitima.numDocumentoIndentificacao"
                            :sexo.sync="vitima.sexo"
                            :tipoDocumento.sync="tipoDocumento"
                            :tipoDocumentos.sync="allTipoDocumentos"
                            :findIndividuo.sync="findIndividuo"/>
                    </q-step>

                    <q-step
                      :name="2"
                      title="Criar/Actualizar Dados"
                      icon="create_new_folder"
                      :done="step > 2" >
              <individuo :apelido.sync="vitima.apelido"
                         :dataNascimento.sync="vitima.dataNascimento"
                         :documentoValidade.sync="vitima.documentoValidade"
                         :estadoCivil.sync="vitima.estadoCivil"
                         :fotografia.sync="vitima.fotografia"
                         :idade.sync="vitima.idade"
                         :localNascimento.sync="vitima.localNascimento"
                         :morada.sync="vitima.morada"
                         :naturalidade.sync="vitima.naturalidade"
                         :nome.sync="vitima.nome"
                         :numDocumentoIndentificacao.sync="vitima.numDocumentoIndentificacao"
                         :pais.sync="pais"
                         :paises.sync="allPaises"
                         :provincia.sync="provincia"
                         :provincias.sync="allProvinciaFromPais"
                         :sexo.sync="vitima.sexo"
                         :telemovel1.sync="vitima.telemovel1"
                         :telemovel2.sync="vitima.telemovel2"
                         :tipoDocumento.sync="tipoDocumento"
                         :tipoDocumentos.sync="allTipoDocumentos"
                         :onFileChange.sync="onFileChange"
                         :image.sync="image"/>
                          </q-step>

                    <q-step
                      :name="3"
                      title="Dados Adicionais"
                      icon="assignment">
                      <create-edit-form :declaracao.sync="autoEntradaVitima.declaracao"/>
                    </q-step>

                  <template v-slot:navigation>
                    <q-stepper-navigation>
                      <q-btn @click="$refs.stepper.next()" color="primary" :label="step === 3 ? 'Terminou' : 'Próximo'" :disable="step === 3 ? true : false"/>
                      <q-btn v-if="step > 1" flat color="primary" @click="$refs.stepper.previous()" label="Voltar" class="q-ml-sm" />
                    </q-stepper-navigation>
                  </template>
                </q-stepper>
              </div>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createVitima" :disable="step === 3 ? false : true"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
     <details-vitima :vitima.sync="vitima" 
                       :image.sync="image" 
                       :autoEntradaVitima.sync="autoEntradaVitima" 
                       :tipoDocumento.sync="tipoDocumento"
                       :pais.sync="pais"
                       :vitima_details_dialog.sync="vitima_details_dialog"
                       :close.sync="close"/>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Vitima from 'src/store/models/vitima/vitima'
import Pais from 'src/store/models/pais/pais'
import TipoDocumentoIdentificacao from 'src/store/models/tipoDocumentoIdentificacao/tipoDocumentoIdentificacao'
import Provincia from 'src/store/models/provincia/provincia'
import AutoEntradaVitima from 'src/store/models/autoEntrada/autoEntradaVitima'
import AutoEntrada from 'src/store/models/autoEntrada/autoEntrada'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Vitima',
  data() {
    return {
       step: 1,
      offset:0,
      listErrors: [],
      vitima_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      image: '',
      show_dialog: false,
      show_filter: false,
      vitima: {
        nome: '',
        apelido: '',
        sexo: '',
        dataNascimento: '',
        naturalidade: '',
        nacionalidade: '',
        localNascimento: '',
        telemovel1: '',
        telemovel2: '',
        estadoCivil: '',
        morada: '',
        numDocumentoIndentificacao: '',
        tipoDocumento: {},
        documentoValidade: '',
        pais: {},
        provincia: {},
        autoEntrada: {}
      },
      pais: {
        codigo: '',
        designacao: '',
        nacionalidade: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      tipoDocumento: {
        codigo: '',
        designacao: ''
      },
      autoEntradaVitima: {
        declaracao: '',
        vitima: {},
        autoEntrada: {}
      },
      columns: [
        {
          name: 'nome',
          required: true,
          label: 'Nome',
          align: 'left',
          field: row => row.nome,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'apelido',
          align: 'left',
          label: 'Apelido',
          field: row => row.apelido,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'sexo', align: 'left', label: 'Gênero', field: row => row.sexo, format: val => `${val}`, sortable: true},
        {
          name: 'dataNascimento',
          align: 'left',
          label: 'Data de Nascimento',
          field: row => row.dataNascimento,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'nacionalidade',
          align: 'left',
          label: 'Nacionalidade',
          field: row => row.nacionalidade,
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
          name: 'estadoCivil',
          align: 'left',
          label: 'Estado Civil',
          field: row => row.estadoCivil,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'tipoDocumento',
          align: 'left',
          label: 'Documento de Identificação',
          field: row => row.tipoDocumento,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'numDocumentoIndentificacao',
          align: 'left',
          label: 'Número do Documento',
          field: row => row.numDocumentoIndentificacao,
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
    return this.getAllVitima()
  },
  props: [
    'autoEntrada'
  ],
  mounted() {
    let offset = 0
    this.getAllVitima(offset)
    this.getAllVitimaProcesso(offset)
    this.getAllProvincia(offset)
    this.getAllPais(offset)
    this.getAllTipoDocumentoIdentificacao(offset)
  },
  components: {
    'create-edit-form': require('components/vitima/createEditForm.vue').default,
    individuo: require('components/individuo/createEditForm.vue').default,
    'search-individuo': require('components/individuo/searchForm.vue').default,
    'details-vitima': require('components/vitima/detailsForm.vue').default
  },
  created() {
  },
  metaInfo: {},
  computed: {
    allVitimasFromAutoEntrada() {
      return AutoEntrada.query().with('vitimas')
                                 .with('vitimas.vitima')
                                 .with('vitimas.vitima.nacionalidade')
                                 .with('vitimas.vitima.provincia')
                                 .with('vitimas.vitima.tipoDocumento')
                                 .with('vitimas.vitima.profissao')
                                 .find(this.autoEntrada.id).vitimas
    },
    allPaises() {
      return Pais.query().all()
    },
    allTipoDocumentos() {
      return TipoDocumentoIdentificacao.query().all()
    },
    allProvincias() {
      return Provincia.query().all()
    },
    allProvinciaFromPais() {
      return Provincia.query().where('pais_id',this.pais.id).get()
    }
  },
  methods: {
     findIndividuo() {
      let results = undefined
      if (this.vitima.nome === undefined || this.vitima.apelido === undefined || 
          this.vitima.sexo === undefined || this.vitima.numDocumentoIndentificacao === undefined ||
          this.vitima.nome === "" || this.vitima.apelido === "" || 
          this.vitima.sexo === "" || this.vitima.numDocumentoIndentificacao === ""  
          ) {
          this.$q.notify({
          color: 'negative',
          classes: 'glossy',
          message: 'Todos os campos marcados com (*) são obrigatórios!'
        })
      }else{

          Vitima.api().get("/vitima?offset="+this.offset+"&max=100").then(resp => {
          console.log(resp)
          this.offset = this.offset + 100
          if(resp.response.data.length > 0){
                results = Vitima.query().where((vitima) => {
                return vitima.nome === this.vitima.nome && 
                       vitima.apelido === this.vitima.apelido && 
                       vitima.sexo === this.vitima.sexo &&
                       vitima.numDocumentoIndentificacao === this.vitima.numDocumentoIndentificacao 
                       }).first()
              if(results === undefined  || results === null){
                    setTimeout(this.findIndividuo, 2)
              }else{
                this.vitima = results
                this.pais = Pais.query().find(this.vitima.nacionalidade_id)
                this.provincia = Provincia.query().find(this.vitima.provincia_id)
                this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.vitima.tipoDocumento_id)
                this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.vitima.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
                this.$q.notify({
                    type: 'positive',
                    color: 'green-4',
                    textColor: 'white',
                    icon: 'cloud_done',
                    timeout: 2000,
                    position: 'bottom',
                    classes: 'glossy',
                    progress: true,
                    message: 'Vitima encontrado com successo!! [' + this.vitima.nome + ' ' + this.vitima.apelido +' ]'
                  })
                this.$refs.stepper.next()
              }
          }else{
            this.offset = 0
              this.$q.notify({
                    type: 'negative',
                    color: 'negative',
                    textColor: 'white',
                    icon: 'cloud_done',
                    timeout: 2000,
                    position: 'bottom',
                    classes: 'glossy',
                    progress: true,
                    message: 'Nenhum Vitima foi encontrado !!'
                  })
          } 
              
          }).catch(error => {
          console.log('Erro no code ' + error)
        })

      }
    },
    createVitima() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.provincia.pais = this.pais
      this.vitima.nacionalidade_id = this.pais.id
      this.vitima.provincia_id = this.provincia.id
      this.vitima.tipoDocumento_id = this.tipoDocumento.id
      this.vitima.autoEntrada_id = this.autoEntrada.id
      this.vitima.nacionalidade = this.pais
      this.vitima.provincia = this.provincia
      this.vitima.tipoDocumento = this.tipoDocumento
      this.vitima.autoEntrada = this.autoEntrada

      this.autoEntradaVitima.vitima =  this.vitima
      this.autoEntradaVitima.autoEntrada = this.autoEntrada
     
      if (this.editedIndex > -1) {
         AutoEntradaVitima.api().patch("/autoEntradaVitima/" + this.autoEntradaVitima.id, this.autoEntradaVitima).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.autoEntradaVitima.vitima.nome + ' ]'
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
        AutoEntradaVitima.api().post("/autoEntradaVitima/", this.autoEntradaVitima).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.autoEntradaVitima.vitima.nome + ' ]'
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
      this.getAllVitima(offset)
      this.getAllVitimaProcesso(offset)
      this.getAllProvincia(offset)
      this.getAllPais(offset)
      this.getAllTipoDocumentoIdentificacao(offset)
      this.vitima_details_dialog = false
      this.step = 1
      this.offset = 0
      this.listErrors = {}
      this.show_dialog = false
      this.vitima = {}
      this.props = this.vitima
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeVitima(vitima) {
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
          message: 'A informação foi Removida com successo! [ ' + vitima.nome + ' ]'
        })
         AutoEntradaVitima.api().delete("/autoEntradaVitima/" + this.autoEntradaVitima.id)
         AutoEntradaVitima.delete(this.autoEntradaVitima.id)
      })
    },
    editaVitima(vitima) {
      this.editedIndex = 0
      this.autoEntradaVitima = Object.assign({}, vitima)
      this.vitima =  this.autoEntradaVitima.vitima
      this.pais = Pais.query().find(this.vitima.nacionalidade_id)
      this.provincia = Provincia.query().find(this.vitima.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.vitima.tipoDocumento_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.vitima.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    mostraVitima(vitima) {
      this.editedIndex = 0
      this.autoEntradaVitima = Object.assign({}, vitima)
      this.vitima =  this.autoEntradaVitima.vitima
      this.pais = Pais.query().find(this.vitima.nacionalidade_id)
      this.provincia = Provincia.query().find(this.vitima.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.vitima.tipoDocumento_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.vitima.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.vitima_details_dialog = true
    },
    getAllVitima(offset) {
        if(offset >= 0){
          Vitima.api().get("/vitima?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllVitima(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
     getAllVitimaProcesso(offset) {
       if(offset >= 0){
          AutoEntradaVitima.api().get("/autoEntradaVitima?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllVitimaProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllTipoDocumentoIdentificacao(offset) {
      if(offset >= 0){
          TipoDocumentoIdentificacao.api().get("/tipoDocumentoIdentificacao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTipoDocumentoIdentificacao(offset), 2)
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
  // getAllProfissao(offset) {
  //      if(offset >= 0){
  //         Profissao.api().get("/profissao?offset="+offset+"&max=100").then(resp => {
  //         offset = offset + 100
  //         if(resp.response.data.length > 0) 
  //             setTimeout(this.getAllProfissao(offset), 2)
  //         }).catch(error => {
  //         console.log('Erro no code ' + error)
  //       })
  //     }
  //   },
    onFileChange(event){
      this.vitima.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.allVitimasFromAutoEntrada.map(row =>
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
      const status = exportFile('vitima_list.csv', content, 'text/csv')
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
