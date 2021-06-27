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
                    <q-item-label class="text-grey-9">{{ processoInvestigacao.numeroProcesso }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('numeroAuto') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInvestigacao.numeroAuto_id }}</q-item-label>
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
                        <q-item-label class="text-grey-9">{{ processoInvestigacao.proveniencia }}</q-item-label>
                      </q-item-section>
                    </q-item>
                 </div>
                  <div class="col">
                    <q-item class="full-width">
                      <q-item-section>
                        <q-item-label lines="1" caption >{{ $t('dataEntrada') }}</q-item-label>
                        <q-item-label class="text-grey-9">{{ processoInvestigacao.dataEntrada }}</q-item-label>
                      </q-item-section>
                    </q-item>
                  </div>
                 </div>
                <q-separator/>
                   <div class="row">
                    <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('formaProcesso') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInvestigacao.formaProcesso.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                    </div>
                     <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('inspector') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInvestigacao.inspector.numero }} - {{ processoInvestigacao.inspector.nome }} {{ processoInvestigacao.inspector.apelido }}</q-item-label>
                  </q-item-section>
                </q-item>
                     </div>
                   </div>
                <q-separator/>
                <div class="row">
                     <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('magistrado') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ processoInvestigacao.magistrado.numero }} - {{ processoInvestigacao.magistrado.nome }} {{ processoInvestigacao.magistrado.apelido }}</q-item-label>
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
                              <q-btn flat color="primary" label="ANEXAR" no-caps :disabled="!this.selectedFile" @click.stop="onUploadFile(processoInvestigacao)"/>
                              <q-btn flat color="primary" label="VER DOCUMENTO" no-caps :disabled="!processoInvestigacao.anexo" @click.stop="forceFileDownload(processoInvestigacao,'Anexo.pdf')"/>
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
                </q-card-section>
                 <div class="row">
                <div class="col">
                  <q-card-actions align="left">
                    <q-btn v-go-back=" '/processoInvestigacao' " class="glossy" color="primary" label="Voltar" no-caps/>
                  </q-card-actions>
                </div>
                <div class="col">
                  <q-card-actions align="right">
                      <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaProcesso(processoInvestigacao)" no-caps />
                      <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeProcesso(processoInvestigacao)" no-caps/>
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
               <div class="text-h6 text-red text-center blink" v-if="processoInvestigacao.despachos.length === 0"> <span>Processo ainda não tem despacho </span></div> 
               <list-despacho :processoInvestigacao.sync="processoInvestigacao"/>
              </q-tab-panel>
            </q-tab-panels>
          </q-card>
      </div>
 </div>
    <q-separator />

            <q-card>
              <q-tabs
                v-model="tab"
                class="bg-teal text-white shadow-2"
                active-color="white"
                indicator-color="white"
                align="center"
                narrow-indicator >
                <q-tab name="acarreacao" label="Auto de Acarreação" />
                <q-tab name="apreensao" label="Auto de Apreensão" />
                <q-tab name="corpoDelito" label="Auto de Corpo de Delito" />
                <q-tab name="declaracao" label="Auto de Declaração" />
                <q-tab name="denuncia" label="Auto de Denúncia" />
                <q-tab name="detencao" label="Auto de Detenção" />
                <q-tab name="pergunta" label="Auto de Perguntas" />
              </q-tabs>

              <q-separator />

              <q-tab-panels v-model="tab" animated transition-prev="scale"  transition-next="scale">
                <q-tab-panel name="acarreacao">
                  <list-acarreacao :processoInvestigacao.sync="processoInvestigacao"/>
                </q-tab-panel>
                <q-tab-panel name="apreensao">
                   <list-apreensao :processoInvestigacao.sync="processoInvestigacao"/>
                </q-tab-panel>
                <q-tab-panel name="corpoDelito">
                   <list-corpoDelito :processoInvestigacao.sync="processoInvestigacao"/>
                </q-tab-panel>
                <q-tab-panel name="declaracao">
                  <list-declaracao :processoInvestigacao.sync="processoInvestigacao"/>
                </q-tab-panel>
                <q-tab-panel name="denuncia">
                   <list-denuncia :processoInvestigacao.sync="processoInvestigacao"/>
                </q-tab-panel>
                <q-tab-panel name="detencao">
                 <list-detencao :processoInvestigacao.sync="processoInvestigacao"/>
                </q-tab-panel>
                <q-tab-panel name="pergunta">
                 <list-pergunta :processoInvestigacao.sync="processoInvestigacao"/>
                </q-tab-panel>
              </q-tab-panels>
          </q-card>

   
    <create-edit-form :show_dialog.sync="show_dialog"
                    :listErrors="listErrors"
                    :numeroProcesso.sync="localProcesso.numeroProcesso"
                    :numeroAuto.sync="numeroAuto"
                    :proveniencia.sync="localProcesso.proveniencia"
                    :formaProcesso.sync="formaProcesso"
                    :dataEntrada.sync="localProcesso.dataEntrada"
                    :magistrado.sync="magistrado"
                    :inspector.sync="inspector"
                    :anexo.sync="localProcesso.anexo"
                    :inspectors.sync="allInspectors"
                    :formaProcessos.sync="allFormaProcessos"
                    :autoEntradas.sync="allAutoEntradas"
                    :magistrados.sync="allMagistrados"
                    :submitting="submitting"
                    :close="close"
                    :createProcesso="createProcesso"
                    :removeProcesso="removeProcesso"/>
  </q-page>
</template>

