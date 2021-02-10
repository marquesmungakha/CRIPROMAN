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
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ bairro.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('provincia') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ bairro.distrito.provincia.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('distrito') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ bairro.distrito.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('postoAdministrativo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ bairro.postoAdministrativo.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('localidade') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ bairro.localidade.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
          </div>
        </div>
      </q-card-section>
      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn v-go-back=" '/bairro' " class="glossy" color="primary" label="Voltar" no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaBairro(bairro)"/>
            <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeBairro(bairro)"/>
          </q-card-actions>
        </div>
      </div>
    </q-card>
    <create-edit-form :close="close"
                      :createBairro="createBairro"
                      :designacao.sync="localBairro.designacao"
                      :distrito.sync="distrito"
                      :distritos.sync="allDistritosFromProvincia"
                      :listErrors="listErrors"
                      :localidade.sync="localidade"
                      :localidades="allLocalidadeFromDistritoOrPostoAdministrativo"
                      :postoAdministrativo.sync="postoAdministrativo"
                      :postoAdministrativos.sync="allPostoAdministrativosFromDistrito"
                      :provincia.sync="provincia"
                      :provincias.sync="allProvincias"
                      :removeBairro="removeBairro"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'
import PostoAdministrativo from 'src/store/models/postoAdministrativo/postoAdministrativo'
import Localidade from 'src/store/models/localidade/localidade'
import Bairro from 'src/store/models/bairro/bairro'

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
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // Return a Promise if you are running an async job
    // Example:
    return Bairro.query().with('distrito.provincia').with('postoAdministrativo').with('localidade').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    bairro: {
      get() {
        return Bairro.query().with('distrito.provincia').with('postoAdministrativo').with('localidade').find(this.$route.params.id)
      },
      set(bairro) {
        this.$emit('update:bairro', '')
        Bairro.update(bairro)
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
    },
    allLocalidades() {
      return Localidade.query().with('distrito.provincia').with('postoAdministrativo').all()
    },
    allLocalidadeFromDistritoOrPostoAdministrativo() {
      if (this.postoAdministrativo != null) {
        return Localidade.query().with('postoAdministrativo').where('postoAdministrativo_id', this.postoAdministrativo.id).get()
      } else {
        return Localidade.query().with('distrito').where('distrito_id', this.distrito.id).get()
      }
    }
  },
  components: {
    'create-edit-form': require('components/bairro/createEditForm.vue').default
  },
  methods: {
    removeBairro(bairro) {
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
        Bairro.api().delete("/bairro/" + bairro.id)
        this.$router.go(-1)
      })
    },
    createBairro() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.localBairro.distrito = this.distrito
      this.localBairro.postoAdministrativo = this.postoAdministrativo
      this.localBairro.localidade = this.localidade
      this.localBairro.distrito_id = this.distrito.id
      this.localBairro.postoAdministrativo_id = this.postoAdministrativo.id
      this.localBairro.localidade_id = this.localidade.id
      Bairro.api().patch("/bairro/" + this.localBairro.id, this.localBairro).then(resp => {
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
    editaBairro(bairro) {
      this.editedIndex = 0
      this.localBairro = Object.assign({}, bairro)
      this.localidade = Localidade.query().find(this.localBairro.localidade_id)
      this.postoAdministrativo = PostoAdministrativo.query().find(this.localBairro.postoAdministrativo_id)
      this.distrito = Distrito.query().find(this.localBairro.distrito_id)
      this.provincia = Provincia.query().find(this.distrito.provincia_id)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.props = this.bairro
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
    this.bairro.distrito = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Bairro',
        basicInformation: 'Informação do Bairro',
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
        basicInformation: 'Informação do Bairro',
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
