<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allSuspeitosFromPecaProcesso" :filter="filter" binary-state-sort row-key="name"
             title="Suspeito">

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
            {{ props.row.suspeito.nome }}
            <q-popup-edit v-model="props.row.suspeito.nome">
              <q-input v-model="props.row.suspeito.nome" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="apelido" :props="props">
            {{ props.row.suspeito.apelido }}
            <q-popup-edit v-model="props.row.suspeito.apelido">
              <q-input v-model="props.row.suspeito.apelido" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="sexo" :props="props">
            {{ props.row.suspeito.sexo }}
            <q-popup-edit v-model="props.row.suspeito.sexo">
              <q-input v-model="props.row.suspeito.sexo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataNascimento" :props="props">
            {{ props.row.suspeito.dataNascimento }}
            <q-popup-edit v-model="props.row.suspeito.dataNascimento">
              <q-input v-model="props.row.suspeito.dataNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nacionalidade" :props="props">
            {{ props.row.suspeito.nacionalidade.nacionalidade }}
            <q-popup-edit v-model="props.row.suspeito.nacionalidade.nacionalidade">
              <q-input v-model="props.row.suspeito.nacionalidade.nacionalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            {{ props.row.suspeito.provincia.designacao }}
            <q-popup-edit v-model="props.row.suspeito.provincia.designacao">
              <q-input v-model="props.row.suspeito.provincia.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="estadoCivil" :props="props">
            {{ props.row.suspeito.estadoCivil }}
            <q-popup-edit v-model="props.row.suspeito.estadoCivil">
              <q-input v-model="props.row.suspeito.estadoCivil" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoDocumento" :props="props">
            {{ props.row.suspeito.tipoDocumento.designacao }}
            <q-popup-edit v-model="props.row.suspeito.tipoDocumento.designacao">
              <q-input v-model="props.row.suspeito.tipoDocumento.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="numDocumentoIndentificacao" :props="props">
            {{ props.row.suspeito.numDocumentoIndentificacao }}
            <q-popup-edit v-model="props.row.suspeito.numDocumentoIndentificacao">
              <q-input v-model="props.row.suspeito.numDocumentoIndentificacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="situacaoPrisional" :props="props">
            {{ props.row.situacaoPrisional.designacao }}
            <q-popup-edit v-model="props.row.situacaoPrisional.designacao">
              <q-input v-model="props.row.situacaoPrisional.designacao" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm @click="mostraSuspeito(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaSuspeito(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeSuspeito(props.row)">
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
            <div class="text-h6">Adicionar/Actualizar Suspeito!</div>
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
            <q-form class="q-gutter-md" @submit.prevent="createSuspeito">
             <div class="q-pa-md">
                <q-stepper
                  v-model="step"
                  ref="stepper"
                  color="primary"
                  header-class="text-bold"
                  animated >
                    <q-step
                      :name="1"
                      title="Verificar Suspeito Existente"
                      icon="settings"
                      :done="step > 1" >
                      <search-individuo :apelido.sync="suspeito.apelido"
                            :nome.sync="suspeito.nome"
                            :numDocumentoIndentificacao.sync="suspeito.numDocumentoIndentificacao"
                            :sexo.sync="suspeito.sexo"
                            :tipoDocumento.sync="tipoDocumento"
                            :tipoDocumentos.sync="allTipoDocumentos"
                            :findIndividuo.sync="findIndividuo"/>
                    </q-step>

                    <q-step
                      :name="2"
                      title="Criar/Actualizar Dados"
                      icon="create_new_folder"
                      :done="step > 2" >
              <individuo :apelido.sync="suspeito.apelido"
                         :dataNascimento.sync="suspeito.dataNascimento"
                         :documentoValidade.sync="suspeito.documentoValidade"
                         :estadoCivil.sync="suspeito.estadoCivil"
                         :fotografia.sync="suspeito.fotografia"
                         :idade.sync="suspeito.idade"
                         :localNascimento.sync="suspeito.localNascimento"
                         :morada.sync="suspeito.morada"
                         :naturalidade.sync="suspeito.naturalidade"
                         :nome.sync="suspeito.nome"
                         :numDocumentoIndentificacao.sync="suspeito.numDocumentoIndentificacao"
                         :pais.sync="pais"
                         :paises.sync="allPaises"
                         :provincia.sync="provincia"
                         :provincias.sync="allProvinciaFromPais"
                         :sexo.sync="suspeito.sexo"
                         :telemovel1.sync="suspeito.telemovel1"
                         :telemovel2.sync="suspeito.telemovel2"
                         :tipoDocumento.sync="tipoDocumento"
                         :tipoDocumentos.sync="allTipoDocumentos"
                         :onFileChange.sync="onFileChange"
                         :image.sync="image"/>
                          </q-step>

                    <q-step
                      :name="3"
                      title="Dados Adicionais"
                      icon="assignment">
              <create-edit-form :dataSituacaoPrisional.sync="pecaProcessoSuspeito.dataSituacaoPrisional"
                                :profissao.sync="profissao"
                                :profissaoList.sync="allProfissao"
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
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createSuspeito" :disable="step === 3 ? false : true"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>

 <details-suspeito :suspeito.sync="suspeito" 
                       :image.sync="image" 
                       :pecaProcessoSuspeito.sync="pecaProcessoSuspeito" 
                       :tipoDocumento.sync="tipoDocumento"
                       :situacaoPrisional.sync="situacaoPrisional"
                       :profissao.sync="profissao"
                       :pais.sync="pais"
                       :suspeito_details_dialog.sync="suspeito_details_dialog"
                       :close.sync="close"/>

  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Pais from 'src/store/models/pais/pais'
