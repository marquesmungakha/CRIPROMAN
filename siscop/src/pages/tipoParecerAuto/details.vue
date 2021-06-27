<template>
  <q-page>
    <div class="q-ma-xs">
        <q-card bordered class="my-card" flat>
          <q-card-section class="bg-secondary text-white">
            <div class="text-h6">{{ $t('basicInformation') }}</div>
          </q-card-section>
          <q-separator/>
          <q-card-section class="bg-white text-grey">
            <div class="row">
              <div class="col-12">
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('codigo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ tipoParecerAuto.codigo }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ tipoParecerAuto.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
          </q-card-section>
          <div class="row">
            <div class="col">
              <q-card-actions align="left">
                <q-btn v-go-back=" '/tipoParecerAuto' " class="glossy" color="primary" label="Voltar" no-caps/>
              </q-card-actions>
            </div>
            <div class="col">
              <q-card-actions align="right">
                <q-btn class="glossy" color="teal" label="Editar" no-caps @click="editaTipoParecerAuto(tipoParecerAuto)"/>
                <q-btn class="glossy" color="negative" label="Apagar" no-caps @click="removeTipoParecerAuto(tipoParecerAuto)"/>
              </q-card-actions>
            </div>
          </div>
        </q-card>
    <create-edit-form :close="close"
                      :codigo.sync="localTipoParecerAuto.codigo"
                      :createTipoParecer="createTipoParecerAuto"
                      :designacao.sync="localTipoParecerAuto.designacao"
                      :listErrors="listErrors"
                      :removeTipoParecer="removeTipoParecerAuto"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
      </div>

  </q-page>
</template>

<script>
import TipoParecerAuto from 'src/store/models/tipoParecerAuto/tipoParecerAuto'

export default {
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return TipoParecerAuto.query().find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    tipoParecerAuto: {
      get() {
        return TipoParecerAuto.query().find(this.$route.params.id)
      },
      set(tipoParecerAuto) {
        this.$emit('update:tipoParecerAuto', '')
        TipoParecerAuto.update(tipoParecerAuto)
      }

    }
  },
  components: {
    'create-edit-form': require('components/tipoParecer/createEditForm.vue').default
  },
  methods: {
    removeTipoParecerAuto(tipoParecerAuto) {
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
          message: 'A informação foi Removida com successo! [ ' + tipoParecerAuto.designacao + ' ]'
        })
        TipoParecerAuto.api().delete("/tipoParecerAuto/" + tipoParecerAuto.id)
        TipoParecerAuto.delete(tipoParecerAuto.id)
        this.$router.go(-1)
      })
    },
    createTipoParecerAuto() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)
      this.tipoParecerAuto = this.localTipoParecerAuto
      TipoParecerAuto.api().patch("/tipoParecerAuto/" + this.localTipoParecerAuto.id, this.localTipoParecerAuto).then(resp => {
        this.submitting = false
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
          message: 'A informação foi actualizada com successo!! [ ' + this.tipoParecerAuto.designacao + ' ]'
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
    editaTipoParecerAuto(tipoParecerAuto) {
      this.localTipoParecerAuto = Object.assign({}, tipoParecerAuto)
      this.tipoParecerAuto = Object.assign({}, tipoParecerAuto)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.tipoParecerAuto = {}
      this.props = this.tipoParecerAuto
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  data() {
    return {
      listErrors: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      localTipoParecerAuto: {
        codigo: '',
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes de Estágio do Auto',
        basicInformation: 'Informacção de Estágio do Auto',
        codigo: 'Código',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes de Estágio do Auto',
        basicInformation: 'Informacção de Estágio do Auto',
        codigo: 'Código',
        designacao: 'Designação'
      }
    }
  }
}
</script>
