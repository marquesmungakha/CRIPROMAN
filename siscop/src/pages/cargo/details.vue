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
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ cargo.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
          </q-card-section>
          <div class="row">
            <div class="col">
              <q-card-actions align="left">
                <q-btn v-go-back=" '/cargo' " class="glossy" color="primary" label="Voltar" no-caps/>
              </q-card-actions>
            </div>
            <div class="col">
              <q-card-actions align="right">
                <q-btn class="glossy" color="teal" label="Editar" no-caps @click="editaCargo(cargo)"/>
                <q-btn class="glossy" color="negative" label="Apagar" no-caps @click="removeCargo(cargo)"/>
              </q-card-actions>
            </div>
          </div>
        </q-card>
      </div>
    </div>
    <create-edit-form :close="close"
                      :createCargo="createCargo"
                      :designacao.sync="localCargo.designacao"
                      :listErrors="listErrors"
                      :removeCargo="removeCargo"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import Cargo from 'src/store/models/cargo/cargo'

export default {
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return Cargo.query().find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    cargo: {
      get() {
        return Cargo.query().find(this.$route.params.id)
      },
      set(cargo) {
        this.$emit('update:cargo', '')
        Cargo.update(cargo)
      }

    }
  },
  components: {
    'create-edit-form': require('components/cargo/createEditForm.vue').default
  },
  methods: {
    removeCargo(cargo) {
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
          message: 'A informação foi Removida com successo! [ ' + cargo.designacao + ' ]'
        })
        Cargo.api().delete("/cargo/" + cargo.id)
        this.$router.go(-1)
      })
    },
    createCargo() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.cargo = this.localCargo
      Cargo.api().patch("/cargo/" + this.cargo.id, this.cargo).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.cargo.designacao + ' ]'
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
    editaCargo(cargo) {
      this.localCargo = Object.assign({}, cargo)
      this.cargo = Object.assign({}, cargo)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.cargo = {}
      this.props = this.cargo
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
      localCargo: {
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Cargo',
        basicInformation: 'Informacção do Cargo',
        codigo: 'Código',
        designacao: 'Designação'
      },
      en: {
        title: 'Detalhes do Cargo',
        basicInformation: 'Informacção do Cargo',
        codigo: 'Código',
        designacao: 'Designação'
      }
    }
  }
}
</script>
