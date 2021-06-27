<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allTestemunhasFromAutoEntrada" :filter="filter" binary-state-sort
             row-key="name" title="Testemunha">

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
            {{ props.row.testemunha.nome }}
            <q-popup-edit v-model="props.row.testemunha.nome">
              <q-input v-model="props.row.testemunha.nome" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="apelido" :props="props">
            {{ props.row.testemunha.apelido }}
            <q-popup-edit v-model="props.row.testemunha.apelido">
              <q-input v-model="props.row.testemunha.apelido" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="sexo" :props="props">
            {{ props.row.testemunha.sexo }}
            <q-popup-edit v-model="props.row.testemunha.sexo">
              <q-input v-model="props.row.testemunha.sexo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataNascimento" :props="props">
            {{ props.row.testemunha.dataNascimento }}
            <q-popup-edit v-model="props.row.testemunha.dataNascimento">
              <q-input v-model="props.row.testemunha.dataNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nacionalidade" :props="props">
            {{ props.row.testemunha.nacionalidade.nacionalidade }}
            <q-popup-edit v-model="props.row.testemunha.nacionalidade.nacionalidade">
              <q-input v-model="props.row.testemunha.nacionalidade.nacionalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            {{ props.row.testemunha.provincia.designacao }}
            <q-popup-edit v-model="props.row.testemunha.provincia.designacao">
              <q-input v-model="props.row.testemunha.provincia.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="estadoCivil" :props="props">
            {{ props.row.testemunha.estadoCivil }}
            <q-popup-edit v-model="props.row.testemunha.estadoCivil">
              <q-input v-model="props.row.testemunha.estadoCivil" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoDocumento" :props="props">
            {{ props.row.testemunha.tipoDocumento.designacao }}
            <q-popup-edit v-model="props.row.testemunha.tipoDocumento.designacao">
              <q-input v-model="props.row.testemunha.tipoDocumento.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="numDocumentoIndentificacao" :props="props">
            {{ props.row.testemunha.numDocumentoIndentificacao }}
            <q-popup-edit v-model="props.row.testemunha.numDocumentoIndentificacao">
              <q-input v-model="props.row.testemunha.numDocumentoIndentificacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="ocupacao" :props="props">
            {{ props.row.ocupacao }}
            <q-popup-edit v-model="props.row.ocupacao">
              <q-input v-model="props.row.ocupacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
         
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm @click="mostraTestemunha(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaTestemunha(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeTestemunha(props.row)">
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
            <div class="text-h6">Adicionar/Actualizar Testemunha!</div>
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
            <q-form class="q-gutter-md" @submit.prevent="createTestemunha">
             <div class="q-pa-md">
                <q-stepper
                  v-model="step"
                  ref="stepper"
                  color="primary"
                  header-class="text-bold"
                  animated >
                    <q-step
                      :name="1"
                      title="Verificar Testemunha Existente"
                      icon="settings"
                      :done="step > 1" >
                      <search-individuo :apelido.sync="testemunha.apelido"
                            :nome.sync="testemunha.nome"
                            :numDocumentoIndentificacao.sync="testemunha.numDocumentoIndentificacao"
                            :sexo.sync="testemunha.sexo"
                            :tipoDocumento.sync="tipoDocumento"
                            :tipoDocumentos.sync="allTipoDocumentos"
                            :findIndividuo.sync="findIndividuo"/>
                    </q-step>

                    <q-step
                      :name="2"
                      title="Criar/Actualizar Dados"
                      icon="create_new_folder"
                      :done="step > 2" >
              <individuo :apelido.sync="testemunha.apelido"
                         :dataNascimento.sync="testemunha.dataNascimento"
                         :documentoValidade.sync="testemunha.documentoValidade"
                         :estadoCivil.sync="testemunha.estadoCivil"
                         :fotografia.sync="testemunha.fotografia"
                         :idade.sync="testemunha.idade"
                         :localNascimento.sync="testemunha.localNascimento"
                         :morada.sync="testemunha.morada"
                         :naturalidade.sync="testemunha.naturalidade"
                         :nome.sync="testemunha.nome"
                         :numDocumentoIndentificacao.sync="testemunha.numDocumentoIndentificacao"
                         :pais.sync="pais"
                         :paises.sync="allPaises"
                         :provincia.sync="provincia"
                         :provincias.sync="allProvinciaFromPais"
                         :sexo.sync="testemunha.sexo"
                         :telemovel1.sync="testemunha.telemovel1"
                         :telemovel2.sync="testemunha.telemovel2"
                         :tipoDocumento.sync="tipoDocumento"
                         :tipoDocumentos.sync="allTipoDocumentos"
                         :onFileChange.sync="onFileChange"
                         :image.sync="image"/>
                          </q-step>

                    <q-step
                      :name="3"
                      title="Dados Adicionais"
                      icon="assignment">
              <create-edit-form :depoimento.sync="autoEntradaTestemunha.depoimento"
                                :ocupacao.sync="autoEntradaTestemunha.ocupacao"
                                :profissao.sync="profissao"
                                :profissaoList.sync="allProfissao"/>
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
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createTestemunha" :disable="step === 3 ? false : true"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
     <details-testemunha :testemunha.sync="testemunha" 
                       :image.sync="image" 
                       :autoEntradaTestemunha.sync="autoEntradaTestemunha" 
                       :tipoDocumento.sync="tipoDocumento"
                       :pais.sync="pais"
                       :testemunha_details_dialog.sync="testemunha_details_dialog"
                       :close.sync="close"/>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Testemunha from 'src/store/models/testemunha/testemunha'
