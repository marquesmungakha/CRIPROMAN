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
                    <q-item-label class="text-grey-9">{{ detencao.numero }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('dataAbertura') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ detencao.dataAbertura }}</q-item-label>
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
                        <q-item-label class="text-grey-9">{{ detencao.descricao }}</q-item-label>
                      </q-item-section>
                    </q-item>
                 </div>
                  <div class="col">
                    <q-item class="full-width">
                      <q-item-section>
                        <q-item-label lines="1" caption >{{ $t('localDetencao') }}</q-item-label>
                        <q-item-label class="text-grey-9">{{ detencao.localDetencao }}</q-item-label>
                      </q-item-section>
                    </q-item>
                  </div>
                 </div>
                <q-separator/>
                   <div class="row">
                    <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('motivoDetencao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getMotivoDetencao.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                    </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('inspector') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getInspector.numero }} - {{ getInspector.nome }} {{ getInspector.apelido }}</q-item-label>
                  </q-item-section>
                </q-item>
                 </div>
                </div>
                <q-separator/>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaDetencao(detencao)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeDetencao(detencao)" no-caps/>
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
                <q-tab name="testeminhas" label="Testemunha" />
              </q-tabs>

              <q-separator />

              <q-tab-panels v-model="tab" animated>
                <q-tab-panel name="testeminhas">
                 <list-testemunhas :autoEntrada.sync="detencao"/>
                </q-tab-panel>
              </q-tab-panels>
          </q-card>

    </div>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="localDetencao.numero"
                    :dataAbertura.sync="localDetencao.dataAbertura"
                    :descricao.sync="localDetencao.descricao"
                    :motivoDetencao.sync="motivoDetencao"
                    :localDetencao.sync="localDetencao.localDetencao"
                    :inspector.sync="inspector"
                    :anexo.sync="localDetencao.anexo"
                    :inspectors.sync="allInspectors"
                    :motivoDetencaos.sync="allMotivoDetencaos"
                    :submitting="submitting"
                    :close="close"
                    :createDetencao="createDetencao"
                    :removeDetencao="removeDetencao"/>
  </q-page>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'

export default {
  name: 'Distrito',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'testeminhas',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localDetencao: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        localDetencao: '',
        anexo: [],
        motivoDetencao: {},
        inspector: {},
        orgao: {}
      },
      motivoDetencao: {
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
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('detencao/getDetencao', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('detencao/getDetencao')
    this.$store.dispatch('motivoDetencao/getAllMotivoDetencao')
    this.$store.dispatch('inspector/getAllInspector')
    this.$store.dispatch('orgao/getAllOrgao')
  },
  computed: {
    detencao: {
      get () {
        return this.$store.getters['detencao/detencao']
      },
      set (detencao) {
        this.SET_UPDATE_DETENCAO({ detencao })
        this.$emit('update:detencao', '')
        this.$store.commit('detencao/SET_UPDATE_DETENCAO', detencao)
      }
    },
    allInspectors () {
      return this.$store.getters['inspector/allInspector']
    },
    allMotivoDetencaos () {
      return this.$store.getters['motivoDetencao/allMotivoDetencao']
    },
    allOrgaos () {
      return this.$store.getters['orgao/allOrgao']
    },
    getMotivoDetencao () {
      const localMotivoDetencao = this.allMotivoDetencaos.filter(motivoDetencao => motivoDetencao.id === this.detencao.motivoDetencao.id)
      if (localMotivoDetencao.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localMotivoDetencao[0] }
    },
    getInspector () {
      const localInspector = this.allInspectors.filter(inspector => this.detencao.inspector.id === inspector.id)
      if (localInspector.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localInspector[0] }
    }
  },
  components: {
    'create-edit-form': require('components/detencao/createEditForm.vue').default,
    'list-testemunhas': require('pages/testemunha/index.vue').default
  },
  methods: {
    ...mapActions('detencao', ['getAllDetencao', 'getDetencao', 'addNewDetencao', 'updateDetencao', 'deleteDetencao']),
    ...mapMutations('detencao', ['SET_UPDATE_DETENCAO']),
    removeDetencao (detencao) {
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
          message: 'A informação foi Removida com successo! [ ' + detencao.designacao + ' ]'
        })
        this.deleteDetencao(detencao)
        this.$router.go(-1)
      })
    },
    createDetencao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localDetencao.motivoDetencao = this.motivoDetencao
      this.localDetencao.inspector = this.inspector
      this.updateDetencao(this.localDetencao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.detencao.designacao + ' ]'
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
    editaDetencao (detencao) {
      this.editedIndex = 0
      this.detencao = Object.assign({}, detencao)
      this.localDetencao = Object.assign({}, detencao)
      this.motivoDetencao = this.allMotivoDetencaos.filter(motivoDetencao => motivoDetencao.id === detencao.motivoDetencao.id)[0]
      this.localDetencao.inspector = this.allInspectors.filter(inspector => detencao.inspector.id === inspector.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('detencao/getDetencao', this.$route.params.id)
      }
      this.$store.dispatch('motivoDetencao/getAllMotivoDetencao')
      this.$store.dispatch('inspector/getAllInspector')
      this.$store.dispatch('orgao/getAllOrgao')
      this.show_dialog = false
      this.props = this.detencao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.detencao.distrito = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Auto de Detenção',
        basicInformation: 'Informacção do Auto de Detenção',
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
        title: 'Detalhes do Auto de Detenção',
        basicInformation: 'Informacção do Auto de Detenção',
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
