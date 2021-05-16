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
                    <q-item-label class="text-grey-9">{{ jurisdicao.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
          </q-card-section>
          <div class="row">
            <div class="col">
              <q-card-actions align="left">
                <q-btn v-go-back=" '/jurisdicao' " class="glossy" color="primary" label="Voltar" no-caps/>
              </q-card-actions>
            </div>
            <div class="col">
              <q-card-actions align="right">
                <q-btn class="glossy" color="teal" label="Editar" no-caps @click="editaJurisdicao(jurisdicao)"/>
                <q-btn class="glossy" color="negative" label="Apagar" no-caps @click="removeJurisdicao(jurisdicao)"/>
              </q-card-actions>
            </div>
          </div>
        </q-card>
      </div>
      <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
        <q-card>
          <q-tabs
            v-model="tab"
            active-color="white"
            align="left"
            class="bg-teal text-white shadow-2"
            indicator-color="white"
            narrow-indicator>
            <q-tab label="crimes" name="crimes"/>
          </q-tabs>

          <q-separator/>

          <q-tab-panels v-model="tab" animated>
            <q-tab-panel name="crimes">
              <div class="text-h6">{{ $t('crimes') }}</div>
              Component List Crimes
            </q-tab-panel>
          </q-tab-panels>
        </q-card>
      </div>
    </div>
    <create-edit-form :close="close"
                      :createJurisdicao="createJurisdicao"
                      :designacao.sync="localJurisdicao.designacao"
                      :listErrors="listErrors"
                      :removeJurisdicao="removeJurisdicao"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'

export default {
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return ClasseJudicial.query().find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    jurisdicao: {
      get() {
        return ClasseJudicial.query().find(this.$route.params.id)
      },
      set(jurisdicao) {
        this.$emit('update:jurisdicao', '')
        ClasseJudicial.update(jurisdicao)
      }

    }
  },
  components: {
    'create-edit-form': require('components/jurisdicao/createEditForm.vue').default
  },
  methods: {
    removeJurisdicao(jurisdicao) {
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
        ClasseJudicial.api().delete("/classeJudicial/" + jurisdicao.id)
        this.$router.go(-1)
      })
    },
    createJurisdicao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.jurisdicao = this.localJurisdicao
      ClasseJudicial.api().patch("/classeJudicial/" + this.localJurisdicao.id, this.localJurisdicao).then(resp => {
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
    editaJurisdicao(jurisdicao) {
      this.localJurisdicao = Object.assign({}, jurisdicao)
      this.jurisdicao = Object.assign({}, jurisdicao)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.jurisdicao = {}
      this.props = this.jurisdicao
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
