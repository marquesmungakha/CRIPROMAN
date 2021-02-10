<template>
  <q-page>
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
                  <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ acarreacao.numero }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('dataAbertura') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ acarreacao.dataAbertura }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('descricao') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ acarreacao.descricao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('inspector') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ acarreacao.inspector.numero }} - {{ acarreacao.inspector.nome }}  {{ acarreacao.inspector.apelido }}
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
                         <q-btn flat color="primary" label="ANEXAR" no-caps :disabled="!this.selectedFile" @click.stop="onUploadFile(acarreacao)"/>
                         <q-btn flat color="primary" label="VER DOCUMENTO" no-caps :disabled="!acarreacao.anexo" @click.stop="forceFileDownload(acarreacao,'Anexo.pdf')"/>
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
              <q-btn v-go-back=" '/processoInvestigacao/'+ acarreacao.processo.id " class="glossy" color="primary" label="Voltar" no-caps/>
            </q-card-actions>
          </div>
          <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaAcarreacao(acarreacao)"/>
            <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeAcarreacao(acarreacao)"/>
          </q-card-actions>
          </div>
         </div>
      </q-card>
      <q-card>
        <q-tabs
          v-model="tab1"
          active-color="white"
          align="center"
          class="bg-teal text-white shadow-2"
          indicator-color="white"
          narrow-indicator>
          <q-tab label="Declarante" name="declarantes"/>
          <q-tab label="Suspeito" name="suspeitos"/>
          <q-tab label="Testemunha" name="testeminhas"/>
        </q-tabs>

        <q-separator/>

        <q-tab-panels v-model="tab1" animated transition-prev="scale"  transition-next="scale">
          <q-tab-panel name="declarantes">
            <list-declarante :pecaProcesso.sync="pecaProcesso"/>
          </q-tab-panel>
          <q-tab-panel name="suspeitos">
            <list-suspeitos :pecaProcesso.sync="pecaProcesso"/>
          </q-tab-panel>
          <q-tab-panel name="testeminhas">
            <list-testemunhas :pecaProcesso.sync="pecaProcesso"/>
          </q-tab-panel>
        </q-tab-panels>
      </q-card>

    </div>
    <create-edit-form :anexo.sync="localAcarreacao.anexo"
                      :close="close"
                      :createAcarreacao="createAcarreacao"
                      :dataAbertura.sync="localAcarreacao.dataAbertura"
                      :descricao.sync="localAcarreacao.descricao"
                      :inspector.sync="inspector"
                      :inspectors.sync="allInspectors"
                      :listErrors="listErrors"
                      :numero.sync="localAcarreacao.numero"
                      :removeAcarreacao="removeAcarreacao"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import Acarreacao from 'src/store/models/acarreacao/acarreacao'
import Inspector from 'src/store/models/inspector/inspector'
import Orgao from 'src/store/models/orgao/orgao'
import PecaProcesso from 'src/store/models/pecaProcesso/pecaProcesso'

export default {
  name: 'Acarreacao',
  data() {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
     selectedFile: "",
      progress: 0,
      tab: 'denunciantes',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localAcarreacao: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        anexo: [],
        inspector: {},
        orgao: {},
        processo: {}
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
    return Acarreacao.query().with('processo').with('inspector').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
     this.getPecaProcesso()
  },
  computed: {
    acarreacao: {
      get() {
        return Acarreacao.query().with('processo').with('inspector').find(this.$route.params.id)
      },
      set(acarreacao) {
        this.$emit('update:acarreacao', '')
        Acarreacao.update(acarreacao)
      }
    },
    pecaProcesso: {
      get() {
        return PecaProcesso.query().find(this.$route.params.id)
      }
    },
    allInspectors() {
      return Inspector.query().all()
    },
    allOrgaos() {
      return Orgao.query().all()
    }
  },
  components: {
    'create-edit-form': require('components/acarreacao/createEditForm.vue').default,
    'list-declarante': require('pages/declarante/index.vue').default,
    'list-suspeitos': require('pages/suspeito/index.vue').default,
    'list-testemunhas': require('pages/testemunha/index.vue').default
  },
  methods: {
    removeAcarreacao(acarreacao) {
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
          message: 'A informação foi Removida com successo! [ ' + acarreacao.numero + ' ]'
        })
        Acarreacao.api().delete("/acarreacao/" + acarreacao.id)
        this.$router.go(-1)
      })
    },
    createAcarreacao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localAcarreacao.inspector_id = this.inspector.id
      this.localAcarreacao.inspector = this.inspector
      Acarreacao.api().patch("/acarreacao/" + this.localAcarreacao.id, this.localAcarreacao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.acarreacao.numero + ' ]'
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
    onUploadFile(acarreacao) {
        const formData = new FormData();
        formData.append("anexo", this.selectedFile);  // appending file
      // sending file to the backend
        AutoEntrada.api().patch("/acarreacao/" + acarreacao.id, formData, {
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
     forceFileDownload(acarreacao, title) {
      var bytes = btoa(new Uint8Array(acarreacao.anexo).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      const url = "data:application/pdf;base64, " + bytes 
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', title)
      document.body.appendChild(link)
      link.click()
    },
    editaAcarreacao(acarreacao) {
      this.editedIndex = 0
      this.acarreacao = Object.assign({}, acarreacao)
      this.localAcarreacao = Object.assign({}, acarreacao)
      this.inspector = Inspector.query().find(acarreacao.inspector.id)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.props = this.acarreacao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
  getPecaProcesso () {
     PecaProcesso.api().get('/pecaProcesso/'+this.$route.params.id)
  },
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
    this.acarreacao.distrito = val
  },
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Auto de Acarreação',
        basicInformation: 'Informacção do Auto de Acarreação',
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
        title: 'Detalhes do Auto de Acarreação',
        basicInformation: 'Informacção do Auto de Acarreação',
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
</style>
