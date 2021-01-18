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
                      <q-item-label lines="1" caption >{{ $t('sigla') }}</q-item-label>
                      <q-item-label class="text-grey-9">{{ orgao.sigla }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ orgao.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('tipoOrgao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getTipoOrgaoLocal.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                 <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('nivel') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ orgao.nivel }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('provincia') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getProvincia.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('distrito') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ getDistrito.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaOrgao(orgao)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeOrgao(orgao)" no-caps/>
                </q-card-actions>
            </q-card>

          <q-card>
            <q-tabs
              v-model="tab"
              class="bg-teal text-white shadow-2"
              active-color="white"
              indicator-color="white"
              align="left"
              narrow-indicator >
              <q-tab name="alocacao" label="Alocação" />
            </q-tabs>

            <q-separator />

            <q-tab-panels v-model="tab" animated>
              <q-tab-panel name="alocacao">
                    <list-alocacao :orgao.sync="orgao"/>
              </q-tab-panel>
            </q-tab-panels>
          </q-card>

    <create-edit-form :show_dialog="show_dialog"
                    :listErrors="listErrors"
                    :sigla.sync="localOrgao.sigla"
                    :designacao.sync="localOrgao.designacao"
                    :tipoOrgao.sync="tipoOrgao"
                    :nivel.sync="localOrgao.nivel"
                    :endereco.sync="localOrgao.endereco"
                    :provincia.sync="provincia"
                    :distrito.sync="distrito"
                    :tipoOrgaos="allTipoOrgaos"
                    :provincias="allProvincias"
                    :distritos="allDistritosFromProvincia"
                    :submitting="submitting"
                    :close="close"
                    :createOrgao="createOrgao"
                    :removeOrgao="removeOrgao"/>
  </q-page>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'

export default {
  name: 'Orgao',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'alocacao',
      tab1: 'Agentes',
      tab2: 'Magistrados',
      splitterModel: 20,
      localOrgao: {
        sigla: '',
        designacao: '',
        nivel: 0,
        endereco: '',
        provincia: {},
        distrito: {},
        class: 'org.devinthesky.unidadeorganica.Orgao'
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      distrito: {
        codigo: '',
        designacao: ''
      },
      tipoOrgao: {
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
    // this.$store.dispatch('orgao/getOrgao', this.$route.params.id)
    // this.$store.dispatch('tipoOrgao/getAllTipoOrgao')
    // this.$store.dispatch('tipoOrgao/getTipoOrgao', this.orgao.tipoOrgao.id)
    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('orgao/getOrgao', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
    this.$store.dispatch('tipoOrgao/getAllTipoOrgao')
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('distrito/getAllDistrito')
    this.$store.dispatch('alocacao/getAllAlocacao')
  },
  computed: {
    orgao: {
      get () {
        return this.$store.getters['orgao/orgao']
      },
      set (orgao) {
        this.SET_UPDATE_ORGAO({ orgao })
        this.$emit('update:orgao', '')
        this.$store.commit('orgao/SET_UPDATE_ORGAO', orgao)
      }
    },
    allTipoOrgaos () {
      return this.$store.getters['tipoOrgao/allTipoOrgao']
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
    // allPostoAdministrativos () {
    //   return this.$store.getters['postoAdministrativo/allPostoAdministrativo']
    // },
    // allCidades () {
    //   return this.$store.getters['cidade/allCidade']
    // },
    // allBairros () {
    //   return this.$store.getters['bairro/allBairro']
    // },
    // allLocalidades () {
    //   return this.$store.getters['localidade/allLocalidade']
    // },
    getTipoOrgaoLocal () {
      const tipoOrgaoLocal = this.allTipoOrgaos.filter(tipoOrgao => tipoOrgao.id === this.orgao.tipoOrgao.id)
      if (tipoOrgaoLocal.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return tipoOrgaoLocal[0] }
    },
    getProvincia () {
      const provincias = this.allProvincias.filter(provincia => this.orgao.provincia.id === provincia.id)
      if (provincias.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return provincias[0] }
    },
    getDistrito () {
      const distritos = this.allDistritos.filter(distrito => this.orgao.distrito.id === distrito.id)
      if (distritos.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return distritos[0] }
    }
  },
  components: {
    'create-edit-form': require('components/orgao/createEditForm.vue').default,
    'list-alocacao': require('pages/alocacao/index.vue').default
  },
  methods: {
    ...mapActions('orgao', ['getAllOrgao', 'getOrgao', 'addNewOrgao', 'updateOrgao', 'deleteOrgao']),
    ...mapActions('alocacao', ['getAllAlocacao', 'addNewAlocacao', 'updateAlocacao', 'deleteAlocacao']),
    ...mapMutations('orgao', ['SET_UPDATE_ORGAO']),
    removeOrgao (orgao) {
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
          message: 'A informação foi Removida com successo! [ ' + orgao.designacao + ' ]'
        })
        this.deleteOrgao(orgao)
        this.$router.go(-1)
      })
    },
    createOrgao () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      if (this.distrito == null) { this.distrito = {} }
      this.localOrgao.distrito = this.distrito
      this.localOrgao.provincia = this.provincia
      this.localOrgao.tipoOrgao = this.tipoOrgao
      this.updateOrgao(this.localOrgao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.orgao.designacao + ' ]'
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
    editaOrgao (orgao) {
      this.editedIndex = 0
      this.orgao = Object.assign({}, orgao)
      this.localOrgao = Object.assign({}, orgao)
      this.tipoOrgao = this.allTipoOrgaos.filter(tipoOrgao => tipoOrgao.id === orgao.tipoOrgao.id)[0]
      this.provincia = this.allProvincias.filter(provincia => provincia.id === orgao.provincia.id)[0]
      this.distrito = this.allDistritos.filter(distrito => distrito.id === orgao.distrito.id)[0]
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('orgao/getOrgao', this.$route.params.id)
      }
      this.$store.dispatch('tipoOrgao/getAllTipoOrgao')
      this.$store.dispatch('provincia/getAllProvincia')
      this.$store.dispatch('distrito/getAllDistrito')
      this.show_dialog = false
      this.props = this.orgao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  filterFn (val, update, abort) {
    const stringOptions = this.allTipoOrgaos
    if (val === '') {
      update(() => {
        this.options = stringOptions.map(tipoOrgao => tipoOrgao)
      })
    } else if (stringOptions.length === 0) {
      update(() => {
        this.options = []
      })
    } else {
      update(() => {
        this.options = stringOptions
          .map(tipoOrgao => tipoOrgao)
          .filter(tipoOrgao => {
            return tipoOrgao &&
                   tipoOrgao.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
          })
      })
    }
  },
  abortFilterFn () {
    // console.log('delayed filter aborted')
  },
  setModel (val) {
    this.orgao.tipoOrgao = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes da ORGAO',
        basicInformation: 'Informacção do ORGAO',
        codigo: 'Código',
        designacao: 'Designação',
        tipoOrgao: 'Tipo de Meio',
        nivel: 'Nível',
        provincia: 'Província',
        distrito: 'Distrito',
        distritos: 'Distritos',
        alocacao: 'Alocação',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do ORGAO',
        basicInformation: 'Informacção do ORGAO',
        codigo: 'Código',
        designacao: 'Designação',
        tipoOrgao: 'Tipo de Meio',
        nivel: 'Nível',
        provincia: 'Província',
        distrito: 'Distrito',
        distritos: 'Distritos',
        alocacao: 'Alocação',
        comments: 'Comentários'
      }
    }
  }
}
</script>
