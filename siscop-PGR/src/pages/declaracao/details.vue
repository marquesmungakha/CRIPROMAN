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
                  <q-item-label class="text-grey-9">{{ declaracao.numero }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Data Abertura') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ declaracao.dataAbertura }}</q-item-label>
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
                  <q-item-label class="text-grey-9">{{ declaracao.descricao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Inspector') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ declaracao.inspector.numero }} - {{ declaracao.inspector.nome }}
                    {{ declaracao.inspector.apelido }}
                  </q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                      <q-item-label caption lines="1">{{ $t('Documento Anexo') }}</q-item-label>
                      <q-item-label class="text-grey-9">
                        <div class="file-upload">
                            <input type="file" @change="onFileChange" accept=".pdf"/> 
                            <q-btn flat color="primary" label="ANEXAR" no-caps :disabled="!this.selectedFile" @click.stop="onUploadFile(declaracao)"/>
                            <q-btn flat color="primary" label="VER DOCUMENTO" no-caps :disabled="!declaracao.anexo" @click.stop="forceFileDownload(declaracao,'Anexo.pdf')"/>
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
              <q-btn v-go-back=" '/processoInvestigacao/'+ declaracao.processo.id " class="glossy" color="primary" label="Voltar" no-caps/>
            </q-card-actions>
          </div>
          <div class="col">
        <q-card-actions align="right">
          <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaDeclaracao(declaracao)"/>
          <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeDeclaracao(declaracao)"/>
        </q-card-actions>
          </div>
        </div>
      </q-card>
      <q-card>
        <q-tabs
          v-model="tab"
          active-color="white"
          align="center"
          class="bg-teal text-white shadow-2"
          indicator-color="white"
          narrow-indicator>
          <q-tab label="Denunciante" name="denunciantes"/>
          <q-tab label="Suspeito" name="suspeitos"/>
        </q-tabs>

        <q-separator/>

        <q-tab-panels v-model="tab" animated>
          <q-tab-panel name="denunciantes">
            <list-denunciante :pecaProcesso.sync="pecaProcesso"/>
          </q-tab-panel>
          <q-tab-panel name="suspeitos">
            <list-suspeitos :pecaProcesso.sync="pecaProcesso"/>
          </q-tab-panel>
        </q-tab-panels>
      </q-card>

    </div>
    <create-edit-form :anexo.sync="localDeclaracao.anexo"
                      :close="close"
                      :createDeclaracao="createDeclaracao"
                      :dataAbertura.sync="localDeclaracao.dataAbertura"
                      :descricao.sync="localDeclaracao.descricao"
                      :inspector.sync="inspector"
                      :inspectors.sync="allInspectors"
                      :listErrors="listErrors"
                      :numero.sync="localDeclaracao.numero"
                      :removeDeclaracao="removeDeclaracao"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import Inspector from 'src/store/models/inspector/inspector'
import PecaProcesso from 'src/store/models/pecaProcesso/pecaProcesso'
import Declaracao from 'src/store/models/declaracao/declaracao'

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
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localDeclaracao: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        anexo: [],
        inspector: {},
        orgao: {}
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
    return Declaracao.query().with('processo').with('inspector').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
     this.getPecaProcesso()
  },
  computed: {
    declaracao: {
      get() {
        return Declaracao.query().with('processo').with('inspector').find(this.$route.params.id)
      },
      set(declaracao) {
        this.$emit('update:declaracao', '')
        Declaracao.update(declaracao)
      }
    },
    pecaProcesso: {
      get() {
        return PecaProcesso.query().find(this.$route.params.id)
      }
    },
    allInspectors() {
      return Inspector.query().all()
    }
  },
  components: {
    'create-edit-form': require('components/declaracao/createEditForm.vue').default,
    'list-denunciante': require('pages/denunciante/index.vue').default,
    'list-suspeitos': require('pages/suspeito/index.vue').default
  },
  methods: {
    removeDeclaracao(declaracao) {
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
          message: 'A informação foi Removida com successo! [ ' + declaracao.numero + ' ]'
        })
         Declaracao.api().delete("/declaracao/" + this.declaracao.id)
        this.$router.go(-1)
      })
    },
    createDeclaracao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localDeclaracao.inspector = this.inspector
      this.localDeclaracao.inspector_id = this.inspector
      Declaracao.api().patch("/declaracao/" + this.localDeclaracao.id, this.localDeclaracao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.declaracao.numero + ' ]'
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
    onUploadFile(declaracao) {
        const formData = new FormData();
        formData.append("anexo", this.selectedFile);  // appending file
      // sending file to the backend
        AutoEntrada.api().patch("/declaracao/" + declaracao.id, formData, {
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
     forceFileDownload(declaracao, title) {
      var bytes = btoa(new Uint8Array(declaracao.anexo).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      const url = "data:application/pdf;base64, " + bytes 
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', title)
      document.body.appendChild(link)
      link.click()
    },
    editaDeclaracao(declaracao) {
      this.editedIndex = 0
      this.declaracao = Object.assign({}, declaracao)
      this.localDeclaracao = Object.assign({}, declaracao)
      this.inspector = Inspector.query().find(declaracao.inspector.id)
      this.show_dialog = true
    },
    close() {
      this.listErrors = {}
      this.show_dialog = false
      this.props = this.declaracao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    getPecaProcesso () {
     PecaProcesso.api().get('/pecaProcesso/'+this.$route.params.id)
    }
  },
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
    this.declaracao.distrito = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Auto de Declaração',
        basicInformation: 'Informacção do Auto de Declaração',
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
        title: 'Detalhes do Auto de Declaração',
        basicInformation: 'Informacção do Auto de Declaração',
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