import TipoDocumentoIdentificacao from 'src/store/models/tipoDocumentoIdentificacao/tipoDocumentoIdentificacao'
import Provincia from 'src/store/models/provincia/provincia'
import Suspeito from 'src/store/models/suspeito/suspeito'
import Profissao from 'src/store/models/profissao/profissao'
import SituacaoPrisional from 'src/store/models/situacaoPrisional/situacaoPrisional'
import PecaProcessoSuspeito from 'src/store/models/pecaProcesso/pecaProcessoSuspeito'
import PecaProcesso from 'src/store/models/pecaProcesso/pecaProcesso'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Suspeito',
  data() {
    return {
      step: 1,
      offset:0,
      listErrors: [],
      suspeito_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      image: '',
      suspeito: {
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
        descricaoOcorrencia: '',
        profissao: {},
        situacaoPrisional: {},
        dataSituacaoPrisional: '',
        pais: {},
        provincia: {}
      },
        pecaProcessoSuspeito: {
        suspeito: {},
        pecaProcesso: {},
        profissao: {},
        situacaoPrisional:{},
        dataSituacaoPrisional: ''
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
      situacaoPrisional: {
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
          name: 'situacaoPrisional',
          align: 'left',
          label: 'Situação Prisional',
          field: row => row.situacaoPrisional,
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
    return this.getAllSuspeito()
  },
  props: [
    'pecaProcesso'
  ],
  mounted() {
    let offset = 0
    this.getAllProfissao(offset)
    this.getAllProfissaoPecaProcesso(offset)
    this.getAllSituacaoPrisional(offset)
    this.getAllSuspeito(offset)
    this.getAllProvincia(offset)
    this.getAllPais(offset)
    this.getAllTipoDocumentoIdentificacao(offset)
  },
  components: {
    'create-edit-form': require('components/suspeito/createEditForm.vue').default,
    individuo: require('components/individuo/createEditForm.vue').default,
    'search-individuo': require('components/individuo/searchForm.vue').default,
    'details-suspeito': require('components/suspeito/detailsForm.vue').default
  },
  created() {
  },
  metaInfo: {},
  computed: {
   allSuspeitosFromPecaProcesso() {
      return PecaProcesso.query().with('suspeitos')
                                 .with('suspeitos.suspeito')
                                 .with('suspeitos.suspeito.nacionalidade')
                                 .with('suspeitos.suspeito.provincia')
                                 .with('suspeitos.suspeito.tipoDocumento')
                                 .with('suspeitos.profissao')
                                 .with('suspeitos.situacaoPrisional')
                                 .find(this.pecaProcesso.id).suspeitos
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
    },
    allSituacaoPrisional() {
      return SituacaoPrisional.query().all()
    }
  },
  methods: {

     findIndividuo() {
      let results = undefined
      if (this.suspeito.nome === undefined || this.suspeito.apelido === undefined || 
          this.suspeito.sexo === undefined || this.suspeito.numDocumentoIndentificacao === undefined ||
          this.suspeito.nome === "" || this.suspeito.apelido === "" || 
          this.suspeito.sexo === "" || this.suspeito.numDocumentoIndentificacao === ""  
          ) {
          this.$q.notify({
          color: 'negative',
          classes: 'glossy',
          message: 'Todos os campos marcados com (*) são obrigatórios!'
        })
      }else{

          Suspeito.api().get("/suspeito?offset="+this.offset+"&max=100").then(resp => {
          console.log(resp)
          this.offset = this.offset + 100
          if(resp.response.data.length > 0){
                results = Suspeito.query().where((suspeito) => {
                return suspeito.nome === this.suspeito.nome && 
                       suspeito.apelido === this.suspeito.apelido && 
                       suspeito.sexo === this.suspeito.sexo &&
                       suspeito.numDocumentoIndentificacao === this.suspeito.numDocumentoIndentificacao 
                       }).first()
              if(results === undefined  || results === null){
                    setTimeout(this.findIndividuo, 2)
              }else{
                this.suspeito = results
                this.pais = Pais.query().find(this.suspeito.nacionalidade_id)
                this.provincia = Provincia.query().find(this.suspeito.provincia_id)
                this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.suspeito.tipoDocumento_id)
                this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.suspeito.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
                this.$q.notify({
                    type: 'positive',
                    color: 'green-4',
                    textColor: 'white',
                    icon: 'cloud_done',
                    timeout: 2000,
                    position: 'bottom',
                    classes: 'glossy',
                    progress: true,
                    message: 'Suspeito encontrado com successo!! [' + this.suspeito.nome + ' ' + this.suspeito.apelido +' ]'
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
                    message: 'Nenhum Suspeito foi encontrado !!'
                  })
          } 
              
          }).catch(error => {
          console.log('Erro no code ' + error)
        })

      }
    },

    createSuspeito() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
     this.provincia.pais = this.pais
      this.suspeito.nacionalidade_id = this.pais.id
      this.suspeito.provincia_id = this.provincia.id
      this.suspeito.tipoDocumento_id = this.tipoDocumento.id
      this.suspeito.pecaProcesso_id = this.pecaProcesso.id
      this.suspeito.nacionalidade = this.pais
      this.suspeito.provincia = this.provincia
      this.suspeito.tipoDocumento = this.tipoDocumento
      this.suspeito.profissao = this.profissao
      this.suspeito.profissao_id = this.profissao.id

      this.pecaProcessoSuspeito.profissao = this.profissao
      this.pecaProcessoSuspeito.profissao_id = this.profissao.id
      this.pecaProcessoSuspeito.situacaoPrisional = this.situacaoPrisional
      this.pecaProcessoSuspeito.pecaProcesso = this.pecaProcesso
      this.pecaProcessoSuspeito.suspeito = this.suspeito

      if (this.editedIndex > -1) {
         PecaProcessoSuspeito.api().patch("/pecaProcessoSuspeito/" + this.pecaProcessoSuspeito.id, this.pecaProcessoSuspeito).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.pecaProcessoSuspeito.suspeito.nome + ' ]'
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
        PecaProcessoSuspeito.api().post("/pecaProcessoSuspeito/", this.pecaProcessoSuspeito).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.pecaProcessoSuspeito.suspeito.nome + ' ]'
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
    this.getAllProfissao(offset)
    this.getAllProfissaoPecaProcesso(offset)
    this.getAllSituacaoPrisional(offset)
    this.getAllSuspeito(offset)
    this.getAllProvincia(offset)
    this.getAllPais(offset)
    this.getAllTipoDocumentoIdentificacao(offset)
      this.suspeito_details_dialog = false
      this.step = 1
      this.offset = 0
      this.listErrors = {}
      this.show_dialog = false
      this.suspeito = {}
      this.props = this.suspeito
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeSuspeito(suspeito) {
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
          message: 'A informação foi Removida com successo! [ ' + suspeito.nome + ' ]'
        })
        PecaProcessoSuspeito.api().delete("/pecaProcessoSuspeito/" + this.pecaProcessoSuspeito.suspeito.nome)
      })
    },
    editaSuspeito(suspeito) {
      this.step = 2
      this.editedIndex = 0
      this.pecaProcessoSuspeito = Object.assign({}, suspeito)
      this.suspeito = this.pecaProcessoSuspeito.suspeito
      this.pais = Pais.query().find( this.suspeito.nacionalidade_id)
      this.provincia = Provincia.query().find( this.suspeito.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.suspeito.tipoDocumento_id)
      this.profissao = Profissao.query().find(suspeito.profissao_id)
      this.situacaoPrisional = SituacaoPrisional.query().find(suspeito.situacaoPrisional_id)
      this.suspeito.pecaProcesso = this.pecaProcesso
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array( this.suspeito.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
     mostraSuspeito(suspeito) {
      this.pecaProcessoSuspeito = Object.assign({}, suspeito)
      this.suspeito = this.pecaProcessoSuspeito.suspeito
      this.pais = Pais.query().find(this.suspeito.nacionalidade_id)
      this.provincia = Provincia.query().find(this.suspeito.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.suspeito.tipoDocumento_id)
      this.profissao = Profissao.query().find(suspeito.profissao_id)
      this.situacaoPrisional = SituacaoPrisional.query().find(suspeito.situacaoPrisional_id)
      this.suspeito.pecaProcesso = this.pecaProcesso
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.suspeito.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.suspeito_details_dialog = true
    },
   getAllProfissaoPecaProcesso(offset) {
      if(offset >= 0){
          PecaProcessoSuspeito.api().get("/pecaProcessoSuspeito?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllProfissaoPecaProcesso(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllSuspeito(offset) {
      if(offset >= 0){
          Suspeito.api().get("/suspeito?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllSuspeito(offset), 2)
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
      this.suspeito.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.allSuspeitosFromPecaProcesso.map(row =>
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
      const status = exportFile('suspeito_list.csv', content, 'text/csv')
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