import Pais from 'src/store/models/pais/pais'
import TipoDocumentoIdentificacao from 'src/store/models/tipoDocumentoIdentificacao/tipoDocumentoIdentificacao'
import Provincia from 'src/store/models/provincia/provincia'
import Profissao from 'src/store/models/profissao/profissao'
import AutoEntradaTestemunha from 'src/store/models/autoEntrada/autoEntradaTestemunha'
import AutoEntrada from 'src/store/models/autoEntrada/autoEntrada'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Testemunha',
  data() {
    return {
       step: 1,
      offset:0,
      listErrors: [],
      testemunha_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      image: '',
      testemunha: {
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
        provincia: {}
      },
      autoEntradaTestemunha: {
        depoimento: '',
        profissao: {},
        ocupacao: '',
        autoEntrada: {},
        testemunha: {}
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
      profissao: {
        designacao: ''
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
        {
          name: 'ocupacao',
          align: 'left',
          label: 'Ocupação',
          field: row => row.ocupacao,
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
    return  this.getAllTestemunha()
  },
  props: [
    'autoEntrada'
  ],
  mounted() {
    let offset = 0
    this.getAllTestemunhaAutoEntrada(offset)
    this.getAllProfissao(offset)
    this.getAllTestemunha(offset)
    this.getAllProvincia(offset)
    this.getAllPais(offset)
    this.getAllTipoDocumentoIdentificacao(offset)
  },
  components: {
    'create-edit-form': require('components/testemunha/createEditForm.vue').default,
    individuo: require('components/individuo/createEditForm.vue').default,
    'search-individuo': require('components/individuo/searchForm.vue').default,
    'details-testemunha': require('components/testemunha/detailsForm.vue').default
  },
  metaInfo: {},
  computed: {
    allTestemunhasFromAutoEntrada() {

      return AutoEntrada.query().with('testemunhas')
                                 .with('testemunhas.testemunha')
                                 .with('testemunhas.testemunha.nacionalidade')
                                 .with('testemunhas.testemunha.provincia')
                                 .with('testemunhas.testemunha.tipoDocumento')
                                 .with('testemunhas.profissao')
                                 .find(this.autoEntrada.id).testemunhas
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
    },
    allProfissao() {
      return Profissao.query().all()
    }
  },
  methods: {
     findIndividuo() {
      let results = undefined
      if (this.testemunnha.nome === undefined || this.testemunnha.apelido === undefined || 
          this.testemunnha.sexo === undefined || this.testemunnha.numDocumentoIndentificacao === undefined ||
          this.testemunnha.nome === "" || this.testemunnha.apelido === "" || 
          this.testemunnha.sexo === "" || this.testemunnha.numDocumentoIndentificacao === ""  
          ) {
          this.$q.notify({
          color: 'negative',
          classes: 'glossy',
          message: 'Todos os campos marcados com (*) são obrigatórios!'
        })
      }else{

          Testemunnha.api().get("/testemunnha?offset="+this.offset+"&max=100").then(resp => {
          console.log(resp)
          this.offset = this.offset + 100
          if(resp.response.data.length > 0){
                results = Testemunnha.query().where((testemunnha) => {
                return testemunnha.nome === this.testemunnha.nome && 
                       testemunnha.apelido === this.testemunnha.apelido && 
                       testemunnha.sexo === this.testemunnha.sexo &&
                       testemunnha.numDocumentoIndentificacao === this.testemunnha.numDocumentoIndentificacao 
                       }).first()
              if(results === undefined  || results === null){
                    setTimeout(this.findIndividuo, 2)
              }else{
                this.testemunnha = results
                this.pais = Pais.query().find(this.testemunnha.nacionalidade_id)
                this.provincia = Provincia.query().find(this.testemunnha.provincia_id)
                this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.testemunnha.tipoDocumento_id)
                this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.testemunnha.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
                this.$q.notify({
                    type: 'positive',
                    color: 'green-4',
                    textColor: 'white',
                    icon: 'cloud_done',
                    timeout: 2000,
                    position: 'bottom',
                    classes: 'glossy',
                    progress: true,
                    message: 'Testemunnha encontrado com successo!! [' + this.testemunnha.nome + ' ' + this.testemunnha.apelido +' ]'
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
                    message: 'Nenhum Testemunnha foi encontrado !!'
                  })
          } 
              
          }).catch(error => {
          console.log('Erro no code ' + error)
        })

      }
    },
    createTestemunha() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.provincia.pais = this.pais
      this.testemunha.nacionalidade_id = this.pais.id
      this.testemunha.provincia_id = this.provincia.id
      this.testemunha.tipoDocumento_id = this.tipoDocumento.id
      this.testemunha.profissao_id = this.profissao
      this.testemunha.autoEntrada_id = this.autoEntrada.id
      this.testemunha.nacionalidade = this.pais
      this.testemunha.provincia = this.provincia
      this.testemunha.tipoDocumento = this.tipoDocumento

      this.autoEntradaTestemunha.profissao = this.profissao
      this.autoEntradaTestemunha.autoEntrada = this.autoEntrada
      this.autoEntradaTestemunha.testemunha = this.testemunha

      if (this.editedIndex > -1) {
         AutoEntradaTestemunha.api().put("/autoEntradaTestemunha/" + this.autoEntradaTestemunha.id, this.autoEntradaTestemunha).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.autoEntradaTestemunha.testemunha.nome + ' ]'
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
       AutoEntradaTestemunha.api().post("/autoEntradaTestemunha/", this.autoEntradaTestemunha).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.autoEntradaTestemunha.testemunha.nome + ' ]'
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
    this.getAllTestemunhaAutoEntrada(offset)
    this.getAllProfissao(offset)
    this.getAllTestemunha(offset)
    this.getAllProvincia(offset)
    this.getAllPais(offset)
    this.getAllTipoDocumentoIdentificacao(offset)
      this.testemunha_details_dialog  = false
      this.step = 1
      this.offset = 0
      this.listErrors = {}
      this.show_dialog = false
      this.testemunha = {}
      this.props = this.testemunha
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeTestemunha(testemunha) {
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
          message: 'A informação foi Removida com successo! [ ' + autoEntradaTestemunha.testemunha.nome + ' ]'
        })
        AutoEntradaTestemunha.api().delete("/autoEntradaTestemunha/" + this.autoEntradaTestemunha.id)
      })
    },
    editaTestemunha(testemunha) {
      this.step = 2
      this.editedIndex = 0
      this.autoEntradaTestemunha = Object.assign({}, testemunha)
      this.testemunha =  this.autoEntradaTestemunha.testemunha
      this.pais = Pais.query().find(this.testemunha.nacionalidade_id)
      this.provincia = Provincia.query().find(this.testemunha.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.testemunha.tipoDocumento_id)
      this.profissao = Profissao.query().find(testemunha.profissao_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.testemunha.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    mostraTestemunha(testemunha) {
      this.step = 2
      this.autoEntradaTestemunha = Object.assign({}, testemunha)
      this.testemunha =  this.autoEntradaTestemunha.testemunha
      this.pais = Pais.query().find(this.testemunha.nacionalidade_id)
      this.provincia = Provincia.query().find(this.testemunha.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.testemunha.tipoDocumento_id)
      this.profissao = Profissao.query().find(testemunha.profissao_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.testemunha.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.testemunha_details_dialog = true
    },
    getAllTestemunhaAutoEntrada(offset) {
      if(offset >= 0){
          AutoEntradaTestemunha.api().get("/autoEntradaTestemunha?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTestemunhaAutoEntrada(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
     getAllTestemunha(offset) {
       if(offset >= 0){
          Testemunha.api().get("/testemunha?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllTestemunha(offset), 2)
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
  getAllProfissao(offset) {
       if(offset >= 0){
          Profissao.api().get("/profissao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllProfissao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    onFileChange(event){
      this.testemunha.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.testemunha.testemunhas.map(row =>
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
      const status = exportFile('testemunha_list.csv', content, 'text/csv')
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
