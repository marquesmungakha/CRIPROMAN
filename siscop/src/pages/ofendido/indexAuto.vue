<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allOfendidosFromAutoEntrada" :filter="filter" binary-state-sort row-key="name"
             title="Ofendido">

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
            {{ props.row.ofendido.nome }}
            <q-popup-edit v-model="props.row.ofendido.nome">
              <q-input v-model="props.row.ofendido.nome" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="apelido" :props="props">
            {{ props.row.ofendido.apelido }}
            <q-popup-edit v-model="props.row.ofendido.apelido">
              <q-input v-model="props.row.ofendido.apelido" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="sexo" :props="props">
            {{ props.row.ofendido.sexo }}
            <q-popup-edit v-model="props.row.ofendido.sexo">
              <q-input v-model="props.row.ofendido.sexo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataNascimento" :props="props">
            {{ props.row.ofendido.dataNascimento }}
            <q-popup-edit v-model="props.row.ofendido.dataNascimento">
              <q-input v-model="props.row.ofendido.dataNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
      
          <q-td key="nacionalidade" :props="props">
            {{ props.row.ofendido.nacionalidade.nacionalidade }}
            <q-popup-edit v-model="props.row.ofendido.nacionalidade.nacionalidade">
              <q-input v-model="props.row.ofendido.nacionalidade.nacionalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            {{ props.row.ofendido.provincia.designacao }}
            <q-popup-edit v-model="props.row.ofendido.provincia.designacao">
              <q-input v-model="props.row.ofendido.provincia.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
         
          <q-td key="estadoCivil" :props="props">
            {{ props.row.ofendido.estadoCivil }}
            <q-popup-edit v-model="props.row.ofendido.estadoCivil">
              <q-input v-model="props.row.ofendido.estadoCivil" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoDocumento" :props="props">
            {{ props.row.ofendido.tipoDocumento.designacao }}
            <q-popup-edit v-model="props.row.ofendido.tipoDocumento.designacao">
              <q-input v-model="props.row.ofendido.tipoDocumento.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="numDocumentoIndentificacao" :props="props">
            {{ props.row.ofendido.numDocumentoIndentificacao }}
            <q-popup-edit v-model="props.row.ofendido.numDocumentoIndentificacao">
              <q-input v-model="props.row.ofendido.numDocumentoIndentificacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
             <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm @click="mostraOfendido(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaOfendido(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeOfendido(props.row)">
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
            <div class="text-h6">Adicionar/Actualizar Ofendido!</div>
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
            <q-form class="q-gutter-md" @submit.prevent="createOfendido">
             <div class="q-pa-md">
                <q-stepper
                  v-model="step"
                  ref="stepper"
                  color="primary"
                  header-class="text-bold"
                  animated >
                    <q-step
                      :name="1"
                      title="Verificar Ofendido Existente"
                      icon="settings"
                      :done="step > 1" >
                      <search-individuo :apelido.sync="ofendido.apelido"
                            :nome.sync="ofendido.nome"
                            :numDocumentoIndentificacao.sync="ofendido.numDocumentoIndentificacao"
                            :sexo.sync="ofendido.sexo"
                            :tipoDocumento.sync="tipoDocumento"
                            :tipoDocumentos.sync="allTipoDocumentos"
                            :findIndividuo.sync="findIndividuo"/>
                    </q-step>

                    <q-step
                      :name="2"
                      title="Criar/Actualizar Dados"
                      icon="create_new_folder"
                      :done="step > 2" >
              <individuo :apelido.sync="ofendido.apelido"
                         :dataNascimento.sync="ofendido.dataNascimento"
                         :documentoValidade.sync="ofendido.documentoValidade"
                         :estadoCivil.sync="ofendido.estadoCivil"
                         :fotografia.sync="ofendido.fotografia"
                         :idade.sync="ofendido.idade"
                         :localNascimento.sync="ofendido.localNascimento"
                         :morada.sync="ofendido.morada"
                         :naturalidade.sync="ofendido.naturalidade"
                         :nome.sync="ofendido.nome"
                         :numDocumentoIndentificacao.sync="ofendido.numDocumentoIndentificacao"
                         :pais.sync="pais"
                         :paises.sync="allPaises"
                         :provincia.sync="provincia"
                         :provincias.sync="allProvinciaFromPais"
                         :sexo.sync="ofendido.sexo"
                         :telemovel1.sync="ofendido.telemovel1"
                         :telemovel2.sync="ofendido.telemovel2"
                         :tipoDocumento.sync="tipoDocumento"
                         :tipoDocumentos.sync="allTipoDocumentos"
                         :onFileChange.sync="onFileChange"
                         :image.sync="image"/>
                </q-step>

                  <template v-slot:navigation>
                    <q-stepper-navigation>
                      <q-btn @click="$refs.stepper.next()" color="primary" :label="step === 2 ? 'Terminou' : 'Próximo'" :disable="step === 2 ? true : false"/>
                      <q-btn v-if="step > 1" flat color="primary" @click="$refs.stepper.previous()" label="Voltar" class="q-ml-sm" />
                    </q-stepper-navigation>
                  </template>
                </q-stepper>
              </div>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createOfendido" :disable="step === 2 ? false : true"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
 <details-ofendido :ofendido.sync="ofendido" 
                       :image.sync="image" 
                       :autoEntradaOfendido.sync="autoEntradaOfendido" 
                       :tipoDocumento.sync="tipoDocumento"
                       :pais.sync="pais"
                       :ofendido_details_dialog.sync="ofendido_details_dialog"
                       :close.sync="close"/>

  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Pais from 'src/store/models/pais/pais'
