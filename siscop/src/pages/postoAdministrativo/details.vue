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
                      <q-item-label class="text-grey-9">{{ postoAdministrativo.codigo }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ postoAdministrativo.designacao }}</q-item-label>
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
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaPostoAdministrativo(postoAdministrativo)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removePostoAdministrativo(postoAdministrativo)" no-caps/>
                </q-card-actions>
            </q-card>
<create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :codigo.sync="localPostoAdministrativo.codigo"
                    :designacao.sync="localPostoAdministrativo.designacao"
                    :distrito.sync="distrito"
                    :provincia.sync="provincia"
                    :provincias.sync="allProvincias"
                    :distritos="allDistritosFromProvincia"
                    :submitting="submitting"
                    :close="close"
                    :createPostoAdministrativo="createPostoAdministrativo"
                    :removePostoAdministrativo="removePostoAdministrativo"/>
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
      tab: 'postoAdministrativos',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localPostoAdministrativo: {
        codigo: '',
        designacao: '',
        distrito: {}
      },
      distrito: {
        codigo: '',
        designacao: ''
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
    return store.dispatch('postoAdministrativo/getPostoAdministrativo', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
  },
  computed: {
    postoAdministrativo: {
      get () {
        return this.$store.getters['postoAdministrativo/postoAdministrativo']
      },
      set (postoAdministrativo) {
        this.SET_UPDATE_POSTOADMINISTRATIVO({ postoAdministrativo })
        this.$emit('update:postoAdministrativo', '')
        this.$store.commit('postoAdministrativo/SET_UPDATE_POSTOADMINISTRATIVO', postoAdministrativo)
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
    getDistritoLocal () {
      const localDistrito = this.allDistritos.filter(distrito => distrito.id === this.postoAdministrativo.distrito.id)
      if (localDistrito.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localDistrito[0] }
    },
    getProvinciaLocal () {
      const localDistrito = this.allDistritos.filter(distrito => distrito.id === this.postoAdministrativo.distrito.id)[0]
      const localProvincia = this.allProvincias.filter(provincia => provincia.id === localDistrito.provincia.id)
      if (localProvincia.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localProvincia[0] }
    }
  },
  components: {
    'create-edit-form': require('components/postoAdministrativo/createEditForm.vue').default
  },
  methods: {
    ...mapActions('postoAdministrativo', ['getAllPostoAdministrativo', 'getPostoAdministrativo', 'addNewPostoAdministrativo', 'updatePostoAdministrativo', 'deletePostoAdministrativo']),
    ...mapMutations('postoAdministrativo', ['SET_UPDATE_POSTOADMINISTRATIVO']),
    removePostoAdministrativo (postoAdministrativo) {
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
          message: 'A informação foi Removida com successo! [ ' + postoAdministrativo.designacao + ' ]'
        })
        this.deletePostoAdministrativo(postoAdministrativo)
        this.$router.go(-1)
      })
    },
    createPostoAdministrativo () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localPostoAdministrativo.distrito.id = this.distrito.id
      this.updatePostoAdministrativo(this.localPostoAdministrativo).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.postoAdministrativo.designacao + ' ]'
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
    editaPostoAdministrativo (postoAdministrativo) {
      this.editedIndex = 0
      this.postoAdministrativo = Object.assign({}, postoAdministrativo)
      this.localPostoAdministrativo = Object.assign({}, postoAdministrativo)
      this.distrito = this.allDistritos.filter(distrito => distrito.id === postoAdministrativo.distrito.id)[0]
      this.provincia = this.allProvincias.filter(provincia => provincia.id === this.distrito.provincia.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('postoAdministrativo/getPostoAdministrativo', this.$route.params.id)
      }
      this.$store.dispatch('provincia/getAllProvincia')
      this.$store.dispatch('distrito/getAllDistrito')
      this.show_dialog = false
      this.props = this.postoAdministrativo
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do PostoAdministrativo',
        basicInformation: 'Informacção do PostoAdministrativo',
        codigo: 'Código',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        postoAdministrativos: 'PostoAdministrativos',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do PostoAdministrativo',
        basicInformation: 'Informacção do PostoAdministrativo',
        codigo: 'Código',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        postoAdministrativos: 'PostoAdministrativos',
        comments: 'Comentários'
      }
    }
  }
}
</script>
