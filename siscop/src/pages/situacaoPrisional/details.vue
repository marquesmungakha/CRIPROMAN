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
                      <q-item-label lines="1" caption >{{ $t('codigo') }}</q-item-label>
                      <q-item-label class="text-grey-9">{{ situacaoPrisional.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ situacaoPrisional.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click="editaSituacaoPrisional(situacaoPrisional)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click="removeSituacaoPrisional(situacaoPrisional)" no-caps/>
                </q-card-actions>
            </q-card>
        </div>
    </div>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localSituacaoPrisional.codigo"
                    :designacao.sync="localSituacaoPrisional.designacao"
                    :submitting="submitting"
                    :close="close"
                    :createSituacaoPrisional="createSituacaoPrisional"
                    :removeSituacaoPrisional="removeSituacaoPrisional"/>
  </q-page>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'

export default {
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('situacaoPrisional/getSituacaoPrisional', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    situacaoPrisional: {
      get () {
        return this.$store.getters['situacaoPrisional/situacaoPrisional']
      },
      set (situacaoPrisional) {
        this.SET_UPDATE_SITUACAOPRISIONAL({ situacaoPrisional })
        this.$emit('update:situacaoPrisional', '')
        this.$store.commit('situacaoPrisional/SET_UPDATE_SITUACAOPRISIONAL', situacaoPrisional)
      }

    }
  },
  components: {
    'create-edit-form': require('components/situacaoPrisional/createEditForm.vue').default
  },
  methods: {
    ...mapActions('situacaoPrisional', ['getAllSituacaoPrisional', 'getSituacaoPrisional', 'addNewSituacaoPrisional', 'updateSituacaoPrisional', 'deleteSituacaoPrisional']),
    ...mapMutations('situacaoPrisional', ['SET_UPDATE_SITUACAOPRISIONAL']),
    removeSituacaoPrisional (situacaoPrisional) {
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
          message: 'A informação foi Removida com successo! [ ' + situacaoPrisional.designacao + ' ]'
        })
        this.deleteSituacaoPrisional(situacaoPrisional)
        this.$router.go(-1)
      })
    },
    createSituacaoPrisional () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.situacaoPrisional = this.localSituacaoPrisional
      this.updateSituacaoPrisional(this.localSituacaoPrisional).then(resp => {
        console.log('response ' + resp)
        this.$q.notify({
          type: 'positive',
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 2000,
          position: 'bottom',
          classes: 'glossy',
          progress: true,
          message: 'A informação foi actualizada com successo!! [ ' + this.situacaoPrisional.designacao + ' ]'
        })
        this.close()
      }).catch(error => {
        console.log('error ' + error)
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
    editaSituacaoPrisional (situacaoPrisional) {
      this.localSituacaoPrisional = Object.assign({}, situacaoPrisional)
      this.situacaoPrisional = Object.assign({}, situacaoPrisional)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('situacaoPrisional/getSituacaoPrisional', this.$route.params.id)
      }
      this.show_dialog = false
      this.situacaoPrisional = {}
      this.props = this.situacaoPrisional
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  data () {
    return {
      listErrors: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      localSituacaoPrisional: {
        codigo: '',
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes da Situação Prisional',
        basicInformation: 'Informacção da  Situação Prisional',
        codigo: 'Código',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes da Situação Prisional',
        basicInformation: 'Informacção da  Situação Prisional',
        codigo: 'Código',
        designacao: 'Designação'
      }
    }
  }
}
</script>
