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
            <!-- <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('codigo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ postoAdministrativo.codigo }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>  -->
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ postoAdministrativo.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('provincia') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ postoAdministrativo.distrito.provincia.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('distrito') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ postoAdministrativo.distrito.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
          </div>
        </div>
      </q-card-section>
      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn v-go-back=" '/postoAdministrativo' " class="glossy" color="primary" label="Voltar" no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar"
                   no-caps @click.stop="editaPostoAdministrativo(postoAdministrativo)"/>
            <q-btn class="glossy" color="negative" label="Apagar"
                   no-caps @click.stop="removePostoAdministrativo(postoAdministrativo)"/>
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
            <q-tab label="localidades" name="localidades"/>
          </q-tabs>

          <q-separator/>
          <q-tab-panels v-model="tab" animated>
            <q-tab-panel name="localidades">
            <list-localidade :localDistrito.sync="postoAdministrativo.distrito" :localProvincia.sync="postoAdministrativo.distrito.provincia" :localPostoAdministrativo.sync="postoAdministrativo"/>
            </q-tab-panel>
          </q-tab-panels>
        </q-card>
      </div>
    <create-edit-form :close="close"
                      :createPostoAdministrativo="createPostoAdministrativo"
                      :designacao.sync="localPostoAdministrativo.designacao"
                      :distrito.sync="distrito"
                      :distritos="allDistritosFromProvincia"
                      :listErrors="listErrors"
                      :provincia.sync="provincia"
                      :provincias.sync="allProvincias"
                      :removePostoAdministrativo="removePostoAdministrativo"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
   </div>
  </q-page>
</template>

<script>
import PostoAdministrativo from 'src/store/models/postoAdministrativo/postoAdministrativo'
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'

export default {
  name: 'Distrito',
  data() {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'localidades',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localPostoAdministrativo: {
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
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // Return a Promise if you are running an async job
    // Example:
    return PostoAdministrativo.query().with('distrito.provincia').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    postoAdministrativo: {
      get() {
        return PostoAdministrativo.query().with('distrito.provincia').find(this.$route.params.id)
      },
      set(postoAdministrativo) {
        this.$emit('update:postoAdministrativo', '')
        PostoAdministrativo.update(postoAdministrativo)
      }
    },
    allProvincias() {
      return Provincia.query().with('pais').all()
    },
    allDistritosFromProvincia() {
      return Distrito.query().with('provincia').where('provincia_id', this.provincia.id).get()
    },
    allDistritos() {
      return Distrito.query().all()
    }
  },
  components: {
    'create-edit-form': require('components/postoAdministrativo/createEditForm.vue').default,
    'list-localidade': require('pages/localidade/index.vue').default
  },
  methods: {
    removePostoAdministrativo(postoAdministrativo) {
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
        PostoAdministrativo.api().delete("/postoAdministrativo/" + postoAdministrativo.id)
        PostoAdministrativo.delete(postoAdministrativo.id)
        this.$router.go(-1)
      })
    },
    createPostoAdministrativo() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)
      this.localPostoAdministrativo.provincia = this.provincia
      this.localPostoAdministrativo.distrito = this.distrito
      this.localPostoAdministrativo.provincia_id = this.provincia.id
      this.localPostoAdministrativo.distrito_id = this.distrito.id
      PostoAdministrativo.api().patch("/postoAdministrativo/" + this.localPostoAdministrativo.id, this.localPostoAdministrativo).then(resp => {
        this.submitting = false
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localPostoAdministrativo.designacao + ' ]'
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
    editaPostoAdministrativo(postoAdministrativo) {
      this.editedIndex = 0
      this.postoAdministrativo = Object.assign({}, postoAdministrativo)
      this.localPostoAdministrativo = Object.assign({}, postoAdministrativo)
      this.distrito = Distrito.query().find(postoAdministrativo.distrito.id)
      this.provincia = Provincia.query().find(this.distrito.provincia_id)
      this.show_dialog = true
    },
    close() {
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
        localidades: 'Localidades',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do PostoAdministrativo',
        basicInformation: 'Informacção do PostoAdministrativo',
        codigo: 'Código',
        designacao: 'Designação',
        provincia: 'Província',
        distrito: 'Distrito',
        localidades: 'Localidades',
        comments: 'Comentários'
      }
    }
  }
}
</script>
