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
                      <q-item-label lines="1" caption >{{ $t('codigo') }}</q-item-label>
                      <q-item-label class="text-grey-9">{{ marca.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ marca.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('tipoMeio') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getTipoMeioLocal.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaMarca(marca)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeMarca(marca)" no-caps/>
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
              <q-tab name="distritos" label="Distritos" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="distritos">
                <div class="text-h6">{{ $t('distritos') }}</div>
                Component List Distritos
              </q-tab-panel>
            </q-tab-panels>
          </q-card>

          <q-card>
            <q-tabs
              v-model="tab1"
              class="bg-teal text-white shadow-2"
              active-color="white"
              indicator-color="white"
              align="left"
              narrow-indicator >
              <q-tab name="comments" label="Coments" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab1" animated>
              <q-tab-panel name="comments">
                <div class="text-h6">{{ $t('comments') }}</div>
                 Component List 1
              </q-tab-panel>
            </q-tab-panels>
          </q-card>

          <q-card>
            <q-tabs
              v-model="tab2"
              class="bg-teal text-white shadow-2"
              active-color="white"
              indicator-color="white"
              align="left"
              narrow-indicator >
              <q-tab name="comments" label="Coments" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab2" animated>
              <q-tab-panel name="comments">
                <div class="text-h6">{{ $t('comments') }}</div>
                 Component List 2
              </q-tab-panel>
            </q-tab-panels>
          </q-card>

        </div>
    </div>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localMarca.codigo"
                    :designacao.sync="localMarca.designacao"
                    :tipoMeio.sync="tipoMeio"
                    :tipoMeios="allTipoMeios"
                    :submitting="submitting"
                    :close="close"
                    :createMarca="createMarca"
                    :removeMarca="removeMarca"/>
  </q-page>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'

export default {
  name: 'Marca',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'distritos',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localMarca: {
        codigo: '',
        designacao: ''
      },
      tipoMeio: {
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
    // this.$store.dispatch('marca/getMarca', this.$route.params.id)
    // this.$store.dispatch('tipoMeio/getAllTipoMeio')
    // this.$store.dispatch('tipoMeio/getTipoMeio', this.marca.tipoMeio.id)
    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('marca/getMarca', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('tipoMeio/getAllTipoMeio')
  },
  computed: {
    marca: {
      get () {
        return this.$store.getters['marca/marca']
      },
      set (marca) {
        this.SET_UPDATE_MARCA({ marca })
        this.$emit('update:marca', '')
        this.$store.commit('marca/SET_UPDATE_MARCA', marca)
      }
    },
    allTipoMeios () {
      return this.$store.getters['tipoMeio/allTipoMeio']
    },
    getTipoMeioLocal () {
      if (this.allTipoMeios.filter(tipoMeio => tipoMeio.id === this.marca.tipoMeio.id).length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return this.allTipoMeios.filter(tipoMeio => tipoMeio.id === this.marca.tipoMeio.id)[0] }
    }
  },
  components: {
    'create-edit-form': require('components/marca/createEditForm.vue').default
  },
  methods: {
    ...mapActions('marca', ['getAllMarca', 'getMarca', 'addNewMarca', 'updateMarca', 'deleteMarca']),
    ...mapMutations('marca', ['SET_UPDATE_MARCA']),
    removeMarca (marca) {
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
          message: 'A informação foi Removida com successo! [ ' + marca.designacao + ' ]'
        })
        this.deleteMarca(marca)
        this.$router.go(-1)
      })
    },
    createMarca () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localMarca.tipoMeio.id = this.tipoMeio.id
      this.updateMarca(this.localMarca).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.marca.designacao + ' ]'
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
    editaMarca (marca) {
      this.editedIndex = 0
      this.marca = Object.assign({}, marca)
      this.localMarca = Object.assign({}, marca)
      this.tipoMeio = this.allTipoMeios.filter(tipoMeio => tipoMeio.id === marca.tipoMeio.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('marca/getMarca', this.$route.params.id)
      }
      this.$store.dispatch('tipoMeio/getAllTipoMeio')
      this.show_dialog = false
      this.props = this.marca
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  filterFn (val, update, abort) {
    const stringOptions = this.allTipoMeios
    if (val === '') {
      update(() => {
        this.options = stringOptions.map(tipoMeio => tipoMeio)
      })
    } else if (stringOptions.length === 0) {
      update(() => {
        this.options = []
      })
    } else {
      update(() => {
        this.options = stringOptions
          .map(tipoMeio => tipoMeio)
          .filter(tipoMeio => {
            return tipoMeio &&
                   tipoMeio.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
          })
      })
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.marca.tipoMeio = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes da MARCA',
        basicInformation: 'Informacção do MARCA',
        codigo: 'Código',
        designacao: 'Designação',
        tipoMeio: 'Tipo de Meio',
        distritos: 'Distritos',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do MARCA',
        basicInformation: 'Informacção do MARCA',
        codigo: 'Código',
        designacao: 'Designação',
        tipoMeio: 'Tipo de Meio',
        distritos: 'Distritos',
        comments: 'Comentários'
      }
    }
  }
}
</script>
