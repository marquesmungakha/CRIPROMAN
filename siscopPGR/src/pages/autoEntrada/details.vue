<template>
  <q-page>
    <div class="row q-col-gutter-sm q-ma-xs">
    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
      <q-card bordered class="my-card" flat>
        <q-card-section class="bg-secondary text-white">
          <div class="text-h6">{{ $t('basicInformation') }}</div>
        </q-card-section>
        <q-separator/>
        <q-card-section class="bg-white text-grey">
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Número') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.numero }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Data Abertura') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.dataAbertura }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Descrição') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.descricao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Modus Operandi') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.modusOperandi }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Data e hora da Ocorrência') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.horaOcorrencia }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Infração') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.infraccao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Endereço') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.endereco }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Responsável do Local') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.responsavelLocal }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Contacto') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.contacto }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Tipo Auto') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.tipoAuto.designacao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Família Delitiva') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.classeJudicial.designacao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Tipo Legal de Crime') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.crime.designacao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Inspector') }}</q-item-label>
                  <q-item-label class="text-grey-9">
                 {{ autoEntrada.inspector.numero }} - {{ autoEntrada.inspector.nome }} {{ autoEntrada.inspector.apelido }}
                  </q-item-label>
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
                         <q-btn flat color="primary" label="ANEXAR" no-caps :disabled="!this.selectedFile" @click.stop="onUploadFile(autoEntrada)"/>
                         <q-btn flat color="primary" label="VER DOCUMENTO" no-caps :disabled="autoEntrada.anexo === null ? false : true" @click.stop="forceFileDownload(autoEntrada,'Anexo.pdf')"/>
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
              <q-btn v-go-back=" '/autoEntrada' " class="glossy" color="primary" label="Voltar" no-caps/>
            </q-card-actions>
          </div>
          <div class="col">
            <q-card-actions align="right">
              <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaAutoEntrada(autoEntrada)"/>
              <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeAutoEntrada(autoEntrada)"/>
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
               <div class="text-h6 text-red text-center blink" v-if="autoEntrada.pareceresAuto.length === 0"> <span>Auto ainda não tem despacho </span></div> 
               <list-despacho :autoEntrada.sync="autoEntrada"/>
              </q-tab-panel>
            </q-tab-panels>
          </q-card>
      </div>
    </div>

      <q-card>
        <q-tabs
          v-model="tab"
          active-color="white"
          align="center"
          class="bg-teal text-white shadow-2"
          indicator-color="white"
          narrow-indicator>
          <q-tab label="Denunciante" name="denunciantes"/>
          <q-tab label="Ofendido" name="ofendidos"/>
          <q-tab label="Vítima" name="vitimas"/>
          <q-tab label="Suspeito" name="suspeitos"/>
          <q-tab label="Meio Empregue" name="meiosUtilizados"/>
          <q-tab label="Prejuízos Causados" name="bensSubtraidos"/>
          <q-tab label="Testemunha" name="testeminhas"/>
        </q-tabs>

        <q-separator/>

        <q-tab-panels v-model="tab" animated transition-prev="scale"  transition-next="scale">
          <q-tab-panel name="denunciantes">
            <list-denunciante :autoEntrada.sync="autoEntrada"/>
          </q-tab-panel>
          <q-tab-panel name="ofendidos">
            <list-ofendidos :autoEntrada.sync="autoEntrada"/>
          </q-tab-panel>
           <q-tab-panel name="vitimas">
            <list-vitimas :autoEntrada.sync="autoEntrada"/>
          </q-tab-panel>
          <q-tab-panel name="suspeitos">
            <list-suspeitos :autoEntrada.sync="autoEntrada"/>
          </q-tab-panel>
          <q-tab-panel name="meiosUtilizados">
            <list-meiosUtilizados :autoEntrada.sync="autoEntrada"/>
          </q-tab-panel>
          <q-tab-panel name="bensSubtraidos">
            <list-bensSubtraidos :autoEntrada.sync="autoEntrada"/>
          </q-tab-panel>
          <q-tab-panel name="testeminhas">
            <list-testemunhas :autoEntrada.sync="autoEntrada"/>
          </q-tab-panel>
        </q-tab-panels>
      </q-card>

    <create-edit-form :anexo.sync="localAutoEntrada.anexo"
                      :classeJudicial.sync="classeJudicial"
                      :classeJudicials.sync="allclasseJudicials"
                      :close="close"
                      :contacto.sync="localAutoEntrada.contacto"
                      :createAutoEntrada="createAutoEntrada"
                      :crime.sync="crime"
                      :crimes.sync="allCrimes"
                      :dataAbertura.sync="localAutoEntrada.dataAbertura"
                      :descricao.sync="localAutoEntrada.descricao"
                      :endereco.sync="localAutoEntrada.endereco"
                      :horaOcorrencia.sync="localAutoEntrada.horaOcorrencia"
                      :infraccao.sync="localAutoEntrada.infraccao"
                      :inspector.sync="inspector"
                      :inspectors.sync="allInspectors"
                      :listErrors="listErrors"
                      :modusOperandi.sync="localAutoEntrada.modusOperandi"
                      :numero.sync="localAutoEntrada.numero"
                      :removeAutoEntrada="removeAutoEntrada"
                      :responsavelLocal.sync="localAutoEntrada.responsavelLocal"
                      :show_dialog="show_dialog"
                      :submitting="submitting"
                      :tipoAuto.sync="tipoAuto"
                      :tipoAutos.sync="alltipoAutos"/>
  </q-page>
