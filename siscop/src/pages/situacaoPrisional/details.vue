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
                    <q-item-label class="text-grey-9">{{ situacaoPrisional.codigo }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ situacaoPrisional.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('activo') }}</q-item-label>
                    <q-item-label class="text-grey-9">
                      <span v-if="situacaoPrisional.activo == true">Activo</span>
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
                <q-btn v-go-back=" '/situacaoPrisional' " class="glossy" color="primary" label="Voltar" no-caps/>
              </q-card-actions>
            </div>
            <div class="col">
              <q-card-actions align="right">
                <q-btn class="glossy" color="teal" label="Editar" no-caps
                       @click="editaSituacaoPrisional(situacaoPrisional)"/>
                <q-btn class="glossy" color="negative" label="Apagar"
                       no-caps @click="removeSituacaoPrisional(situacaoPrisional)"/>
              </q-card-actions>
            </div>
          </div>
        </q-card>
    <create-edit-form :close="close"
                      :codigo.sync="localSituacaoPrisional.codigo"
                      :createSituacaoPrisional="createSituacaoPrisional"
                      :designacao.sync="localSituacaoPrisional.designacao"
                      :activo.sync="localSituacaoPrisional.activo"
                      :listErrors="listErrors"
                      :removeSituacaoPrisional="removeSituacaoPrisional"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
    </div>
  </q-page>
</template>

<script>
import SituacaoPrisional from 'src/store/models/situacaoPrisional/situacaoPrisional'

export default {
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return SituacaoPrisional.query().find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    situacaoPrisional: {
      get() {
        return SituacaoPrisional.query().find(this.$route.params.id)
      },
      set(situacaoPrisional) {
        this.$emit('update:situacaoPrisional', '')
        SituacaoPrisional.update(situacaoPrisional)
      }

    }
  },
  components: {
    'create-edit-form': require('components/situacaoPrisional/createEditForm.vue').default
  },
  methods: {
    removeSituacaoPrisional(situacaoPrisional) {
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
        SituacaoPrisional.api().delete("/situacaoPrisional/" + situacaoPrisional.id)
        SituacaoPrisional.delete(situacaoPrisional.id)
        this.$router.go(-1)
      })
    },
    createSituacaoPrisional() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)
      this.situacaoPrisional = this.localSituacaoPrisional
      SituacaoPrisional.api().patch("/situacaoPrisional/" + this.localSituacaoPrisional.id, this.localSituacaoPrisional).then(resp => {
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
    editaSituacaoPrisional(situacaoPrisional) {
      this.localSituacaoPrisional = Object.assign({}, situacaoPrisional)
      this.situacaoPrisional = Object.assign({}, situacaoPrisional)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.situacaoPrisional = {}
      this.props = this.situacaoPrisional
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
