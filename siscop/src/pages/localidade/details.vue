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
                      <q-item-label class="text-grey-9">{{ localidade.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ localidade.designacao }}</q-item-label>
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
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('postoAdministrativo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getPostoAdministrativoLocal.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaLocalidade(localidade)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeLocalidade(localidade)" no-caps/>
                </q-card-actions>
            </q-card>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localLocalidade.codigo"
                    :designacao.sync="localLocalidade.designacao"
                    :postoAdministrativo.sync="postoAdministrativo"
                    :distrito.sync="distrito"
                    :provincia.sync="provincia"
                    :provincias.sync="allProvincias"
                    :distritos.sync="allDistritosFromProvincia"
                    :postoAdministrativos.sync="allPostoAdministrativosFromDistrito"
                    :submitting="submitting"
                    :close="close"
                    :createLocalidade="createLocalidade"
                    :removeLocalidade="removeLocalidade"/>
  </q-page>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'

export default {
  name: 'Distrito',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'bairros',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localLocalidade: {
        codigo: '',
        designacao: '',
        distrito: {},
        postoAdministrativo: {}
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      postoAdministrativo: {
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
    return store.dispatch('localidade/getLocalidade', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
    this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
  },
  computed: {
    localidade: {
      get () {
        return this.$store.getters['localidade/localidade']
      },
      set (localidade) {
        this.SET_UPDATE_LOCALIDADE({ localidade })
        this.$emit('update:localidade', '')
        this.$store.commit('localidade/SET_UPDATE_LOCALIDADE', localidade)
      }
    },
    allProvincias () {
      return this.$store.getters['provincia/allProvincia']
    },
    allDistritos () {
      return this.$store.getters['distrito/allDistrito']
    },
    allPostoAdministrativos () {
      return this.$store.getters['postoAdministrativo/allPostoAdministrativo']
    },
    allDistritosFromProvincia () {
      return this.allDistritos.filter(distrito => distrito.provincia.id === this.provincia.id)
    },
    allPostoAdministrativosFromDistrito () {
      return this.allPostoAdministrativos.filter(postoAdministrativo => postoAdministrativo.distrito.id === this.distrito.id)
    },
    getDistritoLocal () {
      const localDistrito = this.allDistritos.filter(distrito => distrito.id === this.localidade.distrito.id)
      if (localDistrito.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localDistrito[0] }
    },
    getProvinciaLocal () {
      const localDistrito = this.allDistritos.filter(distrito => distrito.id === this.localidade.distrito.id)[0]
      const localProvincia = this.allProvincias.filter(provincia => provincia.id === localDistrito.provincia.id)
      if (localProvincia.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localProvincia[0] }
    },
    getPostoAdministrativoLocal () {
      const localPostoAdministrativo = this.allPostoAdministrativos.filter(postoAdministrativo => postoAdministrativo.id === this.localidade.postoAdministrativo.id)
      if (localPostoAdministrativo.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localPostoAdministrativo[0] }
    }
  },
  components: {
    'create-edit-form': require('components/localidade/createEditForm.vue').default
  },
  methods: {
    ...mapActions('localidade', ['getAllLocalidade', 'getLocalidade', 'addNewLocalidade', 'updateLocalidade', 'deleteLocalidade']),
    ...mapMutations('localidade', ['SET_UPDATE_LOCALIDADE']),
    removeLocalidade (localidade) {
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
          message: 'A informação foi Removida com successo! [ ' + localidade.designacao + ' ]'
        })
        this.deleteLocalidade(localidade)
        this.$router.go(-1)
      })
    },
    createLocalidade () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localLocalidade.distrito.id = this.distrito.id
      this.localLocalidade.postoAdministrativo.id = this.postoAdministrativo.id
      this.updateLocalidade(this.localLocalidade).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localidade.designacao + ' ]'
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
    editaLocalidade (localidade) {
      this.editedIndex = 0
      this.localidade = Object.assign({}, localidade)
      this.localLocalidade = Object.assign({}, localidade)
      this.postoAdministrativo = this.allPostoAdministrativos.filter(postoAdministrativo => postoAdministrativo.id === this.localidade.postoAdministrativo.id)[0]
      this.distrito = this.allDistritos.filter(distrito => distrito.id === this.localidade.distrito.id)[0]
      this.provincia = this.allProvincias.filter(provincia => provincia.id === this.distrito.provincia.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('localidade/getLocalidade', this.$route.params.id)
      }
      this.$store.dispatch('provincia/getAllProvincia')
      this.$store.dispatch('distrito/getAllDistrito')
      this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
      this.show_dialog = false
      this.props = this.localidade
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.localidade.distrito = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Localidade',
        basicInformation: 'Informacção do Localidade',
        codigo: 'Código',
        designacao: 'Designação',
        distrito: 'Distrito',
        provincia: 'Província',
        postoAdministrativo: 'Posto Administrativo',
        bairros: 'Bairros',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Localidade',
        basicInformation: 'Informacção do Localidade',
        codigo: 'Código',
        designacao: 'Designação',
        distrito: 'Distrito',
        provincia: 'Província',
        postoAdministrativo: 'Posto Administrativo',
        bairros: 'Bairros',
        comments: 'Comentários'
      }
    }
  }
}
</script>
