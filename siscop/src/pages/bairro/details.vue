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
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ bairro.designacao }}</q-item-label>
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
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('localidade') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getLocalidadeLocal.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaBairro(bairro)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeBairro(bairro)" no-caps/>
                </q-card-actions>
            </q-card>
 <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :designacao.sync="localBairro.designacao"
                    :localidade.sync="localidade"
                    :postoAdministrativo.sync="postoAdministrativo"
                    :distrito.sync="distrito"
                    :provincia.sync="provincia"
                    :provincias.sync="allProvincias"
                    :distritos.sync="allDistritosFromProvincia"
                    :postoAdministrativos.sync="allPostoAdministrativosFromDistrito"
                    :localidades="allLocalidadeFromDistritoOrPostoAdministrativo"
                    :submitting="submitting"
                    :close="close"
                    :createBairro="createBairro"
                    :removeBairro="removeBairro"/>
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
      localBairro: {
        designacao: '',
        distrito: {},
        postoAdministrativo: {},
        localidade: {}
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
      },
      localidade: {
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
    return store.dispatch('bairro/getBairro', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
    this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
    this.$store.dispatch('localidade/getAllLocalidade')
  },
  computed: {
    bairro: {
      get () {
        return this.$store.getters['bairro/bairro']
      },
      set (bairro) {
        this.SET_UPDATE_BAIRRO({ bairro })
        this.$emit('update:bairro', '')
        this.$store.commit('bairro/SET_UPDATE_BAIRRO', bairro)
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
    allLocalidades () {
      return this.$store.getters['localidade/allLocalidade']
    },
    allDistritosFromProvincia () {
      return this.allDistritos.filter(distrito => distrito.provincia.id === this.provincia.id)
    },
    allPostoAdministrativosFromDistrito () {
      return this.allPostoAdministrativos.filter(postoAdministrativo => postoAdministrativo.distrito.id === this.distrito.id)
    },
    allLocalidadeFromDistritoOrPostoAdministrativo () {
      if (this.postoAdministrativo != null) { return this.allLocalidades.filter(localidade => localidade.postoAdministrativo.id === this.postoAdministrativo.id) } else { return this.allLocalidades.filter(localidade => localidade.distrito.id === this.distrito.id) }
    },
    getDistritoLocal () {
      const localDistrito = this.allDistritos.filter(distrito => distrito.id === this.bairro.distrito.id)
      if (localDistrito.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localDistrito[0] }
    },
    getProvinciaLocal () {
      const localDistrito = this.allDistritos.filter(distrito => distrito.id === this.bairro.distrito.id)
      const localProvincia = this.allProvincias.filter(provincia => provincia.id === localDistrito[0].provincia.id)
      if (localProvincia.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localProvincia[0] }
    },
    getPostoAdministrativoLocal () {
      const localPostoAdministrativo = this.allPostoAdministrativos.filter(postoAdministrativo => postoAdministrativo.id === this.bairro.postoAdministrativo.id)
      if (localPostoAdministrativo.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localPostoAdministrativo[0] }
    },
    getLocalidadeLocal () {
      const localLocalidade = this.allLocalidades.filter(localidade => localidade.id === this.bairro.localidade.id)
      if (localLocalidade.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localLocalidade[0] }
    }
  },
  components: {
    'create-edit-form': require('components/bairro/createEditForm.vue').default
  },
  methods: {
    ...mapActions('bairro', ['getAllBairro', 'getBairro', 'addNewBairro', 'updateBairro', 'deleteBairro']),
    ...mapMutations('bairro', ['SET_UPDATE_BAIRRO']),
    removeBairro (bairro) {
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
          message: 'A informação foi Removida com successo! [ ' + bairro.designacao + ' ]'
        })
        this.deleteBairro(bairro)
        this.$router.go(-1)
      })
    },
    createBairro () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localBairro.distrito.id = this.distrito.id
      this.localBairro.postoAdministrativo.id = this.postoAdministrativo.id
      this.localBairro.localidade.id = this.localidade.id
      this.updateBairro(this.localBairro).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.bairro.designacao + ' ]'
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
    editaBairro (bairro) {
      this.editedIndex = 0
      this.bairro = Object.assign({}, bairro)
      this.localBairro = Object.assign({}, bairro)
      this.localidade = this.allLocalidades.filter(localidade => localidade.id === bairro.localidade.id)[0]
      this.postoAdministrativo = this.allPostoAdministrativos.filter(postoAdministrativo => postoAdministrativo.id === bairro.postoAdministrativo.id)[0]
      this.distrito = this.allDistritos.filter(distrito => distrito.id === bairro.distrito.id)[0]
      this.provincia = this.allProvincias.filter(provincia => provincia.id === this.distrito.provincia.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('bairro/getBairro', this.$route.params.id)
      }
      this.$store.dispatch('localidade/getAllLocalidade')
      this.$store.dispatch('distrito/getAllDistrito')
      this.$store.dispatch('postoAdministrativo/getAllPostoAdministrativo')
      this.show_dialog = false
      this.props = this.bairro
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.bairro.distrito = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Bairro',
        basicInformation: 'Informacção do Bairro',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        postoAdministrativo: 'Posto Administrativo',
        localidade: 'Localidade',
        bairros: 'Bairros',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Bairro',
        basicInformation: 'Informacção do Bairro',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        postoAdministrativo: 'Posto Administrativo',
        localidade: 'Localidade',
        bairros: 'Bairros',
        comments: 'Comentários'
      }
    }
  }
}
</script>
