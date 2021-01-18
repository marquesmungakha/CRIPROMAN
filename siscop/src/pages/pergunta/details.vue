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
                    <q-item-label class="text-grey-9">{{ pergunta.numero }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('dataAbertura') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ pergunta.dataAbertura }}</q-item-label>
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
                        <q-item-label class="text-grey-9">{{ pergunta.descricao }}</q-item-label>
                      </q-item-section>
                    </q-item>
                 </div>
                  <div class="col">
                    <q-item class="full-width">
                      <q-item-section>
                        <q-item-label lines="1" caption >{{ $t('interrogatorio') }}</q-item-label>
                        <q-item-label class="text-grey-9">{{ pergunta.interrogatorio }}</q-item-label>
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
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaPergunta(pergunta)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removePergunta(pergunta)" no-caps/>
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
                <q-tab name="suspeitos" label="Suspeito" />
              </q-tabs>

              <q-separator />

              <q-tab-panels v-model="tab" animated>
                <q-tab-panel name="suspeitos">
                   <list-suspeitos :autoEntrada.sync="pergunta"/>
                </q-tab-panel>
              </q-tab-panels>
          </q-card>

    </div>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="localPergunta.numero"
                    :dataAbertura.sync="localPergunta.dataAbertura"
                    :descricao.sync="localPergunta.descricao"
                    :interrogatorio.sync="localPergunta.interrogatorio"
                    :inspector.sync="inspector"
                    :anexo.sync="localPergunta.anexo"
                    :inspectors.sync="allInspectors"
                    :submitting="submitting"
                    :close="close"
                    :createPergunta="createPergunta"
                    :removePergunta="removePergunta"/>
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
      tab: 'suspeitos',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localPergunta: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        interrogatorio: '',
        anexo: [],
        inspector: {},
        orgao: {}
      },
      inspector: {
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
    return store.dispatch('pergunta/getPergunta', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('inspector/getAllInspector')
    this.$store.dispatch('orgao/getAllOrgao')
  },
  computed: {
    pergunta: {
      get () {
        return this.$store.getters['pergunta/pergunta']
      },
      set (pergunta) {
        this.SET_UPDATE_PERGUNTA({ pergunta })
        this.$emit('update:pergunta', '')
        this.$store.commit('pergunta/SET_UPDATE_PERGUNTA', pergunta)
      }
    },
    allInspectors () {
      return this.$store.getters['inspector/allInspector']
    },
    allOrgaos () {
      return this.$store.getters['orgao/allOrgao']
    },
    getInspector () {
      const localInspector = this.allInspectors.filter(inspector => this.pergunta.inspector.id === inspector.id)
      if (localInspector.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localInspector[0] }
    }
  },
  components: {
    'create-edit-form': require('components/pergunta/createEditForm.vue').default,
    'list-suspeitos': require('pages/suspeito/index.vue').default
  },
  methods: {
    ...mapActions('pergunta', ['getAllPergunta', 'getPergunta', 'addNewPergunta', 'updatePergunta', 'deletePergunta']),
    ...mapMutations('pergunta', ['SET_UPDATE_PERGUNTA']),
    removePergunta (pergunta) {
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
          message: 'A informação foi Removida com successo! [ ' + pergunta.designacao + ' ]'
        })
        this.deletePergunta(pergunta)
        this.$router.go(-1)
      })
    },
    createPergunta () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localPergunta.inspector = this.inspector
      this.updatePergunta(this.localPergunta).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.pergunta.designacao + ' ]'
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
    editaPergunta (pergunta) {
      this.editedIndex = 0
      this.pergunta = Object.assign({}, pergunta)
      this.localPergunta = Object.assign({}, pergunta)
      this.inspector = this.allInspectors.filter(inspector => pergunta.inspector.id === inspector.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('pergunta/getPergunta', this.$route.params.id)
      }
      this.$store.dispatch('inspector/getAllInspector')
      this.$store.dispatch('orgao/getAllOrgao')
      this.show_dialog = false
      this.props = this.pergunta
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.pergunta.distrito = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Auto de Perguntas',
        basicInformation: 'Informacção do Auto de Perguntas',
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
        title: 'Detalhes do Auto de Perguntas',
        basicInformation: 'Informacção do Auto de Perguntas',
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
