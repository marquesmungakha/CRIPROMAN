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
                    <q-item-label class="text-grey-9">{{ autoEntrada.numero }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('dataAbertura') }}</q-item-label>
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
                        <q-item-label lines="1" caption >{{ $t('descricao') }}</q-item-label>
                        <q-item-label class="text-grey-9">{{ autoEntrada.descricao }}</q-item-label>
                      </q-item-section>
                    </q-item>
                 </div>
                  <div class="col">
                    <q-item class="full-width">
                      <q-item-section>
                        <q-item-label lines="1" caption >{{ $t('modusOperandi') }}</q-item-label>
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
                    <q-item-label lines="1" caption >{{ $t('horaOcorrencia') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ autoEntrada.horaOcorrencia }}</q-item-label>
                  </q-item-section>
                </q-item>
                    </div>
                     <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('infraccao') }}</q-item-label>
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
                    <q-item-label lines="1" caption >{{ $t('endereco') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ autoEntrada.endereco }}</q-item-label>
                  </q-item-section>
                </q-item>
                    </div>
              <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('responsavelLocal') }}</q-item-label>
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
                    <q-item-label lines="1" caption >{{ $t('contacto') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ autoEntrada.contacto }}</q-item-label>
                  </q-item-section>
                </q-item>
                 </div>
                 <div class="col">
               <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('tipoAuto') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getTipoAuto.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                 </div>
                  </div>
                <q-separator/>
                <div class="row">
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('familiaDelitiva') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getJurisdicao.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                 </div>
                  <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('tipoLegalCrime') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getCrime.designacao }}</q-item-label>
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
                    <q-item-label class="text-grey-9">{{ getInspector.numero }} - {{ getInspector.nome }} {{ getInspector.apelido }}</q-item-label>
                  </q-item-section>
                </q-item>
                 </div>
                </div>
                <q-separator/>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaAutoEntrada(autoEntrada)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeAutoEntrada(autoEntrada)" no-caps/>
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
                <q-tab name="suspeitos" label="Suspeito" />
                <q-tab name="meiosUtilizados" label="Meio Utilizado" />
                <q-tab name="bensSubtraidos" label="Bem Subtraido" />
                <q-tab name="testeminhas" label="Testemunha" />
              </q-tabs>

              <q-separator />

              <q-tab-panels v-model="tab" animated>
                <q-tab-panel name="denunciantes">
                  <list-denunciante :autoEntrada.sync="autoEntrada"/>
                </q-tab-panel>
                <q-tab-panel name="ofendidos">
                   <list-ofendidos :autoEntrada.sync="autoEntrada"/>
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

    </div>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="localAutoEntrada.numero"
                    :dataAbertura.sync="localAutoEntrada.dataAbertura"
                    :descricao.sync="localAutoEntrada.descricao"
                    :tipoAuto.sync="tipoAuto"
                    :classeJudicial.sync="classeJudicial"
                    :crime.sync="crime"
                    :modusOperandi.sync="localAutoEntrada.modusOperandi"
                    :horaOcorrencia.sync="localAutoEntrada.horaOcorrencia"
                    :infraccao.sync="localAutoEntrada.infraccao"
                    :endereco.sync="localAutoEntrada.endereco"
                    :responsavelLocal.sync="localAutoEntrada.responsavelLocal"
                    :contacto.sync="localAutoEntrada.contacto"
                    :inspector.sync="inspector"
                    :anexo.sync="localAutoEntrada.anexo"
                    :inspectors.sync="allInspectors"
                    :crimes.sync="allCrimes"
                    :classeJudicials.sync="allclasseJudicials"
                    :tipoAutos.sync="alltipoAutos"
                    :submitting="submitting"
                    :close="close"
                    :createAutoEntrada="createAutoEntrada"
                    :removeAutoEntrada="removeAutoEntrada"/>
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
      tab: 'denunciantes',
      tab1: 'comments',
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
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('autoEntrada/getAutoEntrada', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
    this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
    this.$store.dispatch('localidade/getAllLocalidade')
  },
  computed: {
    autoEntrada: {
      get () {
        return this.$store.getters['autoEntrada/autoEntrada']
      },
      set (autoEntrada) {
        this.SET_UPDATE_AUTOENTRADA({ autoEntrada })
        this.$emit('update:autoEntrada', '')
        this.$store.commit('autoEntrada/SET_UPDATE_AUTOENTRADA', autoEntrada)
      }
    },
    allInspectors () {
      return this.$store.getters['inspector/allInspector']
    },
    allCrimes () {
      return this.$store.getters['crime/allCrime']
    },
    allclasseJudicials () {
      return this.$store.getters['jurisdicao/allJurisdicao']
    },
    alltipoAutos () {
      return this.$store.getters['tipoAuto/allTipoAuto']
    },
    allOrgaos () {
      return this.$store.getters['orgao/allOrgao']
    },
    getTipoAuto () {
      const localTipoAuto = this.alltipoAutos.filter(tipoAuto => tipoAuto.id === this.autoEntrada.tipoAuto.id)
      if (localTipoAuto.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localTipoAuto[0] }
    },
    getJurisdicao () {
      const localJurisdicao = this.allclasseJudicials.filter(jurisdicao => jurisdicao.id === this.autoEntrada.classeJudicial.id)
      if (localJurisdicao.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localJurisdicao[0] }
    },
    getCrime () {
      const localCrime = this.allCrimes.filter(crime => crime.id === this.autoEntrada.crime.id)
      if (localCrime.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localCrime[0] }
    },
    getInspector () {
      const localInspector = this.allInspectors.filter(inspector => this.autoEntrada.inspector.id === inspector.id)
      if (localInspector.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localInspector[0] }
    }
  },
  components: {
    'create-edit-form': require('components/autoEntrada/createEditForm.vue').default,
    'list-denunciante': require('pages/denunciante/index.vue').default,
    'list-ofendidos': require('pages/ofendido/index.vue').default,
    'list-suspeitos': require('pages/suspeito/index.vue').default,
    'list-meiosUtilizados': require('pages/meiosUtilizado/index.vue').default,
    'list-bensSubtraidos': require('pages/bemSubtraido/index.vue').default,
    'list-testemunhas': require('pages/testemunha/index.vue').default
  },
  methods: {
    ...mapActions('autoEntrada', ['getAllAutoEntrada', 'getAutoEntrada', 'addNewAutoEntrada', 'updateAutoEntrada', 'deleteAutoEntrada']),
    ...mapMutations('autoEntrada', ['SET_UPDATE_AUTOENTRADA']),
    removeAutoEntrada (autoEntrada) {
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
        this.deleteAutoEntrada(autoEntrada)
        this.$router.go(-1)
      })
    },
    createAutoEntrada () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localAutoEntrada.tipoAuto = this.tipoAuto
      this.localAutoEntrada.classeJudicial = this.classeJudicial
      this.localAutoEntrada.crime = this.crime
      this.localAutoEntrada.inspector = this.inspector
      this.updateAutoEntrada(this.localAutoEntrada).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.autoEntrada.designacao + ' ]'
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
    editaAutoEntrada (autoEntrada) {
      this.editedIndex = 0
      this.autoEntrada = Object.assign({}, autoEntrada)
      this.localAutoEntrada = Object.assign({}, autoEntrada)
      this.localAutoEntrada.tipoAuto = this.alltipoAutos.filter(tipoAuto => tipoAuto.id === autoEntrada.tipoAuto.id)[0]
      this.localAutoEntrada.classeJudicial = this.allclasseJudicials.filter(jurisdicao => jurisdicao.id === autoEntrada.jurisdicao.id)[0]
      this.localAutoEntrada.crime = this.allCrimes.filter(crime => crime.id === autoEntrada.crime.id)[0]
      this.localAutoEntrada.inspector = this.allInspectors.filter(inspector => autoEntrada.inspector.id === inspector.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('autoEntrada/getAutoEntrada', this.$route.params.id)
      }
      this.$store.dispatch('localidade/getAllLocalidade')
      this.$store.dispatch('distrito/getAllDistrito')
      this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
      this.show_dialog = false
      this.props = this.autoEntrada
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.autoEntrada.distrito = val
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
