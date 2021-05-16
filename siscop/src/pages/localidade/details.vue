<template>
  <q-page>
    <div class="row q-col-gutter-sm q-ma-xs">
        <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
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
                <q-item-label caption lines="1">{{ $t('codigo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ localidade.codigo }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ localidade.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('provincia') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ localidade.distrito.provincia.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('distrito') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ localidade.distrito.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('postoAdministrativo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ localidade.postoAdministrativo.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
          </div>
        </div>
      </q-card-section>
      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn v-go-back=" '/localidade' " class="glossy" color="primary" label="Voltar" no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaLocalidade(localidade)"/>
            <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeLocalidade(localidade)"/>
          </q-card-actions>
        </div>
      </div>
    </q-card>
    </div>
    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
              <q-card>
                <q-tabs
                  v-model="tab"
                  active-color="white"
                  align="center"
                  class="bg-teal text-white shadow-2"
                  indicator-color="white"
                  narrow-indicator>
                  <q-tab label="bairros" name="bairros"/>
                </q-tabs>

                <q-separator/>
                <q-tab-panels v-model="tab" animated>
                  <q-tab-panel name="bairros">
                  <list-bairro :localLocalidade.sync="localidade" :localDistrito.sync="localidade.distrito" :localProvincia.sync="localidade.distrito.provincia" :localPostoAdministrativo.sync="localidade.postoAdministrativo"/>
                  </q-tab-panel>
                </q-tab-panels>
              </q-card>
          </div>
    <create-edit-form :close="close"
                      :codigo.sync="localLocalidade.codigo"
                      :createLocalidade="createLocalidade"
                      :designacao.sync="localLocalidade.designacao"
                      :distrito.sync="distrito"
                      :distritos.sync="allDistritosFromProvincia"
                      :listErrors="listErrors"
                      :postoAdministrativo.sync="postoAdministrativo"
                      :postoAdministrativos.sync="allPostoAdministrativosFromDistrito"
                      :provincia.sync="provincia"
                      :provincias.sync="allProvincias"
                      :removeLocalidade="removeLocalidade"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
    </div>
  </q-page>
</template>

<script>
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'
import PostoAdministrativo from 'src/store/models/postoAdministrativo/postoAdministrativo'
import Localidade from 'src/store/models/localidade/localidade'

export default {
  name: 'Distrito',
  data() {
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
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // Return a Promise if you are running an async job
    // Example:
    return Localidade.query().with('distrito').with('postoAdministrativo').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    localidade: {
      get() {
        return Localidade.query().with('distrito.provincia').with('postoAdministrativo').find(this.$route.params.id)
      },
      set(localidade) {
        this.$emit('update:localidade', '')
        Localidade.update(localidade)
      }
    },
    allProvincias() {
      return Provincia.query().with('pais').all()
    },
    allDistritosFromProvincia() {
      return Distrito.query().with('provincia').where('provincia_id', this.provincia.id).get()
    },
    allDistritos() {
      return Distrito.query().with('provincia').all()
    },
    allPostoAdministrativos() {
      return PostoAdministrativo.query().with('distrito.provincia').all()
    },
    allPostoAdministrativosFromDistrito() {
      return PostoAdministrativo.query().with('distrito').where('distrito_id', this.distrito.id).get()
    }
  },
  components: {
    'create-edit-form': require('components/localidade/createEditForm.vue').default,
    'list-bairro': require('pages/bairro/index.vue').default
  },
  methods: {
    removeLocalidade(localidade) {
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
        Localidade.api().delete("/localidade/" + localidade.id)
        this.$router.go(-1)
      })
    },
    createLocalidade() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localLocalidade.distrito = this.distrito
      this.localLocalidade.postoAdministrativo = this.postoAdministrativo
      this.localLocalidade.postoAdministrativo_id = this.postoAdministrativo.id
      this.localLocalidade.distrito_id = this.distrito.id
      Localidade.api().patch("/localidade/" + this.localLocalidade.id, this.localLocalidade).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localLocalidade.designacao + ' ]'
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
    editaLocalidade(localidade) {
      this.editedIndex = 0
      this.localidade = Object.assign({}, localidade)
      this.postoAdministrativo = PostoAdministrativo.query().find(localidade.postoAdministrativo_id)
      this.distrito = Distrito.query().find(localidade.distrito.id)
      this.provincia = Provincia.query().find(this.distrito.provincia_id)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.props = this.localidade
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
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
