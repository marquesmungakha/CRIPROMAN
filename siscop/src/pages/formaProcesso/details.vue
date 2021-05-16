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
                    <q-item-label class="text-grey-9">{{ formaProcesso.codigo }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ formaProcesso.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                 <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('activo') }}</q-item-label>
                    <q-item-label class="text-grey-9">
                      <span v-if="formaProcesso.activo == true">Activo</span>
                      <span v-else>Desactivo</span>
                    </q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
          </q-card-section>
          <div class="row">
            <div class="col">
              <q-card-actions align="left">
                <q-btn v-go-back=" '/formaProcesso' " class="glossy" color="primary" label="Voltar" no-caps/>
              </q-card-actions>
            </div>
            <div class="col">
              <q-card-actions align="right">
                <q-btn class="glossy" color="teal" label="Editar" no-caps @click="editaFormaProcesso(formaProcesso)"/>
                <q-btn class="glossy" color="negative" label="Apagar" no-caps
                       @click="removeFormaProcesso(formaProcesso)"/>
              </q-card-actions>
            </div>
          </div>
        </q-card>
    <create-edit-form :close="close"
                      :codigo.sync="localFormaProcesso.codigo"
                      :createFormaProcesso="createFormaProcesso"
                      :designacao.sync="localFormaProcesso.designacao"
                      :listErrors="listErrors"
                      :removeFormaProcesso="removeFormaProcesso"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
    </div>
  </q-page>
</template>

<script>
import FormaProcesso from 'src/store/models/formaProcesso/formaProcesso'

export default {
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return FormaProcesso.query().find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    formaProcesso: {
      get() {
        return FormaProcesso.query().find(this.$route.params.id)
      },
      set(formaProcesso) {
        this.$emit('update:formaProcesso', '')
        FormaProcesso.update(formaProcesso)
      }

    }
  },
  components: {
    'create-edit-form': require('components/formaProcesso/createEditForm.vue').default
  },
  methods: {
    removeFormaProcesso(formaProcesso) {
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
        FormaProcesso.api().delete("/formaProcesso/" + formaProcesso.id)
        this.$router.go(-1)
      })
    },
    createFormaProcesso() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.formaProcesso = this.localFormaProcesso
      FormaProcesso.api().patch("/formaProcesso/" + this.localFormaProcesso.id, this.localFormaProcesso).then(resp => {
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
    editaFormaProcesso(formaProcesso) {
      this.localFormaProcesso = Object.assign({}, formaProcesso)
      this.formaProcesso = Object.assign({}, formaProcesso)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.formaProcesso = {}
      this.props = this.formaProcesso
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
