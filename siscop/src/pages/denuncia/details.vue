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
                    <q-item-label class="text-grey-9">{{ denuncia.numero }}</q-item-label>
                  </q-item-section>
                </q-item>
                </div>
                 <div class="col">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('dataAbertura') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ denuncia.dataAbertura }}</q-item-label>
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
                        <q-item-label class="text-grey-9">{{ denuncia.descricao }}</q-item-label>
                      </q-item-section>
                    </q-item>
                 </div>
                  <div class="col">
                    <q-item class="full-width">
                      <q-item-section>
                        <q-item-label lines="1" caption >{{ $t('procedimentoCriminal') }}</q-item-label>
                        <q-item-label class="text-grey-9">{{ denuncia.procedimentoCriminal }}</q-item-label>
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
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaDenuncia(denuncia)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeDenuncia(denuncia)" no-caps/>
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
                <q-tab name="testeminhas" label="Testemunha" />
              </q-tabs>

              <q-separator />

              <q-tab-panels v-model="tab" animated>
                <q-tab-panel name="denunciantes">
                  <list-denunciante :autoEntrada.sync="denuncia"/>
                </q-tab-panel>
                <q-tab-panel name="testeminhas">
                 <list-testemunhas :autoEntrada.sync="denuncia"/>
                </q-tab-panel>
              </q-tab-panels>
          </q-card>

    </div>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :numero.sync="localDenuncia.numero"
                    :dataAbertura.sync="localDenuncia.dataAbertura"
                    :descricao.sync="localDenuncia.descricao"
                    :procedimentoCriminal.sync="localDenuncia.procedimentoCriminal"
                    :inspector.sync="inspector"
                    :anexo.sync="localDenuncia.anexo"
                    :inspectors.sync="allInspectors"
                    :submitting="submitting"
                    :close="close"
                    :createDenuncia="createDenuncia"
                    :removeDenuncia="removeDenuncia"/>
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
      localDenuncia: {
        numero: '',
        dataAbertura: '',
        descricao: '',
        procedimentoCriminal: '',
        anexo: [],
        processo: {},
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
    return store.dispatch('denuncia/getDenuncia', currentRoute.params.id)
  },
  props: ['processo'],
  created () {
  },
  mounted () {
    this.$store.dispatch('inspector/getAllInspector')
    this.$store.dispatch('orgao/getAllOrgao')
  },
  computed: {
    denuncia: {
      get () {
        return this.$store.getters['denuncia/denuncia']
      },
      set (denuncia) {
        this.SET_UPDATE_DENUNCIA({ denuncia })
        this.$emit('update:denuncia', '')
        this.$store.commit('denuncia/SET_UPDATE_DENUNCIA', denuncia)
      }
    },
    allInspectors () {
      return this.$store.getters['inspector/allInspector']
    },
    allOrgaos () {
      return this.$store.getters['orgao/allOrgao']
    },
    getInspector () {
      const localInspector = this.allInspectors.filter(inspector => this.denuncia.inspector.id === inspector.id)
      if (localInspector.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localInspector[0] }
    }
  },
  components: {
    'create-edit-form': require('components/denuncia/createEditForm.vue').default,
    'list-denunciante': require('pages/denunciante/index.vue').default,
    'list-testemunhas': require('pages/testemunha/index.vue').default
  },
  methods: {
    ...mapActions('denuncia', ['getAllDenuncia', 'getDenuncia', 'addNewDenuncia', 'updateDenuncia', 'deleteDenuncia']),
    ...mapMutations('denuncia', ['SET_UPDATE_DENUNCIA']),
    removeDenuncia (denuncia) {
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
          message: 'A informação foi Removida com successo! [ ' + denuncia.designacao + ' ]'
        })
        this.deleteDenuncia(denuncia)
        this.$router.go(-1)
      })
    },
    createDenuncia () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localDenuncia.inspector = this.inspector
      this.updateDenuncia(this.localDenuncia).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.denuncia.designacao + ' ]'
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
    editaDenuncia (denuncia) {
      this.editedIndex = 0
      this.denuncia = Object.assign({}, denuncia)
      this.localDenuncia = Object.assign({}, denuncia)
      this.inspector = this.allInspectors.filter(inspector => denuncia.inspector.id === inspector.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('denuncia/getDenuncia', this.$route.params.id)
      }
      this.$store.dispatch('inspector/getAllInspector')
      this.$store.dispatch('orgao/getAllOrgao')
      this.show_dialog = false
      this.props = this.denuncia
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.denuncia.distrito = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Auto de Denúncia',
        basicInformation: 'Informacção do Auto de Denúncia',
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
        title: 'Detalhes do Auto de Denúncia',
        basicInformation: 'Informacção do Auto de Denúncia',
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
