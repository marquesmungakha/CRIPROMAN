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
                <q-item-label caption lines="1">{{ $t('codigo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ cidade.codigo }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ cidade.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('provincia') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ cidade.provincia.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('distrito') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ cidade.distrito.designacao }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
          </div>
        </div>
      </q-card-section>
      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn v-go-back=" '/cidade' " class="glossy" color="primary" label="Voltar" no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaCidade(cidade)"/>
            <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeCidade(cidade)"/>
          </q-card-actions>
        </div>
      </div>
    </q-card>
    <create-edit-form :close="close"
                      :codigo.sync="localCidade.codigo"
                      :createCidade="createCidade"
                      :designacao.sync="localCidade.designacao"
                      :distrito.sync="distrito"
                      :distritos="allDistritosFromProvincia"
                      :listErrors="listErrors"
                      :provincia.sync="provincia"
                      :provincias="allProvincias"
                      :removeCidade="removeCidade"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
  </q-page>
</template>

<script>
import Cidade from 'src/store/models/cidade/cidade'
import Provincia from 'src/store/models/provincia/provincia'
import Distrito from 'src/store/models/distrito/distrito'

export default {
  name: 'Provincia',
  data() {
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
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // Return a Promise if you are running an async job
    // Example:
    return Cidade.query().with('provincia').with('distrito').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    cidade: {
      get() {
        return Cidade.query().with('provincia').with('distrito').find(this.$route.params.id)
      },
      set(cidade) {
        this.$emit('update:cidade', '')
        Cidade.update(cidade)
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
    'create-edit-form': require('components/cidade/createEditForm.vue').default
  },
  methods: {
    removeCidade(cidade) {
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
        Cidade.api().delete("/cidade/" + cidade.id)
        Cidade.delete(cidade.id)
        this.$router.go(-1)
      })
    },
    createCidade() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)
      this.localCidade.provincia = this.provincia
      this.localCidade.provincia = this.provincia
      this.localCidade.distrito = this.distrito
      this.localCidade.provincia_id = this.provincia.id
      this.localCidade.distrito_id = this.distrito.id
      Cidade.api().patch("/cidade/" + this.localCidade.id, this.localCidade).then(resp => {
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
    editaCidade(cidade) {
      this.editedIndex = 0
      this.localCidade = Object.assign({}, cidade)
      this.provincia = Provincia.query().find(cidade.provincia.id)
      this.distrito = Distrito.query().find(cidade.distrito.id)
      this.show_dialog = true
    },
    close() {
      this.getAllPais()
      this.getAllProvincia()
      this.getAllDistrito()
      this.show_dialog = false
      this.props = this.cidade
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  filterFn(val, update, abort) {
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
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
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