<script>
import Inspector from 'src/store/models/inspector/inspector'
import AutoEntrada from 'src/store/models/autoEntrada/autoEntrada'
import FormaProcesso from 'src/store/models/formaProcesso/formaProcesso'
import Magistrado from 'src/store/models/magistrado/magistrado'
import Orgao from 'src/store/models/orgao/orgao'
import ProcessoInvestigacao from 'src/store/models/processoInvestigacao/processoInvestigacao'
// import ProcessoInvestigacao from 'src/store/models/processoInvestigacao/processoInvestigacao'
// import TipoAuto from 'src/store/models/tipoAuto/tipoAuto'
// import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
// import Crime from 'src/store/models/crime/crime'

export default {
  name: 'Distrito',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      selectedFile: "",
      progress: 0,
      tab: 'denunciantes',
      tab1: 'despacho',
      tab2: 'comments',
      splitterModel: 20,
      localProcesso: {
        numeroProcesso: '',
        proveniencia: '',
        dataEntrada: '',
        anexo: [],
        numeroAuto: {},
        formaProcesso: {},
        magistrado: {},
        inspector: {}
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
        tipoAuto: {},
        classeJudicial: {},
        crime: {},
        inspector: {},
        orgao: {}
      },
      formaProcesso: {
        designacao: ''
      },
      inspector: {
        numero: ''
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
  //  return ProcessoInvestigacao.query().with('orgao').with('inspector').with('magistrado').with('formaProcesso').with('autoEntrada').with('despachos').find(currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    processoInvestigacao: {
      get () {
        return ProcessoInvestigacao.query()
                                   .with('orgao')
                                   .with('inspector')
                                   .with('magistrado')
                                   .with('formaProcesso')
                                   .with(['numeroAuto.orgao.provincia.pais',
                                         'numeroAuto.orgao.tipoOrgao',
                                         'numeroAuto.inspector',
                                         'numeroAuto.classeJudicial',
                                         'numeroAuto.tipoAuto',
                                         'numeroAuto.crime.*'])
                                   .with('despachos')
                                   .find(this.$route.params.id)
      },
      set (processoInvestigacao) {
        this.$emit('update:processoInvestigacao', '')
        ProcessoInvestigacao.update(processoInvestigacao)
      }
    },
    allInspectors () {
      return Inspector.query().all()
    },
    allAutoEntradas () {
      return AutoEntrada.query().withAll().hasNot('processoInvestigacao').get()
    },
      // AutoEntrada.query().with('orgao').with('inspector').with('crime.classeJudicial').with('classeJudicial').with('tipoAuto').hasNot('processoInvestigacao').get()
    allFormaProcessos () {
      return FormaProcesso.query().all()
    },
    allMagistrados () {
      return Magistrado.query().all()
    },
    allOrgaos () {
      return Orgao.query().all()
    }
  },
  components: {
    'create-edit-form': require('components/processoInvestigacao/createEditForm.vue').default,
    'list-apreensao': require('pages/apreensao/index.vue').default,
    'list-acarreacao': require('pages/acarreacao/index.vue').default,
    'list-corpoDelito': require('pages/corpoDelito/index.vue').default,
    'list-declaracao': require('pages/declaracao/index.vue').default,
    'list-denuncia': require('pages/denuncia/index.vue').default,
    'list-detencao': require('pages/detencao/index.vue').default,
    'list-pergunta': require('pages/pergunta/index.vue').default,
    'list-despacho': require('pages/despacho/index.vue').default
  },
  methods: {
    removeProcesso (processoInvestigacao) {
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
          message: 'A informação foi Removida com successo! [ ' + processoInvestigacao.designacao + ' ]'
        })
         ProcessoInvestigacao.api().delete("/processoInvestigacao/" + processoInvestigacao.id)
        this.$router.go(-1)
      })
    },
    createProcesso () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)

      this.localProcesso.formaProcesso = this.formaProcesso
      this.localProcesso.formaProcesso_id = this.formaProcesso.id

      this.localProcesso.magistrado = this.magistrado
       this.localProcesso.magistrado_id = this.magistrado.id

      this.localProcesso.inspector = this.inspector
      this.localProcesso.inspector_id = this.inspector.id
      
      this.localProcesso.numeroAuto = this.numeroAuto
      this.localProcesso.numeroAuto_id = this.numeroAuto.id

      ProcessoInvestigacao.api().patch("/processoInvestigacao/" + this.localProcesso.id, this.localProcesso).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localProcesso.designacao + ' ]'
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
     onFileChange(e) {
        const selectedFile = e.target.files[0]; // accessing file      
        this.selectedFile = selectedFile;
    },
    onUploadFile(processoInvestigacao) {
        const formData = new FormData();
        formData.append("anexo", this.selectedFile);  // appending file
      // sending file to the backend
        AutoEntrada.api().patch("/processoInvestigacao/" + processoInvestigacao.id, formData, {
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
     forceFileDownload(processoInvestigacao, title) {
      var bytes = btoa(new Uint8Array(processoInvestigacao.anexo).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      const url = "data:application/pdf;base64, " + bytes 
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', title)
      document.body.appendChild(link)
      link.click()
    },
    editaProcesso (processoInvestigacao) {
      this.editedIndex = 0
      this.processoInvestigacao = Object.assign({}, processoInvestigacao)
      this.localProcesso = Object.assign({}, processoInvestigacao)
      this.numeroAuto = AutoEntrada.query().find(processoInvestigacao.numeroAuto_id)
      this.formaProcesso = FormaProcesso.query().find(processoInvestigacao.formaProcesso_id)
      this.magistrado = Magistrado.query().find(processoInvestigacao.magistrado_id)
      this.inspector = Inspector.query().find(processoInvestigacao.inspector_id)
      this.show_dialog = true
    },
    close () {
      this.listErrors = {}
      this.show_dialog = false
      this.props = this.processoInvestigacao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.processoInvestigacao.distrito = val
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
        formaProcesso: 'Forma Processo',
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
        formaProcesso: 'Forma Processo',
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