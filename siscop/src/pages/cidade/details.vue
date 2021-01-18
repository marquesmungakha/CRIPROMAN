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
                      <q-item-label class="text-grey-9">{{ cidade.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ cidade.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('provincia') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getProvinciaLocal.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('distrito') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getDistritoLocal.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaCidade(cidade)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeCidade(cidade)" no-caps/>
                </q-card-actions>
            </q-card>
  <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localCidade.codigo"
                    :designacao.sync="localCidade.designacao"
                    :provincia.sync="provincia"
                    :distrito.sync="distrito"
                    :provincias="allProvincias"
                    :distritos="allDistritosFromProvincia"
                    :submitting="submitting"
                    :close="close"
                    :createCidade="createCidade"
                    :removeCidade="removeCidade"/>
  </q-page>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'

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
      localCidade: {
        codigo: '',
        designacao: '',
        provincia: {},
        distrito: {}
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      distrito: {
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
    return store.dispatch('cidade/getCidade', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    // this.$store.dispatch('cidade/getProvincia', this.$route.params.id)
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
    // this.$store.dispatch('provincia/getProvincia', this.cidade.provincia.id)
  },
  computed: {
    cidade: {
      get () {
        return this.$store.getters['cidade/cidade']
      },
      set (cidade) {
        this.SET_UPDATE_CIDADE({ cidade })
        this.$emit('update:cidade', '')
        this.$store.commit('cidade/SET_UPDATE_CIDADE', cidade)
      }
    },
    allProvincias () {
      return this.$store.getters['provincia/allProvincia']
    },
    allDistritos () {
      return this.$store.getters['distrito/allDistrito']
    },
    allDistritosFromProvincia () {
      return this.allDistritos.filter(distrito => distrito.provincia.id === this.provincia.id)
    },
    getProvinciaLocal () {
      const localProvincia = this.allProvincias.filter(provincia => provincia.id === this.cidade.provincia.id)
      if (localProvincia.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localProvincia[0] }
    },
    getDistritoLocal () {
      const localDistrito = this.allDistritos.filter(distrito => distrito.id === this.cidade.distrito.id)
      if (localDistrito.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localDistrito[0] }
    }
  },
  components: {
    'create-edit-form': require('components/cidade/createEditForm.vue').default
  },
  methods: {
    ...mapActions('cidade', ['getAllCidade', 'getCidade', 'addNewCidade', 'updateCidade', 'deleteCidade']),
    ...mapMutations('cidade', ['SET_UPDATE_CIDADE']),
    removeCidade (cidade) {
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
          message: 'A informação foi Removida com successo! [ ' + cidade.designacao + ' ]'
        })
        this.deleteCidade(cidade)
        this.$router.go(-1)
      })
    },
    createCidade () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localCidade.provincia.id = this.provincia.id
      this.localCidade.distrito.id = this.distrito.id
      this.updateCidade(this.localCidade).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.cidade.designacao + ' ]'
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
    editaCidade (cidade) {
      this.editedIndex = 0
      this.cidade = Object.assign({}, cidade)
      this.localCidade = Object.assign({}, cidade)
      this.provincia = this.allProvincias.filter(provincia => provincia.id === cidade.provincia.id)[0]
      this.distrito = this.allDistritos.filter(distrito => distrito.id === cidade.distrito.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('cidade/getCidade', this.$route.params.id)
      }
      this.$store.dispatch('distrito/getAllDistrito')
      this.$store.dispatch('cidade/getAllCidade')
      this.show_dialog = false
      this.props = this.cidade
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
    this.cidade.provincia = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Cidade',
        basicInformation: 'Informacção do Cidade',
        codigo: 'Código',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        cidades: 'Cidades',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Cidade',
        basicInformation: 'Informacção do Cidade',
        codigo: 'Código',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        cidades: 'Cidades',
        comments: 'Comentários'
      }
    }
  }
}
</script>
