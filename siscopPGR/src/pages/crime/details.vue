<template>
  <q-page class="q-ma-xs">
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
                    <q-item-label class="text-grey-9">{{ crime.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('Jurisdição/Família Delitiva') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ crime.classeJudicial.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
          </q-card-section>
          <div class="row">
            <div class="col">
              <q-card-actions align="left">
                <q-btn v-go-back=" '/crime' " class="glossy" color="primary" label="Voltar" no-caps/>
              </q-card-actions>
            </div>
            <div class="col">
              <q-card-actions align="right">
                <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaCrime(crime)"/>
                <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeCrime(crime)"/>
              </q-card-actions>
            </div>
          </div>
        </q-card>
    <create-edit-form :classeJudicial.sync="classeJudicial"
                      :close="close"
                      :createCrime="createCrime"
                      :designacao.sync="localCrime.designacao"
                      :jurisdicoes="allJurisdicoes"
                      :listErrors="listErrors"
                      :removeCrime="removeCrime"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import Crime from 'src/store/models/crime/crime'
import ClasseJudicial from 'src/store/models/jurisdicao/jurisdicao'

export default {
  name: 'Crime',
  data() {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      localCrime: {
        designacao: '',
        classe_judicial: {}
      },
      classeJudicial: {
        designacao: ''
      }
    }
  },
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // this.$store.dispatch('crime/getCrime', this.$route.params.id)
    // this.$store.dispatch('pais/getAllPais')
    // this.$store.dispatch('pais/getPais', this.crime.pais.id)
    // Return a Promise if you are running an async job
    // Example:
    return Crime.query().with('classeJudicial').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    crime: {
      get() {
        return Crime.query().with('classeJudicial').find(this.$route.params.id)
      },
      set(crime) {
        this.$emit('update:crime', '')
        Crime.update(crime)
      }
    }
  },
  components: {
    'create-edit-form': require('components/crime/createEditForm.vue').default
  },
  methods: {
    removeCrime(crime) {
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
          message: 'A informação foi Removida com successo! [ ' + crime.designacao + ' ]'
        })
        Crime.api().delete("/crime/" + crime.id)
        Crime.delete(crime.id)
        this.$router.go(-1)
      })
    },
    createCrime() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localCrime.classeJudicial_id = this.classeJudicial.id
      this.localCrime.classeJudicial = this.classeJudicial
      Crime.api().patch("/crime/" + this.localCrime.id, this.localCrime).then(resp => {
        console.log('update' + resp)
        this.$q.notify({
          type: 'positive',
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 2000,
          position: 'bottom',
          classes: 'glossy',
          progress: true,
          message: 'A informação foi actualizada com successo!! [ ' + this.localCrime.designacao + ' ]'
        })
        this.close()
      }).catch(error => {
        console.log('error' + error)
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
    editaCrime(crime) {
      this.editedIndex = 0
      this.crime = Object.assign({}, crime)
      this.localCrime = Object.assign({}, crime)
      this.classeJudicial = ClasseJudicial.query().find(this.localCrime.classeJudicial_id)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.props = this.crime
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
    this.classeJudicial = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Ações/Tipo Legal de Crime',
        basicInformation: 'Informacção do Ações/Tipo Legal de Crime',
        codigo: 'Código',
        designacao: 'Designação',
        jurisdicao: 'Jurisdição/Famílias Delitivas',
        distritos: 'Distritos',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Ações/Tipo Legal de Crime',
        basicInformation: 'Informacção do Ações/Tipo Legal de Crime',
        codigo: 'Código',
        designacao: 'Designação',
        jurisdicao: 'Jurisdição/Famílias Delitivas',
        distritos: 'Distritos',
        comments: 'Comentários'
      }
    }
  }
}
</script>
