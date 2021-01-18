<template>
  <q-page>
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
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ acarreacao.numero }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('dataAbertura') }}</q-item-label>
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
                        <q-item-label lines="1" caption >{{ $t('descricao') }}</q-item-label>
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
                    <q-item-label lines="1" caption >{{ $t('inspector') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ acarreacao.inspector.numero }} - {{ acarreacao.inspector.nome }} {{ acarreacao.inspector.apelido }}</q-item-label>
                  </q-item-section>
                </q-item>
                 </div>
                </div>
                <q-separator/>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaAcarreacao(acarreacao)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeAcarreacao(acarreacao)" no-caps/>
                </q-card-actions>
            </q-card>
            <q-card>
              <q-tabs
                v-model="tab"
                class="bg-teal text-white shadow-2"
                active-color="white"
                indicator-color="white"
                align="center"
                narrow-indicator >
                <q-tab name="denunciantes" label="Denunciante" />
                <q-tab name="ofendidos" label="Ofendido" />
              </q-tabs>

              <q-separator />

              <q-tab-panels v-model="tab" animated>
                <q-tab-panel name="denunciantes">
                  <list-denunciante :autoEntrada.sync="acarreacao"/>
                </q-tab-panel>
                <q-tab-panel name="ofendidos">
                   <list-ofendidos :autoEntrada.sync="acarreacao"/>
                </q-tab-panel>
              </q-tab-panels>
          </q-card>

    </div>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="localAcarreacao.numero"
                    :dataAbertura.sync="localAcarreacao.dataAbertura"
                    :descricao.sync="localAcarreacao.descricao"
                    :inspector.sync="inspector"
                    :anexo.sync="localAcarreacao.anexo"
                    :inspectors.sync="allInspectors"
                    :submitting="submitting"
                    :close="close"
                    :createAcarreacao="createAcarreacao"
                    :removeAcarreacao="removeAcarreacao"/>
  </q-page>
</template>

<script>
import {QSpinnerBall} from "quasar"
import Acarreacao from 'src/store/models/acarreacao/acarreacao'
import Inspector from 'src/store/models/inspector/inspector'
import Orgao from 'src/store/models/orgao/orgao'

export default {
  name: 'Distrito',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
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
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // Return a Promise if you are running an async job
    // Example:
    return Acarreacao.query().with('processo','inspector').find(currentRoute.params.id)
  },
  created () {
      this.$q.loading.show({
      message: "Caregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })

    setTimeout(() => {
      this.$q.loading.hide()
    }, 600)
  },
  mounted () {
  },
  computed: {
    acarreacao: {
      get () {
        return Acarreacao.find(this.$route.params.id)
      },
      set (acarreacao) {
        this.$emit('update:acarreacao', '')
        Acarreacao.update(acarreacao)
      }
    },
    allInspectors () {
      return Inspector.query().all()
    },
    allOrgaos () {
      return Orgao.query().all()
    }
  },
  components: {
    'create-edit-form': require('components/acarreacao/createEditForm.vue').default,
    'list-denunciante': require('pages/denunciante/index.vue').default,
    'list-ofendidos': require('pages/ofendido/index.vue').default
  },
  methods: {
    removeAcarreacao (acarreacao) {
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
          message: 'A informação foi Removida com successo! [ ' + acarreacao.designacao + ' ]'
        })
        Acarreacao.api().delete("/acarreacao/" + acarreacao.id)
        this.$router.go(-1)
      })
    },
    createAcarreacao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localAcarreacao.inspector = this.inspector
      Acarreacao.api().patch("/acarreacao/" + this.localAcarreacao.id,this.localAcarreacao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.acarreacao.designacao + ' ]'
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
    editaAcarreacao (acarreacao) {
      this.editedIndex = 0
      this.acarreacao = Object.assign({}, acarreacao)
      this.localAcarreacao = Object.assign({}, acarreacao)
      this.inspector = Inspector.query().find(acarreacao.inspector.id)
      this.show_dialog = true
    },
    close () {
      this.show_dialog = false
      this.props = this.acarreacao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.acarreacao.distrito = val
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
