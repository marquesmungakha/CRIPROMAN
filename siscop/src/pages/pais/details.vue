<template>
  <q-page>
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
                      <q-item-label class="text-grey-9">{{ pais.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ pais.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('nacionalidade') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ pais.nacionalidade }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click="editaPais(pais)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click="removePais(pais)" no-caps/>
                </q-card-actions>
            </q-card>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localPais.codigo"
                    :designacao.sync="localPais.designacao"
                    :nacionalidade.sync="localPais.nacionalidade"
                    :submitting="submitting"
                    :close="close"
                    :createPais="createPais"
                    :removePais="removePais"/>
  </q-page>
</template>

<script>
import {QSpinnerBall} from "quasar";
import Pais from "src/store/models/pais/pais";

export default {
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return Pais.find(currentRoute.params.id) // store.dispatch('pais/getPais', currentRoute.params.id)
  },
  created () {
    this.$q.loading.show({
      message: "Loading",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })

    setTimeout( () => {
      this.$q.loading.hide()
    },600)
  },
  mounted () {
  },
  computed: {
    pais: {
      get () {
        return Pais.find(this.$route.params.id)
      },
      set (pais) {
        this.$emit('update:pais', '')
        Pais.update(pais) // this.$store.commit('pais/SET_UPDATE_PAIS', pais)
      }

    }
  },
  components: {
    'create-edit-form': require('components/pais/createEditForm.vue').default
  },
  methods: {
    removePais (pais) {
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
        Pais.api().delete("/pais/"+pais.id)
        this.$router.go(-1)
      })
    },
    createPais () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.pais = this.localPais
      Pais.api().patch("/pais/"+this.pais.id,this.localPais).then(resp => {
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
    editaPais (pais) {
      this.localPais = Object.assign({}, pais)
      this.pais = Object.assign({}, pais)
      this.show_dialog = true
    },
    close () {
      this.show_dialog = false
      this.pais = {}
      this.listErrors = []
      this.props = this.pais
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
