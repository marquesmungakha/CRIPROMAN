<template>
  <q-page>
  <div class="row q-col-gutter-sm q-ma-xs">
        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
          <q-card class="my-card" flat bordered>
            <q-card-section class="bg-secondary text-white">
                <div class="text-h6">{{ $t('basicInformation') }}</div>
            </q-card-section>
            <q-separator/>
            <q-card-section class="bg-white text-grey">
              <div class="row">
                <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('numeroProcesso') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.numeroProcesso }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('Historial') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.historial }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
              </div>
                  <q-separator/>
                  <div class="row">
                <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('Jurisdição/Famílias Diletivas') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.classeJudicial.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('Accções/Crimes') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.accoesCrimes.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
              </div>
              <q-separator/>
                 <div class="row">
                 <div class="col">
                    <q-item class="full-width">
                      <q-item-section>
                        <q-item-label lines="1" caption >{{ $t('proveniencia') }}</q-item-label>
                        <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.proveniencia }}</q-item-label>
                      </q-item-section>
                    </q-item>
                 </div>
                  <div class="col">
                    <q-item class="full-width">
                      <q-item-section>
                        <q-item-label lines="1" caption >{{ $t('Data de Entrada') }}</q-item-label>
                        <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.dataEntrada }}</q-item-label>
                      </q-item-section>
                    </q-item>
                  </div>
                 </div>
                <q-separator/>
                   <div class="row">
                    <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('Forma Processo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.formaProcesso.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                    </div>
                    <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('magistrado') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.magistrado.numero }} - {{ processoInstrucaoPreparatoria.magistrado.nome }} {{ processoInstrucaoPreparatoria.magistrado.apelido }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                   </div>
                <q-separator/>
                 <div class="row">
                 <div class="col">
                    <q-item class="full-width">
                      <q-item-section>
                        <q-item-label lines="1" caption >{{ $t('Processo com Autor') }}</q-item-label>
                        <q-item-label class="text-grey-9">{{ processoInstrucaoPreparatoria.autor }}</q-item-label>
                      </q-item-section>
                    </q-item>
                 </div>
                <div class="col">
                  <q-item class="full-width">
                  <q-item-section>
                        <q-item-label caption lines="1">{{ $t('Documento Anexo') }}</q-item-label>
                        <q-item-label class="text-grey-9">
                          <div class="file-upload">
                              <input type="file" @change="onFileChange" accept=".pdf"/> 
                              <q-btn flat color="primary" label="ANEXAR" no-caps :disabled="!this.selectedFile" @click.stop="onUploadFile(processoInstrucaoPreparatoria)"/>
                              <q-btn flat color="primary" label="VER DOCUMENTO" no-caps :disabled="processoInstrucaoPreparatoria.anexo === null ? false : true" @click.stop="forceFileDownload(processoInstrucaoPreparatoria,'Anexo.pdf')"/>
                              <div v-if="progress" class="progess-bar">
                                    <div
                                      class="progress-bar progress-bar-info progress-bar-striped"
                                      role="progressbar"
                                      :aria-valuenow="progress"
                                      aria-valuemin="0"
                                      aria-valuemax="100"
                                      :style="{ width: progress + '%' }">
                                      {{ progress }}
                                    </div>
                                  </div>
                          </div>
                        </q-item-label>
                    </q-item-section>
                    </q-item>
                  </div>
                </div>
                 <q-separator/>
                <div class="row"  v-if="processoInstrucaoPreparatoria.processoInvestigacao === null">
                <div>
                <q-input bottom-slots v-model="numAuto" label="Número do Processo" counter maxlength="50" :dense="false"   @input="$emit('update:numAuto', $event)">
                    <template v-slot:append>
                      <q-avatar>
                        <img src="~assets/SernicLogo.png/">
                      </q-avatar>
                    </template>
                    <template v-slot:hint>
                      Introduza o Número
                    </template>
                  </q-input>
                </div>
                   <q-card-actions align="center">
                    <q-btn label="Carregar" size="16px" class="q-px-xl q-py-xs glossy" color="teal" @click.stop="updateProcessoInvestigacao(numAuto,processoInstrucaoPreparatoria)"/>
                     </q-card-actions>
                </div>
                  
                </q-card-section>
                 <div class="row">
                <div class="col">
                  <q-card-actions align="left">
                    <q-btn v-go-back=" '/processoInstrucaoPreparatoria' " class="glossy" color="primary" label="Voltar" no-caps/>
                  </q-card-actions>
                </div>
                <div class="col">
                  <q-card-actions align="right">
                      <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaProcesso(processoInstrucaoPreparatoria)" no-caps />
                      <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeProcesso(processoInstrucaoPreparatoria)" no-caps/>
                  </q-card-actions>
                </div>
                 </div>
            </q-card>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
          <q-card>
            <q-tabs
              v-model="tab1"
              active-color="white"
              align="center"
              class="bg-teal text-white shadow-2"
              indicator-color="white"
              narrow-indicator>
              <q-tab label="despacho" name="despacho"/>
            </q-tabs>

            <q-separator/>
            <q-tab-panels v-model="tab1" animated>
              <q-tab-panel name="despacho">
               <div class="text-h6 text-red text-center blink" v-if="processoInstrucaoPreparatoria.pareceresProcesso.length === 0"> <span>Processo ainda não tem despacho </span></div> 
               <list-despacho :processoInstrucaoPreparatoria.sync="processoInstrucaoPreparatoria"/>
              </q-tab-panel>
            </q-tab-panels>
          </q-card>
      </div>
 </div>
 <div v-if="processoInstrucaoPreparatoria.processoInvestigacao !== null" class="progess-bar">
  <q-separator/>
    <div class="row q-col-gutter-sm q-ma-xs">
        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
       <details-auto :autoEntrada.sync="processoInstrucaoPreparatoria.processoInvestigacao.numeroAuto"/>
        </div>
		<div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
         <details-processo-investigacao :processoInvestigacao.sync="processoInstrucaoPreparatoria.processoInvestigacao"/>
      </div>
 </div>
 </div>

  <q-separator/>
    <q-card>
      <q-tabs
        v-model="tab2"
        class="bg-teal text-white shadow-2"
        active-color="white"
        indicator-color="white"
        align="center"
        narrow-indicator >
        <q-tab name="denunciante" label="denunciante" />
        <q-tab name="vitima" label="Vítima" />
        <q-tab name="arguido" label="Arguido" />
      </q-tabs>

      <q-separator />

      <q-tab-panels v-model="tab2" animated transition-prev="scale"  transition-next="scale">
        <q-tab-panel name="denunciante">
          <list-denunciante :processoInstrucaoPreparatoria.sync="processoInstrucaoPreparatoria"/>
        </q-tab-panel>
        <q-tab-panel name="vitima">
            <list-vitima :processoInstrucaoPreparatoria.sync="processoInstrucaoPreparatoria"/>
        </q-tab-panel>
        <q-tab-panel name="arguido">
            <list-arguido :processoInstrucaoPreparatoria.sync="processoInstrucaoPreparatoria"/>
        </q-tab-panel>
      </q-tab-panels>
  </q-card>

   
   <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numeroProcesso.sync="processoInstrucaoPreparatoria.numeroProcesso"
                    :proveniencia.sync="processoInstrucaoPreparatoria.proveniencia"
                    :historial.sync="processoInstrucaoPreparatoria.historial"
                    :formaProcesso.sync="formaProcesso"
                    :classeJudicial.sync="classeJudicial"
                    :classeJudicials.sync="allclasseJudicials"
                    :crime.sync="crime"
                    :crimes.sync="allCrimesFromClasseJudicial"
                    :dataEntrada.sync="processoInstrucaoPreparatoria.dataEntrada"
                    :magistrado.sync="magistrado"
                    :anexo.sync="processoInstrucaoPreparatoria.anexo"
                    :autor.sync="processoInstrucaoPreparatoria.autor"
                    :formaProcessos.sync="allFormaProcessos"
                    :magistrados.sync="allMagistrados"
                    :submitting="submitting"
                    :close="close"
                    :createProcesso="createProcesso"
                    :removeProcesso="removeProcesso"/>
  </q-page>
</template>

<script>  
import {QSpinnerBall, QSpinnerGears} from 'quasar'
import FormaProcesso from 'src/store/models/formaProcesso/formaProcesso'
import Magistrado from 'src/store/models/magistrado/magistrado'
import Orgao from 'src/store/models/orgao/orgao'
import ProcessoInstrucaoPreparatoria from 'src/store/models/processoInstrucaoPreparatoria/processoInstrucaoPreparatoria'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
import Crime from 'src/store/models/crime/crime'
import axios from 'axios'
import ProcessoInvestigacao from 'src/store/models/processoInvestigacao/processoInvestigacao'
import AutoEntrada from 'src/store/models/autoEntrada/autoEntrada'


export default {
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'denunciantes',
      tab1: 'despacho',
      tab2: 'comments',
      selectedFile: "",
      numAuto:'',
      progress: 0,
      splitterModel: 20,
      localProcesso: {
       numeroProcesso: '',
        proveniencia: '',
        dataEntrada: '',
        anexo: [],
        autor: '',
        historial: '',
        formaProcesso: {},
        magistrado: {},
        classeJudicial: {},
        accoesCrimes: {},
        orgao: {}
      },
      processoInvestigacao: {
        numeroProcesso: '',
        proveniencia: '',
        dataEntrada: '',
        anexo: [],
        autor:'',
        numeroAuto: '',
        formaProcesso: '',
        magistrado: '',
        inspector: '',
        uuid: ''
      },
      numeroAuto: {
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
        tipoAuto: '',
        classeJudicial: '',
        crime: '',
        inspector: '',
        orgao: '',
        uuid:''
      },
      crime: {
        designacao: ''
      },
      formaProcesso: {
        designacao: ''
      },
      classeJudicial: {
        designacao: ''
      },
      magistrado: {
        numero: ''
      },
      orgao: {
        codigo: '',
        designacao: ''
      }
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
    return ProcessoInstrucaoPreparatoria
    .query()
    .with('magistrado')
    .with('formaProcesso')
    .with('classeJudicial')
    .with('accoesCrimes.classeJudicial')
    .with('pareceresProcesso')
    .with('processoInvestigacao.*')
    .with('orgao.tipoOrgao')
    .with('orgao.provincia.pais')
    .find(currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    let offset = 0
    this.getAllAutoEntrada(offset)
    this.getAllProcessoInvestigacao(offset)
  },
  computed: {
    processoInstrucaoPreparatoria: {
      get () {
        return ProcessoInstrucaoPreparatoria
        .query()
        .with('magistrado')
        .with('formaProcesso')
        .with('classeJudicial')
        .with('accoesCrimes.classeJudicial')
        .with('pareceresProcesso')
        .with('processoInvestigacao.*')
        .with('orgao.tipoOrgao')
        .with('orgao.provincia.pais')
        .with('orgao.distrito.provincia.pais')
        .find(this.$route.params.id)
      },
      set (processoInstrucaoPreparatoria) {
        this.$emit('update:processoInstrucaoPreparatoria', '')
        ProcessoInstrucaoPreparatoria.update(processoInstrucaoPreparatoria)
      }
    },
    allFormaProcessos () {
      return FormaProcesso.query().where('activo', true).all()
    },
    allMagistrados () {
      return Magistrado.query().all()
    },
    allCrimes() {
      return Crime.query().all()
    },
    allCrimesFromClasseJudicial() {
      if( this.classeJudicial != null)
          return Crime.query().where('classeJudicial_id', this.classeJudicial.id).get()
      else
          return Crime.query().get(0)
    },
    allclasseJudicials() {
      return ClasseJudicial.query().all()
    },
    allOrgaos () {
      return Orgao.query().all()
    }
  },
  components: {
    'create-edit-form': require('components/processoInstrucaoPreparatoria/createEditForm.vue').default,
    'list-denunciante': require('pages/denuncianteProcesso/index.vue').default,
    'list-vitima': require('pages/vitimaProcesso/index.vue').default,
    'list-arguido': require('pages/arguidoProcesso/index.vue').default,
    'list-despacho': require('pages/parecerProcesso/index.vue').default,
    'details-processo-investigacao': require('components/processoInvestigacao/detailsForm.vue').default,
    'details-auto': require('components/autoEntrada/detailsForm.vue').default
  },
  methods: {
    removeProcesso (processoInstrucaoPreparatoria) {
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
          message: 'A informação foi Removida com successo! [ ' + processoInstrucaoPreparatoria.numeroProcessos + ' ]'
        })
         ProcessoInstrucaoPreparatoria.api().delete("/processoInstrucaoPreparatoria/" + processoInstrucaoPreparatoria.id)
         ProcessoInstrucaoPreparatoria.delete(processoInstrucaoPreparatoria.id)
        this.$router.go(-1)
      })
    },
    createProcesso () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)

      this.orgao = Orgao.query().with('provincia.*')
                                .with('tipoOrgao')
                                .with('distrito.provincia.*')
                                .find(localStorage.getItem('orgaoId'))


      this.localProcesso.formaProcesso_id = this.formaProcesso.id
      this.localProcesso.formaProcesso = this.formaProcesso

      this.localProcesso.magistrado_id = this.magistrado.id
      this.localProcesso.magistrado = this.magistrado

      this.localProcesso.classeJudicial = this.classeJudicial
      this.crime.classeJudicial = this.classeJudicial

      this.localProcesso.accoesCrimes_id = this.crime.id
      this.localProcesso.accoesCrimes = this.crime

      this.localProcesso.orgao = this.orgao
      this.localProcesso.orgao_id = this.orgao.id

      ProcessoInstrucaoPreparatoria.api().patch("/processoInstrucaoPreparatoria/" + this.localProcesso.id, this.localProcesso).then(resp => {
        console.log('update' + resp)
        this.$q.notify({
          type: 'positive',
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 2000,
          position: 'bottom',
          classes: 'glossy',
          progress: true,
          message: 'A informação foi actualizada com successo!! [ ' + this.localProcesso.numeroProcesso + ' ]'
        })
        this.close()
      }).catch(error => {
        console.log('error' + error)
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
    },

    async updateProcessoInvestigacao(numeroProcesso,processoInstrucaoPreparatoria) {

      let token = this.getTokenFromSernic()
      if(token !== null){
        await axios.get("http://192.168.1.4:8080/api/processoInvestigacao",  
        this.$q.loading.show({
          message: "Identificando o Processo de Investigação Nº. "+numeroProcesso,
          spinnerColor: "grey-4",
          spinner: QSpinnerGears
        })).then(response => {
          if(response.data.length > 0){
            
            let processo = response.data.filter(item => {
              return item.numeroProcesso === numeroProcesso
              });

            if(processo.length > 0){
              this.getAutoAndPatch(processo[0],processoInstrucaoPreparatoria)
            }else{
               this.$q.notify({
                type: 'warning',
                color: 'red',
                textColor: 'white',
                icon: 'cloud_done',
                timeout: 2000,
                position: 'bottom',
                classes: 'glossy',
                progress: true,
                message: 'O processo nº '+numeroProcesso+' não foi localizado !!!!'
                })
              }
          }
              this.timer = setTimeout(() => {
              this.$q.loading.hide()
              this.timer = void 0
            }, 3000)
        }).catch(error => { 
              this.timer = setTimeout(() => {
              this.$q.loading.hide()
              this.timer = void 0
            }, 3000)

            this.$q.notify({
                type: 'warning',
                color: 'red',
                textColor: 'white',
                icon: 'cloud_done',
                timeout: 2000,
                position: 'bottom',
                classes: 'glossy',
                progress: true,
                message: 'Foi identificado um erro no carregamento do processo !!!!'
          })
             console.error("There was an error!", error) 
             });
      }
    },
 
   async getAutoAndPatch(processo,processoInstrucaoPreparatoria){
          await axios.get("http://192.168.1.4:8080/api/autoEntrada/"+processo.numeroAuto.id)
                      .then(response => {
                             this.patchProcesssoInvestigacao(response.data,processo,processoInstrucaoPreparatoria)
                  }).catch(error => { 
                        this.timer = setTimeout(() => {
                        this.$q.loading.hide()
                        this.timer = void 0
                      }, 3000)
                      console.log(error)
                      this.$q.notify({
                          type: 'warning',
                          color: 'red',
                          textColor: 'white',
                          icon: 'cloud_done',
                          timeout: 2000,
                          position: 'bottom',
                          classes: 'glossy',
                          progress: true,
                          message: 'Foi identificado um erro no carregamento do processo !!!!'
                    })
                });          
   },

async patchProcesssoInvestigacao(autoEntrada,processo,processoInstrucaoPreparatoria){

let formaProcesso = await axios.get("http://192.168.1.4:8080/api/formaProcesso/"+processo.formaProcesso.id)
let classeJudicial = await axios.get("http://192.168.1.4:8080/api/classeJudicial/"+autoEntrada.classeJudicial.id)
let crime = await axios.get("http://192.168.1.4:8080/api/crime/"+autoEntrada.crime.id)
let orgao = await axios.get("http://192.168.1.4:8080/api/orgao/"+autoEntrada.orgao.id)
let tipoAuto = await axios.get("http://192.168.1.4:8080/api/tipoAuto/"+autoEntrada.tipoAuto.id)

this.numeroAuto.numero = autoEntrada.numero
this.numeroAuto.dataAbertura = autoEntrada.dataAbertura
this.numeroAuto.descricao = autoEntrada.descricao
this.numeroAuto.modusOperandi = autoEntrada.modusOperandi
this.numeroAuto.horaOcorrencia = autoEntrada.horaOcorrencia
this.numeroAuto.infraccao = autoEntrada.infraccao
this.numeroAuto.endereco = autoEntrada.endereco
this.numeroAuto.responsavelLocal = autoEntrada.responsavelLocal
this.numeroAuto.contacto = autoEntrada.contacto
this.numeroAuto.tipoAuto = tipoAuto.data.designacao
this.numeroAuto.classeJudicial = classeJudicial.data.designacao
this.numeroAuto.crime = crime.data.designacao
this.numeroAuto.inspector = 'Anonimo'
this.numeroAuto.orgao = orgao.data.designacao
this.numeroAuto.uuid = autoEntrada.uuid

 this.processoInvestigacao.numeroProcesso = processo.numeroProcesso
 this.processoInvestigacao.proveniencia = processo.proveniencia
 this.processoInvestigacao.dataEntrada = processo.dataEntrada
 this.processoInvestigacao.anexo = processo.anexo
 this.processoInvestigacao.autor = processo.autor
 this.processoInvestigacao.numeroAuto = this.numeroAuto
 this.processoInvestigacao.formaProcesso = formaProcesso.data.designacao
 this.processoInvestigacao.magistrado = 'Anonimo'
 this.processoInvestigacao.inspector = 'Anonimo'
 this.processoInvestigacao.uuid = processo.uuid

 processoInstrucaoPreparatoria.processoInvestigacao = this.processoInvestigacao
 ProcessoInstrucaoPreparatoria.api().patch("/processoInstrucaoPreparatoria/" + processoInstrucaoPreparatoria.id, processoInstrucaoPreparatoria,  this.$q.loading.show({
          message: "Carregando o Processo ...",
          spinnerColor: "grey-4",
          spinner: QSpinnerGears
        })).then(resp => {
        console.log('update' + resp)
         this.$q.loading.hide()
        this.$q.notify({
          type: 'positive',
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 2000,
          position: 'bottom',
          classes: 'glossy',
          progress: true,
         message: 'O Processo foi actualizada com successo!'
        })
        this.close()
      }).catch(error => {
         this.$q.loading.hide()
        this.$q.notify({
          type: 'warning',
          color: 'red',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 2000,
          position: 'bottom',
          classes: 'glossy',
          progress: true,
         message: 'O Processo ja foi assossiado!'
        })
      console.log(error)
      })
      ProcessoInstrucaoPreparatoria.update(processoInstrucaoPreparatoria.id)
},
  async getTokenFromSernic(){
  let token = null
    await axios.post("http://192.168.1.4:8080/api/login", {username:'admin',password:'admin'}, 
    this.$q.loading.show({
      message: "Connectando ao Servidor do SERNIC ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
    })).then(response => {
       token = response.data.access_token
       localStorage.setItem('sernicToken',token)
       this.$q.loading.hide()
      })
      .catch(error => {
          this.$q.loading.hide()
          this.$q.notify({
          type: 'warning',
          color: 'red',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 6600,
          position: 'top-right',
          classes: 'glossy',
          progress: true,
          message: 'O servidor do SERNIC não está operacional de momento, por favor verifique a sua internet ou tenta mais tarde!'
        })
      })
     return token
  },
    onFileChange(e) {
        const selectedFile = e.target.files[0]; // accessing file      
        this.selectedFile = selectedFile;
    },
    onUploadFile(processoInstrucaoPreparatoria) {
        const formData = new FormData();
        formData.append("anexo", this.selectedFile);  // appending file
      // sending file to the backend
        ProcessoInstrucaoPreparatoria.api().patch("/processoInstrucaoPreparatoria/" + processoInstrucaoPreparatoria.id, formData, {
          onUploadProgress: ProgressEvent => { 
            let progress = Math.round((ProgressEvent.loaded / ProgressEvent.total) * 100)+"%";
            this.progress = progress;
            } 
        })
          .then(res => {
            console.log(res);
            this.selectedFile = null
          })
          .catch(err => {
            console.log(err);
          });
    },
     forceFileDownload(processoInstrucaoPreparatoria, title) {
      var bytes = btoa(new Uint8Array(processoInstrucaoPreparatoria.anexo).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      const url = "data:application/pdf;base64, " + bytes 
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', title)
      document.body.appendChild(link)
      link.click()
    },
    editaProcesso (processoInstrucaoPreparatoria) {
      this.editedIndex = 0
      this.processoInstrucaoPreparatoria = Object.assign({}, processoInstrucaoPreparatoria)
      this.localProcesso = Object.assign({}, processoInstrucaoPreparatoria)
      this.formaProcesso = FormaProcesso.query().find(processoInstrucaoPreparatoria.formaProcesso_id)
      this.magistrado = Magistrado.query().find(processoInstrucaoPreparatoria.magistrado_id)
      this.crime = Crime.query().find(processoInstrucaoPreparatoria.accoesCrimes_id)
      this.classeJudicial =  ClasseJudicial.query().find(processoInstrucaoPreparatoria.classeJudicial_id)
      this.show_dialog = true
    },
    close () {
      this.listErrors = {}
      this.show_dialog = false
      this.props = this.processoInstrucaoPreparatoria
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
      async getAllAutoEntrada(offset) {
      if(offset >= 0){
        await AutoEntrada.api().get("/autoEntrada?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllAutoEntrada(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
    async getAllProcessoInvestigacao(offset) {
      if(offset >= 0){
        await ProcessoInvestigacao.api().get("/processoInvestigacao?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllProcessoInvestigacao(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
      }
    },
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.processoInstrucaoPreparatoria.distrito = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Processo',
        basicInformation: 'Informacção do Processo',
        numeroProcesso: 'Número do Processo',
        numeroAuto: 'Número do Auto',
        proveniencia: 'Proveniência',
        dataEntrada: 'Data de Entrada',
        anexo: 'Anexo',
        formaProcesso: 'FormaProcesso',
        inspector: 'Inspector',
        magistrado: 'Magistrado',
        agentes: 'Agentes'
      },
      en: {
        title: 'Detalhes do Processo',
        basicInformation: 'Informacção do Processo',
        numeroProcesso: 'Número do Processo',
        numeroAuto: 'Número do Auto',
        proveniencia: 'Proveniência',
        dataEntrada: 'Data de Entrada',
        anexo: 'Anexo',
        formaProcesso: 'FormaProcesso',
        inspector: 'Inspector',
        magistrado: 'Magistrado',
        agentes: 'Agentes'
      }
    }
  }
}
</script>
<style scoped>
.blink{
		text-align: center;
		line-height: 50px;
	}
	span{
		font-size: 25px;
		font-family: cursive;
    font-weight: bold;
		color: rgb(252, 4, 4);
		animation: blink 1s linear infinite;
	}
@keyframes blink{
0%{opacity: 0.1;}
50%{opacity: .5;}
100%{opacity: 1;}
}
</style>