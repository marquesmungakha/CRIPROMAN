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
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ jurisdicao.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click="editaJurisdicao(jurisdicao)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click="removeJurisdicao(jurisdicao)" no-caps/>
                </q-card-actions>
            </q-card>
        </div>
        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
          <q-card>
            <q-tabs
              v-model="tab"
              class="bg-teal text-white shadow-2"
              active-color="white"
              indicator-color="white"
              align="left"
              narrow-indicator >
              <q-tab name="crimes" label="crimes" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="crimes">
                <div class="text-h6">{{ $t('crimes') }}</div>
                Component List Crimes
              </q-tab-panel>
            </q-tab-panels>
          </q-card>
        </div>
    </div>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :designacao.sync="localJurisdicao.designacao"
                    :submitting="submitting"
                    :close="close"
                    :createJurisdicao="createJurisdicao"
                    :removeJurisdicao="removeJurisdicao"/>
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
    return store.dispatch('jurisdicao/getJurisdicao', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    jurisdicao: {
      get () {
        return this.$store.getters['jurisdicao/jurisdicao']
      },
      set (jurisdicao) {
        this.SET_UPDATE_JURISDICAO({ jurisdicao })
        this.$emit('update:jurisdicao', '')
        this.$store.commit('jurisdicao/SET_UPDATE_JURISDICAO', jurisdicao)
      }

    }
  },
  components: {
    'create-edit-form': require('components/jurisdicao/createEditForm.vue').default
  },
  methods: {
    ...mapActions('jurisdicao', ['getAllJurisdicao', 'getJurisdicao', 'addNewJurisdicao', 'updateJurisdicao', 'deleteJurisdicao']),
    ...mapMutations('jurisdicao', ['SET_UPDATE_JURISDICAO']),
    removeJurisdicao (jurisdicao) {
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
          message: 'A informação foi Removida com successo! [ ' + jurisdicao.designacao + ' ]'
        })
        this.deleteJurisdicao(jurisdicao)
        this.$router.go(-1)
      })
    },
    createJurisdicao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.jurisdicao = this.localJurisdicao
      this.updateJurisdicao(this.localJurisdicao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.jurisdicao.designacao + ' ]'
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
    editaJurisdicao (jurisdicao) {
      this.localJurisdicao = Object.assign({}, jurisdicao)
      this.jurisdicao = Object.assign({}, jurisdicao)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('jurisdicao/getJurisdicao', this.$route.params.id)
      }
      this.show_dialog = false
      this.jurisdicao = {}
      this.props = this.jurisdicao
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
      tab: 'crimes',
      localJurisdicao: {
        designacao: ''
      }
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes da Família Delitiva',
        basicInformation: 'Informacção da Família Delitiva',
        designacao: 'Designação',
        crimes: 'Crimes'
      },
      en: {
        title: 'Detalhes da Família Delitiva',
        basicInformation: 'Informacção da Família Delitiva',
        designacao: 'Designação',
        crimes: 'Crimes'
      }
    }
  }
}
</script>
