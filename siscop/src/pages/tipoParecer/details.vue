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
                      <q-item-label class="text-grey-9">{{ tipoParecer.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ tipoParecer.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click="editaTipoParecer(tipoParecer)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click="removeTipoParecer(tipoParecer)" no-caps/>
                </q-card-actions>
            </q-card>
        </div>
    </div>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localTipoParecer.codigo"
                    :designacao.sync="localTipoParecer.designacao"
                    :submitting="submitting"
                    :close="close"
                    :createTipoParecer="createTipoParecer"
                    :removeTipoParecer="removeTipoParecer"/>
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
    return store.dispatch('tipoParecer/getTipoParecer', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    tipoParecer: {
      get () {
        return this.$store.getters['tipoParecer/tipoParecer']
      },
      set (tipoParecer) {
        this.SET_UPDATE_TIPOPARECER({ tipoParecer })
        this.$emit('update:tipoParecer', '')
        this.$store.commit('tipoParecer/SET_UPDATE_TIPOPARECER', tipoParecer)
      }

    }
  },
  components: {
    'create-edit-form': require('components/tipoParecer/createEditForm.vue').default
  },
  methods: {
    ...mapActions('tipoParecer', ['getAllTipoParecer', 'getTipoParecer', 'addNewTipoParecer', 'updateTipoParecer', 'deleteTipoParecer']),
    ...mapMutations('tipoParecer', ['SET_UPDATE_TIPOPARECER']),
    removeTipoParecer (tipoParecer) {
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
          message: 'A informação foi Removida com successo! [ ' + tipoParecer.designacao + ' ]'
        })
        this.deleteTipoParecer(tipoParecer)
        this.$router.go(-1)
      })
    },
    createTipoParecer () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.tipoParecer = this.localTipoParecer
      this.updateTipoParecer(this.localTipoParecer).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.tipoParecer.designacao + ' ]'
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
    editaTipoParecer (tipoParecer) {
      this.localTipoParecer = Object.assign({}, tipoParecer)
      this.tipoParecer = Object.assign({}, tipoParecer)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('tipoParecer/getTipoParecer', this.$route.params.id)
      }
      this.show_dialog = false
      this.tipoParecer = {}
      this.props = this.tipoParecer
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
      localTipoParecer: {
        codigo: '',
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Tipo de Parecer do Processo',
        basicInformation: 'Informacção do Tipo de Parecer do Processo',
        codigo: 'Código',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes do Tipo de Parecer do Processo',
        basicInformation: 'Informacção do Tipo de Parecer do Processo',
        codigo: 'Código',
        designacao: 'Designação'
      }
    }
  }
}
</script>
