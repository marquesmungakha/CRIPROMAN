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
                      <q-item-label class="text-grey-9">{{ tipoOrgao.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ tipoOrgao.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click="editaTipoOrgao(tipoOrgao)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click="removeTipoOrgao(tipoOrgao)" no-caps/>
                </q-card-actions>
            </q-card>
        </div>
    </div>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localTipoOrgao.codigo"
                    :designacao.sync="localTipoOrgao.designacao"
                    :submitting="submitting"
                    :close="close"
                    :createTipoOrgao="createTipoOrgao"
                    :removeTipoOrgao="removeTipoOrgao"/>
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
    return store.dispatch('tipoOrgao/getTipoOrgao', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    tipoOrgao: {
      get () {
        return this.$store.getters['tipoOrgao/tipoOrgao']
      },
      set (tipoOrgao) {
        this.SET_UPDATE_TIPOORGAO({ tipoOrgao })
        this.$emit('update:tipoOrgao', '')
        this.$store.commit('tipoOrgao/SET_UPDATE_TIPOORGAO', tipoOrgao)
      }

    }
  },
  components: {
    'create-edit-form': require('components/tipoOrgao/createEditForm.vue').default
  },
  methods: {
    ...mapActions('tipoOrgao', ['getAllTipoOrgao', 'getTipoOrgao', 'addNewTipoOrgao', 'updateTipoOrgao', 'deleteTipoOrgao']),
    ...mapMutations('tipoOrgao', ['SET_UPDATE_TIPOORGAO']),
    removeTipoOrgao (tipoOrgao) {
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
          message: 'A informação foi Removida com successo! [ ' + tipoOrgao.designacao + ' ]'
        })
        this.deleteTipoOrgao(tipoOrgao)
        this.$router.go(-1)
      })
    },
    createTipoOrgao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.tipoOrgao = this.localTipoOrgao
      this.updateTipoOrgao(this.localTipoOrgao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.tipoOrgao.designacao + ' ]'
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
    editaTipoOrgao (tipoOrgao) {
      this.localTipoOrgao = Object.assign({}, tipoOrgao)
      this.tipoOrgao = Object.assign({}, tipoOrgao)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('tipoOrgao/getTipoOrgao', this.$route.params.id)
      }
      this.show_dialog = false
      this.tipoOrgao = {}
      this.props = this.tipoOrgao
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
      localTipoOrgao: {
        codigo: '',
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Tipo de Orgão',
        basicInformation: 'Informacção do Tipo de Orgão',
        codigo: 'Código',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes do Tipo de Orgão',
        basicInformation: 'Informacção do Tipo de Orgão',
        codigo: 'Código',
        designacao: 'Designação'
      }
    }
  }
}
</script>