</template>

<script>
import AutoEntrada from 'src/store/models/autoEntrada/autoEntrada'
import Inspector from 'src/store/models/inspector/inspector'
import Crime from 'src/store/models/crime/crime'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'
import TipoAuto from 'src/store/models/tipoAuto/tipoAuto'
import Orgao from 'src/store/models/orgao/orgao'
import Provincia from 'src/store/models/provincia/provincia'

export default {
  name: 'Distrito',
  data() {
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
      localAutoEntrada: {
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
        codigo: '',
        designacao: ''
      },
      orgao: {
        codigo: '',
        designacao: ''
      }
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
    return AutoEntrada.query().with('orgao')
                              .with('orgao.tipoOrgao')
                              .with('orgao.provinncia.*')
                              .with('orgao.distrito.provinncia.*')
                              .with('inspector')
                              .with('crime')
                              .with('classeJudicial')
                              .with('tipoAuto')
                              .has('pareceresAuto').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    autoEntrada: {
      get() {
        return AutoEntrada.query().with('orgao')
                                  .with('orgao.tipoOrgao')
                                  .with('orgao.provincia.pais')
                                  .with('orgao.distrito.provincia.pais')
                                  .with('inspector')
                                  .with('crime')
                                  .with('crime.classeJudicial')
                                  .with('classeJudicial')
                                  .with('tipoAuto')
                                  .with('pareceresAuto').find(Number(this.$route.params.id))
      },
      set(autoEntrada) {
        this.$emit('update:autoEntrada', '')
        AutoEntrada.update(autoEntrada)
      }
    },
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
     allProvincias() {
      return Provincia.query().with('pais').get()
    },
    allOrgaos() {
      return Orgao.query().with('provincia').get()
    }
  },
  components: {
    'create-edit-form': require('components/autoEntrada/createEditForm.vue').default,
    'list-denunciante': require('pages/denunciante/indexAuto.vue').default,
    'list-ofendidos': require('pages/ofendido/indexAuto.vue').default,
    'list-vitimas': require('pages/vitima/indexAuto.vue').default,
    'list-suspeitos': require('pages/suspeito/indexAuto.vue').default,
    'list-meiosUtilizados': require('pages/meiosUtilizado/index.vue').default,
    'list-bensSubtraidos': require('pages/bemSubtraido/index.vue').default,
    'list-testemunhas': require('pages/testemunha/indexAuto.vue').default,
    'list-despacho': require('pages/parecerAuto/index.vue').default
  },
  methods: {
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
          message: 'A informação foi Removida com successo! [ ' + autoEntrada.designacao + ' ]'
        })
        AutoEntrada.api().delete("/autoEntrada/" + autoEntrada.id)
        AutoEntrada.delete(autoEntrada.id)
        this.$router.go(-1)
      })
    },
    createAutoEntrada() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)


      this.orgao = Orgao.query().with('provincia.*')
                                .with('tipoOrgao')
                                .with('distrito.provincia.*')
                                .find(localStorage.getItem('orgaoId'))


      this.localAutoEntrada.tipoAuto = this.tipoAuto
      this.crime.classeJudicial = this.classeJudicial
      this.localAutoEntrada.classeJudicial = this.classeJudicial
      this.localAutoEntrada.crime = this.crime
      this.localAutoEntrada.inspector = this.inspector
      this.localAutoEntrada.tipoAuto_id = this.tipoAuto.id
      this.localAutoEntrada.classeJudicial_id = this.classeJudicial.id
      this.localAutoEntrada.crime_id = this.crime.id
      this.localAutoEntrada.inspector_id = this.inspector.id
      this.localAutoEntrada.orgao_id = this.orgao.id
      this.localAutoEntrada.orgao = this.orgao


      AutoEntrada.api().patch("/autoEntrada/" + this.localAutoEntrada.id, this.localAutoEntrada).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localAutoEntrada.designacao + ' ]'
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
    onUploadFile(autoEntrada) {
        const formData = new FormData();
        formData.append("anexo", this.selectedFile);  // appending file
      // sending file to the backend
        AutoEntrada.api().patch("/autoEntrada/" + autoEntrada.id, formData, {
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
     forceFileDownload(autoEntrada, title) {
      var bytes = btoa(new Uint8Array(autoEntrada.anexo).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      const url = "data:application/pdf;base64, " + bytes 
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', title)
      document.body.appendChild(link)
      link.click()
    },
    editaAutoEntrada(autoEntrada) {
      this.editedIndex = 0
      this.autoEntrada = Object.assign({}, autoEntrada)
      this.localAutoEntrada = Object.assign({}, autoEntrada)
      this.tipoAuto = TipoAuto.query().find(autoEntrada.tipoAuto_id)
      this.classeJudicial = ClasseJudicial.query().find(autoEntrada.classeJudicial_id)
      this.crime = Crime.query().find(autoEntrada.crime_id)
      this.inspector = Inspector.query().find(autoEntrada.inspector_id)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.props = this.autoEntrada
      this.listErrors = {}
      this.tipoAuto = {}
      this.classeJudicial = {}
      this.crime = {}
      this.inspector = {}
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
  },
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
  },
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Auto de Entrada',
        basicInformation: 'Informacção do Auto de Entrada',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        postoAdministrativo: 'Posto Administrativo',
        localidade: 'Localidade',
        denunciantes: 'Denunciante',
        ofendidos: 'Ofendidos',
        suspeitos: 'Suspeitos',
        meiosUtilizados: 'Meios Utilizados',
        bensSubtraidos: 'Bens Subtraidos',
        testeminhas: 'Testemunha',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Auto de Entrada',
        basicInformation: 'Informacção do Auto de Entrada',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        postoAdministrativo: 'Posto Administrativo',
        localidade: 'Localidade',
        denunciantes: 'Denunciante',
        ofendidos: 'Ofendidos',
        suspeitos: 'Suspeitos',
        meiosUtilizados: 'Meios Utilizados',
        bensSubtraidos: 'Bens Subtraidos',
        testeminhas: 'Testemunha',
        comments: 'Comentários'
      }
    }
  }
}
</script>

<style scoped>
.progess-bar {
  height: 1rem;
  width: 0%;
  background-color: #bbeee3;
  color: rgb(90, 90, 90);
  padding: 2px;
  font-weight: bold;
}
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