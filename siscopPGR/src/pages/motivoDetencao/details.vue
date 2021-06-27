<template>
  <q-page>
    <div class="row q-col-gutter-sm q-ma-xs">
      <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
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
                    <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ motivoDetencao.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
          </q-card-section>
          <div class="row">
            <div class="col">
              <q-card-actions align="left">
                <q-btn v-go-back=" '/motivoDetencao' " class="glossy" color="primary" label="Voltar" no-caps/>
              </q-card-actions>
            </div>
            <div class="col">
              <q-card-actions align="right">
                <q-btn class="glossy" color="teal" label="Editar" no-caps @click="editaMotivoDetencao(motivoDetencao)"/>
                <q-btn class="glossy" color="negative" label="Apagar" no-caps
                       @click="removeMotivoDetencao(motivoDetencao)"/>
              </q-card-actions>
            </div>
          </div>
        </q-card>
      </div>
    </div>
    <create-edit-form :close="close"
                      :createMotivoDetencao="createMotivoDetencao"
                      :designacao.sync="localMotivoDetencao.designacao"
                      :listErrors="listErrors"
                      :removeMotivoDetencao="removeMotivoDetencao"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import MotivoDetencao from 'src/store/models/motivoDetencao/motivoDetencao'

export default {
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return MotivoDetencao.query().find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    motivoDetencao: {
      get() {
        return MotivoDetencao.query().find(this.$route.params.id)
      },
      set(motivoDetencao) {
        this.$emit('update:motivoDetencao', '')
        MotivoDetencao.update(motivoDetencao)
      }

    }
  },
  components: {
    'create-edit-form': require('components/motivoDetencao/createEditForm.vue').default
  },
  methods: {
    removeMotivoDetencao(motivoDetencao) {
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
          message: 'A informação foi Removida com successo! [ ' + motivoDetencao.designacao + ' ]'
        })
        MotivoDetencao.api().delete("/motivoDetencao/" + motivoDetencao.id)
        MotivoDetencao.delete(motivoDetencao.id)
        this.$router.go(-1)
      })
    },
    createMotivoDetencao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.motivoDetencao = this.localMotivoDetencao
      MotivoDetencao.api().patch("/motivoDetencao/" + this.localMotivoDetencao.id, this.localMotivoDetencao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.motivoDetencao.designacao + ' ]'
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
    editaMotivoDetencao(motivoDetencao) {
      this.localMotivoDetencao = Object.assign({}, motivoDetencao)
      this.motivoDetencao = Object.assign({}, motivoDetencao)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.motivoDetencao = {}
      this.props = this.motivoDetencao
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
      localMotivoDetencao: {
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Motivo de Detenção',
        basicInformation: 'Informacção do Motivo de Detenção',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes do Motivo de Detenção',
        basicInformation: 'Informacção do Motivo de Detenção',
        designacao: 'Designação'
      }
    }
  }
}
</script>
