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
                <q-item-label caption lines="1">{{ $t('codigo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ pais.codigo }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ pais.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('nacionalidade') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ pais.nacionalidade }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
          </div>
        </div>
      </q-card-section>
      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn v-go-back="'/pais'" class="glossy" color="primary" label="Voltar" no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar" no-caps @click="editaPais(pais)"/>
            <q-btn class="glossy" color="negative" label="Apagar" no-caps @click="removePais(pais)"/>
          </q-card-actions>
        </div>
      </div>
    </q-card>
     </div>
        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
          <q-card>
            <q-tabs
              v-model="tab1"
              active-color="white"
              align="center"
              class="bg-teal text-white shadow-2"
              indicator-color="white"
              narrow-indicator>
              <q-tab label="provincias" name="provincias"/>
            </q-tabs>

            <q-separator/>
            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="provincias">
               <list-provincia :paisLocal.sync="pais"/>
              </q-tab-panel>
            </q-tab-panels>
          </q-card>
      </div>
      <create-edit-form :close="close"
                      :codigo.sync="localPais.codigo"
                      :createPais="createPais"
                      :designacao.sync="localPais.designacao"
                      :listErrors="listErrors"
                      :nacionalidade.sync="localPais.nacionalidade"
                      :removePais="removePais"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </div>
  </q-page>
</template>

<script>
import {QSpinnerBall} from "quasar";
import Pais from "src/store/models/pais/pais";

export default {
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return Pais.find(currentRoute.params.id) // store.dispatch('pais/getPais', currentRoute.params.id)
  },
  created() {
    this.$q.loading.show({
      message: "Caregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })

    setTimeout(() => {
      this.$q.loading.hide()
    }, 600)
  },
  mounted() {
  },
  computed: {
    pais: {
      get() {
        return Pais.find(this.$route.params.id)
      },
      set(pais) {
        this.$emit('update:pais', '')
        Pais.update(pais) // this.$store.commit('pais/SET_UPDATE_PAIS', pais)
      }

    }
  },
  components: {
    'create-edit-form': require('components/pais/createEditForm.vue').default,
    'list-provincia': require('pages/provincia/index.vue').default
  },
  methods: {
    removePais(pais) {
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
          message: 'A informação foi Removida com successo! [ ' + pais.designacao + ' ]'
        })
        Pais.api().delete("/pais/" + pais.id)
          Pais.delete(pais.id)
        this.$router.go(-1)
      })
    },
    createPais() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.pais = this.localPais
      Pais.api().patch("/pais/" + this.pais.id, this.localPais).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.pais.designacao + ' ]'
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
    editaPais(pais) {
      this.localPais = Object.assign({}, pais)
      this.pais = Object.assign({}, pais)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.pais = {}
      this.listErrors = []
      this.props = this.pais
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
      tab: 'provincias',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localPais: {
        codigo: '',
        designacao: '',
        nacionalidade: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do País',
        basicInformation: 'Informacção do País',
        codigo: 'Código',
        designacao: 'Designação',
        nacionalidade: 'Nacionalidade',
        provincias: 'Províncias',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do País',
        basicInformation: 'Informacção do País',
        codigo: 'Código',
        designacao: 'Designação',
        nacionalidade: 'Nacionalidade',
        provincias: 'Províncias',
        comments: 'Comentários'
      }
    }
  }
}
</script>
