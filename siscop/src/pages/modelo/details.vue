<template>
  <q-page>
    <div class="q-ma-xs">
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
                    <q-item-label class="text-grey-9">{{ modelo.codigo }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('designacao') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ modelo.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label caption lines="1">{{ $t('marca') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ modelo.marca.designacao }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
              </div>
            </div>
          </q-card-section>
          <div class="row">
            <div class="col">
              <q-card-actions align="left">
                <q-btn v-go-back=" '/modelo' " class="glossy" color="primary" label="Voltar" no-caps/>
              </q-card-actions>
            </div>
            <div class="col">
              <q-card-actions align="right">
                <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaModelo(modelo)"/>
                <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeModelo(modelo)"/>
              </q-card-actions>
            </div>
          </div>
        </q-card>
    <create-edit-form :close="close"
                      :codigo.sync="localModelo.codigo"
                      :createModelo="createModelo"
                      :designacao.sync="localModelo.designacao"
                      :listErrors="listErrors"
                      :marca.sync="marca"
                      :marcas="allMarcas"
                      :removeModelo="removeModelo"
                      :show_dialog="show_dialog"
                      :submitting="submitting"/>
      </div>

  </q-page>
</template>

<script>
import Modelo from 'src/store/models/modelo/modelo'
import Marca from 'src/store/models/marca/marca'
import TipoMeio from 'src/store/models/tipoMeio/tipoMeio'

export default {
  name: 'Modelo',
  data() {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      tab: 'distritos',
      tab1: 'comments',
      tab2: 'comments',
      splitterModel: 20,
      localModelo: {
        codigo: '',
        designacao: '',
        marca: {}
      },
      marca: {
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
    // this.$store.dispatch('modelo/getModelo', this.$route.params.id)
    // this.$store.dispatch('marca/getAllMarca')
    // this.$store.dispatch('marca/getMarca', this.modelo.marca.id)
    // Return a Promise if you are running an async job
    // Example:
    return Modelo.query().with('marca').find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    modelo: {
      get() {
        return Modelo.query().with('marca').find(this.$route.params.id)
      },
      set(modelo) {
        this.$emit('update:modelo', '')
        Modelo.update(modelo)
      }
    },
    allMarcas() {
      return Marca.query().all()
    },
  },
  components: {
    'create-edit-form': require('components/modelo/createEditForm.vue').default
  },
  methods: {
    removeModelo(modelo) {
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
          message: 'A informação foi Removida com successo! [ ' + modelo.designacao + ' ]'
        })
        Modelo.api().delete("/modelo/" + modelo.id)
        Modelo.delete(modelo.id)
        this.$router.go(-1)
      })
    },
    createModelo() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)
      this.localModelo.marca = this.marca
      this.localModelo.marca.tipoMeio = TipoMeio.query().find(this.marca.tipoMeio_id)
      this.localModelo.marca_id = this.marca.id
      Modelo.api().patch("/modelo/" + this.localModelo.id, this.localModelo).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.modelo.designacao + ' ]'
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
    editaModelo(modelo) {
      this.editedIndex = 0
      this.modelo = Object.assign({}, modelo)
      this.localModelo = Object.assign({}, modelo)
      this.marca = Marca.query().find(modelo.marca_id)
      this.show_dialog = true
    },
    close() {
      this.show_dialog = false
      this.props = this.modelo
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  filterFn(val, update, abort) {
    const stringOptions = this.allMarcas
    if (val === '') {
      update(() => {
        this.options = stringOptions.map(marca => marca)
      })
    } else if (stringOptions.length === 0) {
      update(() => {
        this.options = []
      })
    } else {
      update(() => {
        this.options = stringOptions
          .map(marca => marca)
          .filter(marca => {
            return marca &&
              marca.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
          })
      })
    }
  },
  abortFilterFn() {
    // console.log('delayed filter aborted')
  },
  setModel(val) {
    this.modelo.marca = val
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes da Modelo',
        basicInformation: 'Informacção do Modelo',
        codigo: 'Código',
        designacao: 'Designação',
        marca: 'Tipo de Meio',
        distritos: 'Distritos',
        comments: 'Comentários'
      },
      en: {
        title: 'Detalhes do Modelo',
        basicInformation: 'Informacção do Modelo',
        codigo: 'Código',
        designacao: 'Designação',
        marca: 'Tipo de Meio',
        distritos: 'Distritos',
        comments: 'Comentários'
      }
    }
  }
}
</script>
