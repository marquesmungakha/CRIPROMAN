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
                      <q-item-label class="text-grey-9">{{ formaProcesso.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ formaProcesso.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click="editaFormaProcesso(formaProcesso)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click="removeFormaProcesso(formaProcesso)" no-caps/>
                </q-card-actions>
            </q-card>
        </div>
    </div>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localFormaProcesso.codigo"
                    :designacao.sync="localFormaProcesso.designacao"
                    :submitting="submitting"
                    :close="close"
                    :createFormaProcesso="createFormaProcesso"
                    :removeFormaProcesso="removeFormaProcesso"/>
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
    return store.dispatch('formaProcesso/getFormaProcesso', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    formaProcesso: {
      get () {
        return this.$store.getters['formaProcesso/formaProcesso']
      },
      set (formaProcesso) {
        this.SET_UPDATE_FORMAPROCESSO({ formaProcesso })
        this.$emit('update:formaProcesso', '')
        this.$store.commit('formaProcesso/SET_UPDATE_FORMAPROCESSO', formaProcesso)
      }

    }
  },
  components: {
    'create-edit-form': require('components/formaProcesso/createEditForm.vue').default
  },
  methods: {
    ...mapActions('formaProcesso', ['getAllFormaProcesso', 'getFormaProcesso', 'addNewFormaProcesso', 'updateFormaProcesso', 'deleteFormaProcesso']),
    ...mapMutations('formaProcesso', ['SET_UPDATE_FORMAPROCESSO']),
    removeFormaProcesso (formaProcesso) {
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
          message: 'A informação foi Removida com successo! [ ' + formaProcesso.designacao + ' ]'
        })
        this.deleteFormaProcesso(formaProcesso)
        this.$router.go(-1)
      })
    },
    createFormaProcesso () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.formaProcesso = this.localFormaProcesso
      this.updateFormaProcesso(this.localFormaProcesso).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.formaProcesso.designacao + ' ]'
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
    editaFormaProcesso (formaProcesso) {
      this.localFormaProcesso = Object.assign({}, formaProcesso)
      this.formaProcesso = Object.assign({}, formaProcesso)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('formaProcesso/getFormaProcesso', this.$route.params.id)
      }
      this.show_dialog = false
      this.formaProcesso = {}
      this.props = this.formaProcesso
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
      localFormaProcesso: {
        codigo: '',
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes da Forma de Processo',
        basicInformation: 'Informacção da Forma de Processo',
        codigo: 'Código',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes da Forma de Processo',
        basicInformation: 'Informacção da Forma de Processo',
        codigo: 'Código',
        designacao: 'Designação'
      }
    }
  }
}
</script>
