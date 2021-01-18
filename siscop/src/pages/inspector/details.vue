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
                      <q-item-label lines="1" caption >{{ $t('numero') }}</q-item-label>
                      <q-item-label class="text-grey-9">{{ inspector.numero }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('nome') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ inspector.nome }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('apelido') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ inspector.apelido }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                 <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('sexo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ inspector.sexo }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('idade') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ inspector.idade }}</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('contacto1') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ inspector.telemovel1 }}</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('contacto2') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ inspector.telemovel2 }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('cargo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ inspector.cargoOcupado }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('descricao_cargo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ inspector.descricao }}</q-item-label>
                  </q-item-section>
                </q-item>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaInspector(inspector)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeInspector(inspector)" no-caps/>
                </q-card-actions>
            </q-card>
            <div class="q-pa-sm q-gutter-sm">
                <q-dialog v-model="show_dialog" persistent>
                  <q-card style="width: 1100px; max-width: 90vw;">
                  <q-card-section>
                      <div class="text-h6">Editar Inspector!</div>
                  </q-card-section>
                      <q-card-section>
                      <li v-for="item in listErrors" :key="item">
                      {{ item }}
                      </li>
                      </q-card-section>
                      <q-separator />
                      <q-card-section style="max-height: 70vh" class="scroll">
                              <q-form @submit.prevent="createInspector" class="q-gutter-md">
                                  <quadro :nome.sync="localInspector.nome"
                                          :sexo.sync="localInspector.sexo"
                                          :telemovel2.sync="localInspector.telemovel2"
                                          :apelido.sync="localInspector.apelido"
                                          :telemovel1.sync="localInspector.telemovel1"
                                          :fotografia.sync="localInspector.fotografia"
                                          :idade.sync="localInspector.idade"
                                          :numero.sync="localInspector.numero"/>
                                  <create-edit-form :descricao.sync="localInspector.descricao"
                                                    :cargoOcupado.sync="localInspector.cargoOcupado"/>
                                </q-form>
                      </q-card-section>
                      <q-separator />
                  <q-card-actions align="right">
                      <q-btn type="submit" :loading="submitting" @click.stop="createInspector" color="teal" label="Gravar" />
                      <q-btn label="Cancelar" type="reset" @click="close" color="negative" v-close-popup />
                  </q-card-actions>
                  </q-card>
              </q-dialog>
            </div>
  </q-page>
</template>

<script>
import { mapActions, mapMutations } from 'vuex'

export default {
  name: 'Inspector',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      localInspector: {
        nome: '',
        sexo: '',
        idade: '',
        telemovel2: '',
        apelido: '',
        telemovel1: '',
        fotografia: '',
        class: 'org.devinthesky.quadro.Inspector',
        descricao: '',
        numero: '',
        cargoOcupado: ''
      }
    }
  },
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.
    // this.$store.dispatch('inspector/getInspector', this.$route.params.id)
    // this.$store.dispatch('tipoInspector/getAllTipoInspector')
    // this.$store.dispatch('tipoInspector/getTipoInspector', this.inspector.tipoInspector.id)
    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('inspector/getInspector', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    inspector: {
      get () {
        return this.$store.getters['inspector/inspector']
      },
      set (inspector) {
        this.SET_UPDATE_INSPECTOR({ inspector })
        this.$emit('update:inspector', '')
        this.$store.commit('inspector/SET_UPDATE_INSPECTOR', inspector)
      }
    },
    allTipoInspectors () {
      return this.$store.getters['tipoInspector/allTipoInspector']
    }
  },
  components: {
    'create-edit-form': require('components/inspector/createEditForm.vue').default,
    quadro: require('components/quadro/createEditFields.vue').default
  },
  methods: {
    ...mapActions('inspector', ['getAllInspector', 'getInspector', 'addNewInspector', 'updateInspector', 'deleteInspector']),
    ...mapMutations('inspector', ['SET_UPDATE_INSPECTOR']),
    removeInspector (inspector) {
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
          message: 'A informação foi Removida com successo! [ ' + inspector.numero + ' ]'
        })
        this.deleteInspector(inspector)
        this.$router.go(-1)
      })
    },
    createInspector () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      console.log(this.localInspector.fotografia)
      this.updateInspector(this.localInspector).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.inspector.numero + ' ]'
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
    editaInspector (inspector) {
      this.editedIndex = 0
      this.inspector = Object.assign({}, inspector)
      this.localInspector = Object.assign({}, inspector)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('inspector/getInspector', this.$route.params.id)
      }
      this.show_dialog = false
      this.props = this.inspector
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Inspector',
        basicInformation: 'Informacção do Inspector',
        numero: 'Número do Inspector',
        nome: 'Nome',
        apelido: 'Apelido',
        sexo: 'Gênero',
        idade: 'Idade',
        cargo: 'Cargo qu ocupa',
        descricao_cargo: 'Descrição do Cargo',
        contacto1: 'Contacto Principal',
        contacto2: 'Contacto Alternativo'
      },
      en: {
        title: 'Detalhes do Inspector',
        basicInformation: 'Informacção do Inspector',
        numero: 'Número do Inspector',
        nome: 'Nome',
        apelido: 'Apelido',
        sexo: 'Gênero',
        idade: 'Idade',
        cargo: 'Cargo qu ocupa',
        descricao_cargo: 'Descrição do Cargo',
        contacto1: 'Contacto Principal',
        contacto2: 'Contacto Alternativo'
      }
    }
  }
}
</script>
