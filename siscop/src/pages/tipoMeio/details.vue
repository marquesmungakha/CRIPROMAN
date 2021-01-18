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
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ tipoMeio.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click="editaTipoMeio(tipoMeio)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click="removeTipoMeio(tipoMeio)" no-caps/>
                </q-card-actions>
            </q-card>
        </div>
    </div>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :designacao.sync="localTipoMeio.designacao"
                    :submitting="submitting"
                    :close="close"
                    :createTipoMeio="createTipoMeio"
                    :removeTipoMeio="removeTipoMeio"/>
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
    return store.dispatch('tipoMeio/getTipoMeio', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    tipoMeio: {
      get () {
        return this.$store.getters['tipoMeio/tipoMeio']
      },
      set (tipoMeio) {
        this.SET_UPDATE_TIPOMEIO({ tipoMeio })
        this.$emit('update:tipoMeio', '')
        this.$store.commit('tipoMeio/SET_UPDATE_TIPOMEIO', tipoMeio)
      }

    }
  },
  components: {
    'create-edit-form': require('components/tipoMeio/createEditForm.vue').default
  },
  methods: {
    ...mapActions('tipoMeio', ['getAllTipoMeio', 'getTipoMeio', 'addNewTipoMeio', 'updateTipoMeio', 'deleteTipoMeio']),
    ...mapMutations('tipoMeio', ['SET_UPDATE_TIPOMEIO']),
    removeTipoMeio (tipoMeio) {
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
          message: 'A informação foi Removida com successo! [ ' + tipoMeio.designacao + ' ]'
        })
        this.deleteTipoMeio(tipoMeio)
        this.$router.go(-1)
      })
    },
    createTipoMeio () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.tipoMeio = this.localTipoMeio
      this.updateTipoMeio(this.localTipoMeio).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.tipoMeio.designacao + ' ]'
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
    editaTipoMeio (tipoMeio) {
      this.localTipoMeio = Object.assign({}, tipoMeio)
      this.tipoMeio = Object.assign({}, tipoMeio)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('tipoMeio/getTipoMeio', this.$route.params.id)
      }
      this.show_dialog = false
      this.tipoMeio = {}
      this.props = this.tipoMeio
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
      localTipoMeio: {
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Tipo de Meio Usado',
        basicInformation: 'Informacção do Tipo de Meio Usado',
        codigo: 'Código',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes do TipoMeio',
        basicInformation: 'Informacção do Tipo de Meio Usado',
        codigo: 'Código',
        designacao: 'Designação'
      }
    }
  }
}
</script>
