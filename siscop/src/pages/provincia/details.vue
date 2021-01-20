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
                <q-item-label lines="1" caption>{{ $t('codigo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ provincia.codigo }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label lines="1" caption>{{ $t('designacao') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ provincia.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label lines="1" caption>{{ $t('pais') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ provincia.pais.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
          </div>
        </div>
      </q-card-section>

      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn class="glossy" label="Voltar" color="primary" v-go-back=" '/provincia' " no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaProvincia(provincia)" no-caps/>
            <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeProvincia(provincia)" no-caps/>
          </q-card-actions>
        </div>
      </div>

    </q-card>

    <create-edit-form :show_dialog="show_dialog"
                      :listErrors="(listErrors)"
                      :codigo.sync="localProvincia.codigo"
                      :designacao.sync="localProvincia.designacao"
                      :pais.sync="pais"
                      :paises="allPaises"
                      :submitting="submitting"
                      :close="close"
                      :createProvincia="createProvincia"
                      :removeProvincia="removeProvincia"/>
  </q-page>
</template>

<script>
import Pais from "src/store/models/pais/pais";
import Provincia from "src/store/models/provincia/provincia";

export default {
  name: 'Provincia',
  data() {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'distritos',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localProvincia: {
        codigo: '',
        designacao: '',
        pais: {}
      },
      pais: {
        codigo: '',
        designacao: '',
        nacionalidade: ''
      }
    }
  },
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // this.$store.dispatch('provincia/getProvincia', this.$route.params.id)
    // this.$store.dispatch('pais/getAllPais')
    // this.$store.dispatch('pais/getPais', this.provincia.pais.id)
    // Return a Promise if you are running an async job
    // Example:
    return Provincia.query().with('pais').find(currentRoute.params.id)  // store.dispatch('provincia/getProvincia', currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    provincia: {
      get() {
        return Provincia.query().with('pais').find(this.$route.params.id)
      },
      set(provincia) {
        this.$emit('update:provincia', '')
        Provincia.update(provincia)
      }
    },
    allPaises() {
      return Pais.query().all()
    }
  },
  components: {
    'create-edit-form': require('components/provincia/createEditForm.vue').default
  },
  methods: {
    removeProvincia(provincia) {
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
          message: 'A informação foi Removida com successo! [ ' + provincia.designacao + ' ]'
        })
        Provincia.api().delete("/provincia/" + provincia.id)
        this.$router.go(-1)
      })
    },
    createProvincia() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.provincia = this.localProvincia
      this.localProvincia.pais.id = this.pais.id
      Provincia.api().patch("/provincia/" + this.provincia.id, this.localProvincia).then(resp => {
        console.log('update ' + resp)
        this.$q.notify({
          type: 'positive',
          color: 'green-4',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 2000,
          position: 'bottom',
          classes: 'glossy',
          progress: true,
          message: 'A informação foi actualizada com successo!! [ ' + this.provincia.designacao + ' ]'
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
    editaProvincia(provincia) {
      this.editedIndex = 0
      this.provincia = Object.assign({}, provincia)
      this.localProvincia = Object.assign({}, provincia)
      this.pais = Pais.query().find(provincia.pais.id)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.props = this.provincia
      this.listErrors = []
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  filterFn(val, update, abort) {
    const stringOptions = Pais.query().all()
    if (val === '') {
      update(() => {
        this.options = stringOptions.map(pais => pais)
      })
    } else if (stringOptions.length === 0) {
      update(() => {
        this.options = []
      })
    } else {
      update(() => {
        this.options = stringOptions
          .map(pais => pais)
          .filter(pais => {
            return pais &&
              pais.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
          })
      })
    }
  },
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
    this.provincia.pais = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes da Província',
        basicInformation: 'Informacção do Província',
        codigo: 'Código',
        designacao: 'Designação',
        pais: 'País',
        distritos: 'Distritos',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Província',
        basicInformation: 'Informacção do Província',
        codigo: 'Código',
        designacao: 'Designação',
        pais: 'País',
        distritos: 'Distritos',
        comments: 'Comentários'
      }
    }
  }
}
</script>
