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
                <div class="col-12">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ crime.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('jurisdicao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getJurisdicaoLocal.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaCrime(crime)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeCrime(crime)" no-caps/>
                </q-card-actions>
            </q-card>
        </div>
    </div>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :designacao.sync="localCrime.designacao"
                    :classeJudicial.sync="classeJudicial"
                    :jurisdicoes="allJurisdicoes"
                    :submitting="submitting"
                    :close="close"
                    :createCrime="createCrime"
                    :removeCrime="removeCrime"/>
  </q-page>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'

export default {
  name: 'Crime',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      localCrime: {
        designacao: '',
        classe_judicial: {}
      },
      classeJudicial: {
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
    // this.$store.dispatch('crime/getCrime', this.$route.params.id)
    // this.$store.dispatch('pais/getAllPais')
    // this.$store.dispatch('pais/getPais', this.crime.pais.id)
    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('crime/getCrime', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('jurisdicao/getAllJurisdicao')
  },
  computed: {
    crime: {
      get () {
        return this.$store.getters['crime/crime']
      },
      set (crime) {
        this.SET_UPDATE_CRIME({ crime })
        this.$emit('update:crime', '')
        this.$store.commit('crime/SET_UPDATE_CRIME', crime)
      }
    },
    allJurisdicoes () {
      return this.$store.getters['jurisdicao/allJurisdicao']
    },
    getJurisdicaoLocal () {
      const jurisdicaoLocal = this.allJurisdicoes.filter(jurisdicao => jurisdicao.id === this.crime.classe_judicial.id)
      if (jurisdicaoLocal.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return jurisdicaoLocal[0] }
    }
  },
  components: {
    'create-edit-form': require('components/crime/createEditForm.vue').default
  },
  methods: {
    ...mapActions('crime', ['getAllCrime', 'getCrime', 'addNewCrime', 'updateCrime', 'deleteCrime']),
    ...mapMutations('crime', ['SET_UPDATE_CRIME']),
    removeCrime (crime) {
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
          message: 'A informação foi Removida com successo! [ ' + crime.designacao + ' ]'
        })
        this.deleteCrime(crime)
        this.$router.go(-1)
      })
    },
    createCrime () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localCrime.classe_judicial.id = this.classeJudicial.id
      this.updateCrime(this.localCrime).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.crime.designacao + ' ]'
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
    editaCrime (crime) {
      this.editedIndex = 0
      this.crime = Object.assign({}, crime)
      this.localCrime = Object.assign({}, crime)
      this.classeJudicial = this.allJurisdicoes.filter(jurisdicao => jurisdicao.id === crime.classe_judicial.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('crime/getCrime', this.$route.params.id)
      }
      this.$store.dispatch('jurisdicao/getAllJurisdicao')
      this.show_dialog = false
      this.props = this.crime
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.classeJudicial = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes da Província',
        basicInformation: 'Informacção do Província',
        codigo: 'Código',
        designacao: 'Designação',
        jurisdicao: 'Jurisdição',
        distritos: 'Distritos',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Província',
        basicInformation: 'Informacção do Província',
        codigo: 'Código',
        designacao: 'Designação',
        jurisdicao: 'Jurisdição',
        distritos: 'Distritos',
        comments: 'Comentários'
      }
    }
  }
}
</script>
