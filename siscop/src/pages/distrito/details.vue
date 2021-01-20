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
                      <q-item-label class="text-grey-9">{{ distrito.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ distrito.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('provincia') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ distrito.provincia.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                  <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn class="glossy" label="Voltar" color="primary" v-go-back=" '/distrito' " no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaDistrito(distrito)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeDistrito(distrito)" no-caps/>
                </q-card-actions>
                 </div>
      </div>
            </q-card>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localDistrito.codigo"
                    :designacao.sync="localDistrito.designacao"
                    :provincia.sync="provincia"
                    :provincias="allProvincias"
                    :submitting="submitting"
                    :close="close"
                    :createDistrito="createDistrito"
                    :removeDistrito="removeDistrito"/>
  </q-page>
</template>

<script>
import Distrito from 'src/store/models/distrito/distrito'
import Provincia from 'src/store/models/provincia/provincia'

export default {
  name: 'Provincia',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'cidades',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localDistrito: {
        codigo: '',
        designacao: '',
        provincia: {}
      },
      provincia: {
        codigo: '',
        designacao: ''
      }
    }
  },
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // Return a Promise if you are running an async job
    // Example:
    return Distrito.query().with('provincia').find(currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    distrito: {
      get () {
        return Distrito.query().with('provincia').find(this.$route.params.id)
      },
      set (distrito) {
        this.$emit('update:distrito', '')
        Distrito.update(distrito)
        // this.$store.commit('distrito/SET_UPDATE_DISTRITO', distrito)
      }
    },
    allProvincias () {
      return Provincia.query().all()
    }
  },
  components: {
    'create-edit-form': require('components/distrito/createEditForm.vue').default
  },
  methods: {
    removeDistrito (distrito) {
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
          message: 'A informação foi Removida com successo! [ ' + distrito.designacao + ' ]'
        })
       Distrito.api().delete("/distrito/"+distrito.id)
        this.$router.go(-1)
      })
    },
    createDistrito () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localDistrito.provincia.id = this.provincia.id
      this.distrito = this.localDistrito
      Distrito.api().patch("/distrito/"+this.localDistrito.id, this.localDistrito).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localDistrito.designacao + ' ]'
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
    editaDistrito (distrito) {
      this.editedIndex = 0
      this.distrito = Object.assign({}, distrito)
      this.localDistrito = Object.assign({}, distrito)
      this.provincia = this.allProvincias.filter(provincia => provincia.id === distrito.provincia.id)[0]
      this.show_dialog = true
    },
    close () {
      this.show_dialog = false
      this.props = this.distrito
       this.listErrors = []
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  filterFn (val, update, abort) {
    const stringOptions = this.allProvincias
    if (val === '') {
      update(() => {
        this.options = stringOptions.map(provincia => provincia)
      })
    } else if (stringOptions.length === 0) {
      update(() => {
        this.options = []
      })
    } else {
      update(() => {
        this.options = stringOptions
          .map(provincia => provincia)
          .filter(provincia => {
            return provincia &&
                   provincia.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
          })
      })
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.distrito.provincia = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Distrito',
        basicInformation: 'Informacção do Distrito',
        codigo: 'Código',
        designacao: 'Designação',
        provincia: 'Província',
        cidades: 'Cidades',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Distrito',
        basicInformation: 'Informacção do Distrito',
        codigo: 'Código',
        designacao: 'Designação',
        provincia: 'Província',
        cidades: 'Cidades',
        comments: 'Comentários'
      }
    }
  }
}
</script>
