<template>
  <q-page>
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
                <q-item-label caption lines="1">{{ $t('sigla') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ orgao.sigla }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ orgao.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('tipoOrgao') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ orgao.tipoOrgao.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('nivel') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ orgao.nivel }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('provincia') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ orgao.provincia.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('distrito') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ orgao.distrito.designacao }} </q-item-label>
              </q-item-section>
            </q-item>
          </div>
        </div>
      </q-card-section>
      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn @click="$router.go(-1)" class="glossy" color="primary" label="Voltar" no-caps/>
           <!--div v-else>
            <q-btn v-go-back=" '/orgao' " class="glossy" color="primary" label="Voltar" no-caps/>
          </div-->
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaOrgao(orgao)"/>
            <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeOrgao(orgao)"/>
          </q-card-actions>
        </div>
      </div>
    </q-card>

    <q-card>
      <q-tabs
        v-model="tab"
        active-color="white"
        align="center"
        class="bg-teal text-white shadow-2"
        indicator-color="white"
        narrow-indicator>
        <q-tab label="Alocação" name="alocacao"/>
        <q-tab label="Sub Orgão" name="suborgao"/>
      </q-tabs>

      <q-separator/>

      <q-tab-panels v-model="tab" animated>
        <q-tab-panel name="alocacao">
          <list-alocacao :orgao.sync="orgao"/>
        </q-tab-panel>
        <q-tab-panel name="suborgao">
          <list-suborgao :orgao.sync="orgao"/>
        </q-tab-panel>
      </q-tab-panels>
    </q-card>

    <create-edit-form :close="close"
                      :createOrgao="createOrgao"
                      :designacao.sync="localOrgao.designacao"
                      :distrito.sync="distrito"
                      :distritos="allDistritosFromProvincia"
                      :endereco.sync="localOrgao.endereco"
                      :listErrors="listErrors"
                      :nivel.sync="localOrgao.nivel"
                      :provincia.sync="provincia"
                      :provincias="allProvincias"
                      :removeOrgao="removeOrgao"
                      :show_dialog="show_dialog"
                      :sigla.sync="localOrgao.sigla"
                      :submitting="submitting"
                      :tipoOrgao.sync="tipoOrgao"
                      :tipoOrgaos="allTipoOrgaos"/>
  </q-page>
</template>

<script>
import Orgao from 'src/store/models/orgao/orgao'
import Distrito from 'src/store/models/distrito/distrito'
import Provincia from 'src/store/models/provincia/provincia'
import TipoOrgao from 'src/store/models/tipoOrgao/tipoOrgao'
import Pais from 'src/store/models/pais/pais'

export default {
  name: 'Orgao',
  data() {
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
        distrito: {}
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
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
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
    return Orgao.query().with('tipoOrgao').with('provincia').with('distrito').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    orgao: {
      get() {

        return Orgao.query().with('tipoOrgao').with('provincia').with('distrito').find(this.$route.params.id)
      },
      set(orgao) {
        this.$emit('update:orgao', '')
        Orgao.update(orgao)
      }
    },
     allTipoOrgaos() {
      return TipoOrgao.query().all()
    },
    allPais() {
      return Pais.query().all()
    },
    allProvincias() {
      return Provincia.query().all()
    },
    allDistritos() {
      return Distrito.query().all()
    },
    allDistritosFromProvincia() {
      return Distrito.query().where('provincia_id', this.provincia.id).get()
    }
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
  },
  components: {
    'create-edit-form': require('components/orgao/createEditForm.vue').default,
    'list-alocacao': require('pages/alocacao/index.vue').default,
    'list-suborgao': require('pages/dependenciaOrgao/index.vue').default
  },
  methods: {
    removeOrgao(orgao) {
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
        Orgao.api().delete("/orgao/" + orgao.id)
        this.$router.go(-1)
      })
    },
    createOrgao() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localOrgao.provincia_id = this.provincia.id
      this.localOrgao.provincia = this.provincia
      this.localOrgao.provincia.pais = Pais.query().find(this.provincia.pais_id)
      this.localOrgao.tipoOrgao_id = this.tipoOrgao.id
      this.localOrgao.tipoOrgao = this.tipoOrgao
      if (this.distrito == null) {
        this.distrito = {}
      } else {
        this.localOrgao.distrito_id = this.distrito.id
        this.localOrgao.distrito.provincia = this.provincia
      }
      this.localOrgao.distrito = this.distrito
      Orgao.api().patch("/orgao/" + this.localOrgao.id, this.localOrgao).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localOrgao.designacao + ' ]'
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
    editaOrgao(orgao) {
      this.editedIndex = 0
      this.orgao = Object.assign({}, orgao)
      this.localOrgao = Object.assign({}, orgao)
      this.tipoOrgao = TipoOrgao.query().find(orgao.tipoOrgao.id)
      this.provincia = Provincia.query().with('pais').find(orgao.provincia.id)
      this.distrito = Distrito.query().with('provincia').find(orgao.distrito.id)
      this.show_dialog = true
    },
    close() {
      this.listErrors = {}
      this.show_dialog = false
      this.props = this.orgao
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  filterFn(val, update, abort) {
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
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
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