import TipoDocumentoIdentificacao from 'src/store/models/tipoDocumentoIdentificacao/tipoDocumentoIdentificacao'
import Provincia from 'src/store/models/provincia/provincia'
import Ofendido from 'src/store/models/ofendido/ofendido'
import AutoEntradaOfendido from 'src/store/models/autoEntrada/autoEntradaOfendido'
import AutoEntrada from 'src/store/models/autoEntrada/autoEntrada'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Ofendido',
  data() {
    return {
       step: 1,
      offset:0,
      listErrors: [],
      ofendido_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      image: '',
      ofendido: {
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
        localTrabalho: '',
        ocupacao: '',
        qualidadeDe: '',
        pais: {},
        provincia: {}
      },
       autoEntradaOfendido: {
        ofendido: {},
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
  preFetch({}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return  this.getAllOfendido()
  },
  props: [
    'autoEntrada'
  ],
  mounted() {
    let offset = 0 
    this.getAllOfendido(offset)
    this.getAllOfendidoAutoEntrada(offset)
    this.getAllProvincia(offset)
    this.getAllPais(offset)
    this.getAllTipoDocumentoIdentificacao(offset)
  },
  components: {
    individuo: require('components/individuo/createEditForm.vue').default,
    'search-individuo': require('components/individuo/searchForm.vue').default,
    'details-ofendido': require('components/ofendido/detailsForm.vue').default
  },
    created() {
  },
  metaInfo: {},
  computed: {
   allOfendidosFromAutoEntrada() {
      return AutoEntrada.query().with('ofendidos')
                                 .with('ofendidos.ofendido')
                                 .with('ofendidos.ofendido.nacionalidade')
                                 .with('ofendidos.ofendido.provincia')
                                 .with('ofendidos.ofendido.tipoDocumento')
                                 .with('ofendidos.ofendido.profissao')
                                 .find(this.autoEntrada.id).ofendidos
    },
     // return .query().with('provincia').with('nacionalidade').with('tipoDocumento').where('autoEntrada_id', this.autoEntrada.id).get()
    
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
      if (this.ofendido.nome === undefined || this.ofendido.apelido === undefined || 
          this.ofendido.sexo === undefined || this.ofendido.numDocumentoIndentificacao === undefined ||
          this.ofendido.nome === "" || this.ofendido.apelido === "" || 
          this.ofendido.sexo === "" || this.ofendido.numDocumentoIndentificacao === ""  
          ) {
          this.$q.notify({
          color: 'negative',
          classes: 'glossy',
          message: 'Todos os campos marcados com (*) são obrigatórios!'
        })
      }else{
          Ofendido.api().get("/ofendido?offset="+this.offset+"&max=100").then(resp => {
        this.submitting = false
          console.log(resp)
          this.offset = this.offset + 100
          if(resp.response.data.length > 0){
                results = Ofendido.query().where((ofendido) => {
                return ofendido.nome === this.ofendido.nome && 
                       ofendido.apelido === this.ofendido.apelido && 
                       ofendido.sexo === this.ofendido.sexo &&
                       ofendido.numDocumentoIndentificacao === this.ofendido.numDocumentoIndentificacao 
                       }).first()
              if(results === undefined || results === null){
                    setTimeout(this.findIndividuo, 2)
              }else{
                this.ofendido = results
                this.pais = Pais.query().find(this.ofendido.nacionalidade_id)
                this.provincia = Provincia.query().find(this.ofendido.provincia_id)
                this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.ofendido.tipoDocumento_id)
                this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.ofendido.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
                this.$q.notify({
                    type: 'positive',
                    color: 'green-4',
                    textColor: 'white',
                    icon: 'cloud_done',
                    timeout: 2000,
                    position: 'bottom',
                    classes: 'glossy',
                    progress: true,
                    message: 'Ofendido encontrado com successo!! [' + this.ofendido.nome + ' ' + this.ofendido.apelido +' ]'
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
                    message: 'Nenhum Ofendido foi encontrado !!'
                  })
          } 
              
          }).catch(error => {
          console.log('Erro no code ' + error)
        })

      }
    },
    createOfendido() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)
      this.provincia.pais = this.pais
      this.ofendido.nacionalidade_id = this.pais.id
      this.ofendido.provincia_id = this.provincia.id
      this.ofendido.tipoDocumento_id = this.tipoDocumento.id
      this.ofendido.autoEntrada_id = this.autoEntrada.id
      this.ofendido.nacionalidade = this.pais
      this.ofendido.provincia = this.provincia
      this.ofendido.tipoDocumento = this.tipoDocumento

      this.autoEntradaOfendido.ofendido = this.ofendido
      this.autoEntradaOfendido.autoEntrada = this.autoEntrada

      if (this.editedIndex > -1) {
        AutoEntradaOfendido.api().patch("/autoEntradaOfendido/" + this.autoEntradaOfendido.id, this.autoEntradaOfendido).then(() => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.ofendido.nome + ' ]'
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
       AutoEntradaOfendido.api().post("/autoEntradaOfendido/", this.autoEntradaOfendido).then(resp => {
        this.submitting = false
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
            message: 'A informação foi inserida com successo! [ ' + this.ofendido.nome + ' ]'
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
      this.getAllOfendido(offset)
      this.getAllOfendidoAutoEntrada(offset)
      this.getAllProvincia(offset)
      this.getAllPais(offset)
      this.getAllTipoDocumentoIdentificacao(offset)
      this.ofendido_details_dialog = false
      this.step = 1
      this.offset = 0
      this.listErrors = {}
      this.show_dialog = false
      this.ofendido = {}
      this.props = this.ofendido
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeOfendido(ofendido) {
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
          message: 'A informação foi Removida com successo! [ ' + ofendido.nome + ' ]'
        })
        AutoEntradaOfendido.api().delete("/autoEntradaOfendido/" + this.autoEntradaOfendido.id)
        AutoEntradaOfendido.delete(+ this.autoEntradaOfendido.id)
      })
    },
    editaOfendido(ofendido) {
      this.step = 2
      this.editedIndex = 0
      this.autoEntradaOfendido = Object.assign({}, ofendido)
      this.ofendido =  this.autoEntradaOfendido.ofendido
      this.pais = Pais.query().find(this.ofendido.nacionalidade_id)
      this.provincia = Provincia.query().find(this.ofendido.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.ofendido.tipoDocumento_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.ofendido.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    mostraOfendido(ofendido) {
      this.autoEntradaOfendido = Object.assign({}, ofendido)
      this.ofendido =  this.autoEntradaOfendido.ofendido
      this.pais = Pais.query().find(this.ofendido.nacionalidade_id)
      this.provincia = Provincia.query().find(this.ofendido.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.ofendido.tipoDocumento_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.ofendido.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.ofendido_details_dialog = true
    },
    getAllOfendido(offset) {
      if(offset >= 0){
          Ofendido.api().get("/ofendido?offset="+offset+"&max=100").then(resp => {
        this.submitting = false
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllOfendido(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    }, 
     getAllOfendidoAutoEntrada(offset) {
        if(offset >= 0){
          AutoEntradaOfendido.api().get("/autoEntradaOfendido?offset="+offset+"&max=100").then(resp => {
        this.submitting = false
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllOfendidoAutoEntrada(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
    getAllTipoDocumentoIdentificacao(offset) {
      if(offset >= 0){
          TipoDocumentoIdentificacao.api().get("/tipoDocumentoIdentificacao?offset="+offset+"&max=100").then(resp => {
        this.submitting = false
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
        this.submitting = false
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
        this.submitting = false
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllProvincia(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    onFileChange(event) {
      this.ofendido.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.allOfendidosFromAutoEntrada.map(row =>
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
      const status = exportFile('ofendido_list.csv', content, 'text/csv')
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
