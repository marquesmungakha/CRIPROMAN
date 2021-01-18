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
                      <q-item-label class="text-grey-9">{{ tipoDocumentoIdentificacao.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ tipoDocumentoIdentificacao.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click="editaTipoDocumentoIdentificacao(tipoDocumentoIdentificacao)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click="removeTipoDocumentoIdentificacao(tipoDocumentoIdentificacao)" no-caps/>
                </q-card-actions>
            </q-card>
        </div>
    </div>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localTipoDocumentoIdentificacao.codigo"
                    :designacao.sync="localTipoDocumentoIdentificacao.designacao"
                    :submitting="submitting"
                    :close="close"
                    :createTipoDocumentoIdentificacao="createTipoDocumentoIdentificacao"
                    :removeTipoDocumentoIdentificacao="removeTipoDocumentoIdentificacao"/>
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
    return store.dispatch('tipoDocumentoIdentificacao/getTipoDocumentoIdentificacao', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    tipoDocumentoIdentificacao: {
      get () {
        return this.$store.getters['tipoDocumentoIdentificacao/tipoDocumentoIdentificacao']
      },
      set (tipoDocumentoIdentificacao) {
        this.SET_UPDATE_TIPODOCUMENTOIDENTIFICACAO({ tipoDocumentoIdentificacao })
        this.$emit('update:tipoDocumentoIdentificacao', '')
        this.$store.commit('tipoDocumentoIdentificacao/SET_UPDATE_TIPODOCUMENTOIDENTIFICACAO', tipoDocumentoIdentificacao)
      }

    }
  },
  components: {
    'create-edit-form': require('components/tipoDocumentoIdentificacao/createEditForm.vue').default
  },
  methods: {
    ...mapActions('tipoDocumentoIdentificacao', ['getAllTipoDocumentoIdentificacao', 'getTipoDocumentoIdentificacao', 'addNewTipoDocumentoIdentificacao', 'updateTipoDocumentoIdentificacao', 'deleteTipoDocumentoIdentificacao']),
    ...mapMutations('tipoDocumentoIdentificacao', ['SET_UPDATE_TIPODOCUMENTOIDENTIFICACAO']),
    removeTipoDocumentoIdentificacao (tipoDocumentoIdentificacao) {
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
          message: 'A informação foi Removida com successo! [ ' + tipoDocumentoIdentificacao.designacao + ' ]'
        })
        this.deleteTipoDocumentoIdentificacao(tipoDocumentoIdentificacao)
        this.$router.go(-1)
      })
    },
    createTipoDocumentoIdentificacao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.tipoDocumentoIdentificacao = this.localTipoDocumentoIdentificacao
      this.updateTipoDocumentoIdentificacao(this.localTipoDocumentoIdentificacao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.tipoDocumentoIdentificacao.designacao + ' ]'
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
    editaTipoDocumentoIdentificacao (tipoDocumentoIdentificacao) {
      this.localTipoDocumentoIdentificacao = Object.assign({}, tipoDocumentoIdentificacao)
      this.tipoDocumentoIdentificacao = Object.assign({}, tipoDocumentoIdentificacao)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('tipoDocumentoIdentificacao/getTipoDocumentoIdentificacao', this.$route.params.id)
      }
      this.show_dialog = false
      this.tipoDocumentoIdentificacao = {}
      this.props = this.tipoDocumentoIdentificacao
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
      localTipoDocumentoIdentificacao: {
        codigo: '',
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Tipo de Documento de Identificação',
        basicInformation: 'Informacção do Tipo de Documento de Identificação',
        codigo: 'Código',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes do Tipo de Documento de Identificação',
        basicInformation: 'Informacção do Tipo de Documento de Identificação',
        codigo: 'Código',
        designacao: 'Designação'
      }
    }
  }
}
</script>
