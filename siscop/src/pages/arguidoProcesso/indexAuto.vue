<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allArguidosFromPecaProcesso" :filter="filter" binary-state-sort
             row-key="name" title="Arguido">

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
            {{ props.row.arguido.nome}}
            <q-popup-edit v-model="props.row.arguido.nome">
              <q-input v-model="props.row.arguido.nome" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="apelido" :props="props">
            {{ props.row.arguido.apelido }}
            <q-popup-edit v-model="props.row.arguido.apelido">
              <q-input v-model="props.row.arguido.apelido" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="sexo" :props="props">
            {{ props.row.arguido.sexo }}
            <q-popup-edit v-model="props.row.arguido.sexo">
              <q-input v-model="props.row.arguido.sexo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataNascimento" :props="props">
            {{ props.row.arguido.dataNascimento }}
            <q-popup-edit v-model="props.row.arguido.dataNascimento">
              <q-input v-model="props.row.arguido.dataNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="naturalidade" :props="props">
            {{ props.row.arguido.naturalidade }}
            <q-popup-edit v-model="props.row.arguido.naturalidade">
              <q-input v-model="props.row.arguido.naturalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nacionalidade" :props="props">
            {{ props.row.arguido.nacionalidade.nacionalidade }}
            <q-popup-edit v-model="props.row.arguido.nacionalidade.nacionalidade">
              <q-input v-model="props.row.arguido.nacionalidade.nacionalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            {{ props.row.arguido.provincia.designacao }}
            <q-popup-edit v-model="props.row.arguido.provincia.designacao">
              <q-input v-model="props.row.arguido.provincia.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="localNascimento" :props="props">
            {{ props.row.arguido.localNascimento }}
            <q-popup-edit v-model="props.row.arguido.localNascimento">
              <q-input v-model="props.row.arguido.localNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="estadoCivil" :props="props">
            {{ props.row.arguido.estadoCivil }}
            <q-popup-edit v-model="props.row.arguido.estadoCivil">
              <q-input v-model="props.row.arguido.estadoCivil" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoDocumento" :props="props">
            {{ props.row.arguido.tipoDocumento.designacao }}
            <q-popup-edit v-model="props.row.arguido.tipoDocumento.designacao">
              <q-input v-model="props.row.arguido.tipoDocumento.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="numDocumentoIndentificacao" :props="props">
            {{ props.row.arguido.numDocumentoIndentificacao }}
            <q-popup-edit v-model="props.row.arguido.numDocumentoIndentificacao">
              <q-input v-model="props.row.arguido.numDocumentoIndentificacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="documentoValidade" :props="props">
            {{ props.row.arguido.documentoValidade }}
            <q-popup-edit v-model="props.row.arguido.documentoValidade">
              <q-input v-model="props.row.arguido.documentoValidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm @click="mostraArguido(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaArguido(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeArguido(props.row)">
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
            <div class="text-h6">Adicionar/Actualizar Arguido!</div>
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
            <q-form class="q-gutter-md" @submit.prevent="createArguido">
             <div class="q-pa-md">
                <q-stepper
                  v-model="step"
                  ref="stepper"
                  color="primary"
                  header-class="text-bold"
                  animated >
                    <q-step
                      :name="1"
                      title="Verificar Arguido Existente"
                      icon="settings"
                      :done="step > 1" >
                      <search-individuo :apelido.sync="arguido.apelido"
                            :nome.sync="arguido.nome"
                            :numDocumentoIndentificacao.sync="arguido.numDocumentoIndentificacao"
                            :sexo.sync="arguido.sexo"
                            :tipoDocumento.sync="tipoDocumento"
                            :tipoDocumentos.sync="allTipoDocumentos"
                            :findIndividuo.sync="findIndividuo"/>
                    </q-step>

                    <q-step
                      :name="2"
                      title="Criar/Actualizar Dados"
                      icon="create_new_folder"
                      :done="step > 2" >
              <individuo :apelido.sync="arguido.apelido"
                         :dataNascimento.sync="arguido.dataNascimento"
                         :documentoValidade.sync="arguido.documentoValidade"
                         :estadoCivil.sync="arguido.estadoCivil"
                         :fotografia.sync="arguido.fotografia"
                         :idade.sync="arguido.idade"
                         :localNascimento.sync="arguido.localNascimento"
                         :morada.sync="arguido.morada"
                         :naturalidade.sync="arguido.naturalidade"
                         :nome.sync="arguido.nome"
                         :numDocumentoIndentificacao.sync="arguido.numDocumentoIndentificacao"
                         :pais.sync="pais"
                         :paises.sync="allPaises"
                         :provincia.sync="provincia"
                         :provincias.sync="allProvinciaFromPais"
                         :sexo.sync="arguido.sexo"
                         :telemovel1.sync="arguido.telemovel1"
                         :telemovel2.sync="arguido.telemovel2"
                         :tipoDocumento.sync="tipoDocumento"
                         :tipoDocumentos.sync="allTipoDocumentos"
                         :onFileChange.sync="onFileChange"
                         :image.sync="image"/>
                          </q-step>

                    <q-step
                      :name="3"
                      title="Dados Adicionais"
                      icon="assignment">
      <create-edit-form :localTrabalho.sync="pecaProcessoArguido.localTrabalho"
                        :declaracao.sync="pecaProcessoArguido.declaracao"
                        :ocupacao.sync="pecaProcessoArguido.ocupacao"
                        :dataSituacaoPrisional.sync="pecaProcessoArguido.dataSituacaoPrisional"
                        :situacaoPrisional.sync="situacaoPrisional"
                        :situacaoPrisionalList.sync="allSituacaoPrisional"/>
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
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createArguido" :disable="step === 3 ? false : true"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
    <details-arguido :arguido.sync="arguido" 
                       :image.sync="image" 
                       :pecaProcessoArguido.sync="pecaProcessoArguido" 
                       :tipoDocumento.sync="tipoDocumento"
                       :pais.sync="pais"
                       :arguido_details_dialog.sync="arguido_details_dialog"
                       :close.sync="close"/>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Arguido from 'src/store/models/arguido/arguido'
import Pais from 'src/store/models/pais/pais'
import TipoDocumentoIdentificacao from 'src/store/models/tipoDocumentoIdentificacao/tipoDocumentoIdentificacao'
import Provincia from 'src/store/models/provincia/provincia'
import PecaProcessoArguido from 'src/store/models/pecaProcesso/pecaProcessoArguido'
import PecaProcesso from 'src/store/models/pecaProcesso/pecaProcesso'
import SituacaoPrisional from 'src/store/models/situacaoPrisional/situacaoPrisional'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Arguido',
  data() {
    return {
       step: 1,
      offset:0,
      listErrors: [],
      arguido_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      image: '',
      show_dialog: false,
      show_filter: false,
      arguido: {
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
       situacaoPrisional: {
        designacao: ''
      },
      pecaProcessoArguido: {
        localTrabalho: '',
        ocupacao: '',
        dataSituacaoPrisional: '',
        situacaoPrisional: {},
        arguido: {},
        pecaProcesso: {}
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
          name: 'naturalidade',
          align: 'left',
          label: 'Naturalidade',
          field: row => row.naturalidade,
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
          name: 'localNascimento',
          align: 'left',
          label: 'Local de Nascimento',
          field: row => row.localNascimento,
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
          name: 'documentoValidade',
          align: 'left',
          label: 'Validade do Documento',
          field: row => row.documentoValidade,
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
    return this.getAllArguido()
  },
  props: [
    'pecaProcesso'
  ],
  mounted() {
    let offset = 0
    this.getAllArguido(offset)
    this.getAllArguidoProcesso(offset)
    this.getAllSituacaoPrisional(offset)
    this.getAllProvincia(offset)
    this.getAllPais(offset)
    this.getAllTipoDocumentoIdentificacao(offset)
  },
  components: {
    'create-edit-form': require('components/arguido/createEditForm.vue').default,
    individuo: require('components/individuo/createEditForm.vue').default,
    'search-individuo': require('components/individuo/searchForm.vue').default,
    'details-arguido': require('components/arguido/detailsForm.vue').default
  },
  created() {
  },
  metaInfo: {},
  computed: {
    allArguidosFromPecaProcesso() {
      return PecaProcesso.query().with('arguidos')
                                 .with('arguidos.arguido')
                                 .with('arguidos.arguido.nacionalidade')
                                 .with('arguidos.arguido.provincia')
                                 .with('arguidos.arguido.tipoDocumento')
                                 .with('arguidos.arguido.profissao')
                                 .find(this.pecaProcesso.id).arguidos
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
    allSituacaoPrisional() {
      return SituacaoPrisional.query().all()
    }
  },
  methods: {
     findIndividuo() {
      let results = undefined
      if (this.arguido.nome === undefined || this.arguido.apelido === undefined || 
          this.arguido.sexo === undefined || this.arguido.numDocumentoIndentificacao === undefined ||
          this.arguido.nome === "" || this.arguido.apelido === "" || 
          this.arguido.sexo === "" || this.arguido.numDocumentoIndentificacao === ""  
          ) {
          this.$q.notify({
          color: 'negative',
          classes: 'glossy',
          message: 'Todos os campos marcados com (*) são obrigatórios!'
        })
      }else{

          Arguido.api().get("/arguido?offset="+this.offset+"&max=100").then(resp => {
          console.log(resp)
          this.offset = this.offset + 100
          if(resp.response.data.length > 0){
                results = Arguido.query().where((arguido) => {
                return arguido.nome === this.arguido.nome && 
                       arguido.apelido === this.arguido.apelido && 
                       arguido.sexo === this.arguido.sexo &&
                       arguido.numDocumentoIndentificacao === this.arguido.numDocumentoIndentificacao 
                       }).first()
              if(results === undefined  || results === null){
                    setTimeout(this.findIndividuo, 2)
              }else{
                this.arguido = results
                this.pais = Pais.query().find(this.arguido.nacionalidade_id)
                this.provincia = Provincia.query().find(this.arguido.provincia_id)
                this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.arguido.tipoDocumento_id)
                this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.arguido.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
                this.$q.notify({
                    type: 'positive',
                    color: 'green-4',
                    textColor: 'white',
                    icon: 'cloud_done',
                    timeout: 2000,
                    position: 'bottom',
                    classes: 'glossy',
                    progress: true,
                    message: 'Arguido encontrado com successo!! [' + this.arguido.nome + ' ' + this.arguido.apelido +' ]'
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
                    message: 'Nenhum Arguido foi encontrado !!'
                  })
          } 
              
          }).catch(error => {
          console.log('Erro no code ' + error)
        })

      }
    },
    createArguido() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.provincia.pais = this.pais
      this.arguido.nacionalidade_id = this.pais.id
      this.arguido.provincia_id = this.provincia.id
      this.arguido.tipoDocumento_id = this.tipoDocumento.id
      this.arguido.pecaProcesso_id = this.pecaProcesso.id
      this.arguido.nacionalidade = this.pais
      this.arguido.provincia = this.provincia
      this.arguido.tipoDocumento = this.tipoDocumento

      this.pecaProcessoArguido.situacaoPrisional = this.situacaoPrisional
      this.pecaProcessoArguido.arguido =  this.arguido
      this.pecaProcessoArguido.pecaProcesso = this.pecaProcesso
     
     console.log(this.pecaProcessoArguido)

      if (this.editedIndex > -1) {
         PecaProcessoArguido.api().patch("/pecaProcessoArguido/" + this.pecaProcessoArguido.id, this.pecaProcessoArguido).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.pecaProcessoArguido.arguido.nome + ' ]'
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
        PecaProcessoArguido.api().post("/pecaProcessoArguido/", this.pecaProcessoArguido).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.pecaProcessoArguido.arguido.nome + ' ]'
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
      this.getAllArguido(offset)
      this.getAllArguidoProcesso(offset)
      this.getAllSituacaoPrisional(offset)
      this.getAllProvincia(offset)
      this.getAllPais(offset)
      this.getAllTipoDocumentoIdentificacao(offset)
      this.arguido_details_dialog = false
      this.step = 1
      this.offset = 0
      this.listErrors = {}
      this.show_dialog = false
      this.arguido = {}
      this.props = this.arguido
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeArguido(arguido) {
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
          message: 'A informação foi Removida com successo! [ ' + arguido.nome + ' ]'
        })
         Arguido.api().delete("/pecaProcessoArguido/" + this.arguido.id)
      })
    },
    editaArguido(arguido) {
      this.step = 2
      this.editedIndex = 0
      this.pecaProcessoArguido = Object.assign({}, arguido)
      this.arguido =  this.pecaProcessoArguido.arguido
      this.pais = Pais.query().find(this.arguido.nacionalidade_id)
      this.provincia = Provincia.query().find(this.arguido.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.arguido.tipoDocumento_id)
      this.situacaoPrisional = SituacaoPrisional.query().find(arguido.situacaoPrisional_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.arguido.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    mostraArguido(arguido) {
      this.step = 2
      this.editedIndex = 0
      this.pecaProcessoArguido = Object.assign({}, arguido)
      this.arguido =  this.pecaProcessoArguido.arguido
      this.pais = Pais.query().find(this.arguido.nacionalidade_id)
      this.provincia = Provincia.query().find(this.arguido.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.arguido.tipoDocumento_id)
      this.situacaoPrisional = SituacaoPrisional.query().find(arguido.situacaoPrisional_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.arguido.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.arguido_details_dialog = true
    },
    getAllArguido(offset) {
             if(offset >= 0){
          Arguido.api().get("/arguido?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllArguido(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
     getAllArguidoProcesso(offset) {
              if(offset >= 0){
          PecaProcessoArguido.api().get("/pecaProcessoArguido?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllArguidoProcesso(offset), 2)
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
    getAllSituacaoPrisional(offset) {
          if(offset >= 0){
          SituacaoPrisional.api().get("/situacaoPrisional?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllSituacaoPrisional(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    onFileChange(event){
      this.arguido.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.arguido.arguidos.map(row =>
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
      const status = exportFile('arguido_list.csv', content, 'text/csv')
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
