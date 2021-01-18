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
                      <q-item-label class="text-grey-9">{{ agente.numero }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('nome') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ agente.nome }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('apelido') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ agente.apelido }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                 <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('sexo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ agente.sexo }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('idade') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ agente.idade }}</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('contacto1') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ agente.telemovel1 }}</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('contacto2') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ agente.telemovel2 }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('cargo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ agente.cargoOcupado }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('descricao_cargo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ agente.descricao }}</q-item-label>
                  </q-item-section>
                </q-item>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaAgente(agente)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeAgente(agente)" no-caps/>
                </q-card-actions>
            </q-card>
            <div class="q-pa-sm q-gutter-sm">
                <q-dialog v-model="show_dialog" persistent>
                  <q-card style="width: 1100px; max-width: 90vw;">
                  <q-card-section>
                      <div class="text-h6">Editar Agente!</div>
                  </q-card-section>
                      <q-card-section>
                      <li v-for="item in listErrors" :key="item">
                      {{ item }}
                      </li>
                      </q-card-section>
                      <q-separator />
                      <q-card-section style="max-height: 70vh" class="scroll">
                              <q-form @submit.prevent="createAgente" class="q-gutter-md">
                                  <quadro :nome.sync="localAgente.nome"
                                          :sexo.sync="localAgente.sexo"
                                          :telemovel2.sync="localAgente.telemovel2"
                                          :apelido.sync="localAgente.apelido"
                                          :telemovel1.sync="localAgente.telemovel1"
                                          :fotografia.sync="localAgente.fotografia"
                                          :idade.sync="localAgente.idade"
                                          :numero.sync="localAgente.numero"/>
                                  <create-edit-form :descricao.sync="localAgente.descricao"
                                                    :cargoOcupado.sync="localAgente.cargoOcupado"/>
                                </q-form>
                      </q-card-section>
                      <q-separator />
                  <q-card-actions align="right">
                      <q-btn type="submit" :loading="submitting" @click.stop="createAgente" color="teal" label="Gravar" />
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
  name: 'Agente',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      localAgente: {
        nome: '',
        sexo: '',
        idade: '',
        telemovel2: '',
        apelido: '',
        telemovel1: '',
        fotografia: '',
        class: 'org.devinthesky.quadro.Agente',
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
    // this.$store.dispatch('agente/getAgente', this.$route.params.id)
    // this.$store.dispatch('tipoAgente/getAllTipoAgente')
    // this.$store.dispatch('tipoAgente/getTipoAgente', this.agente.tipoAgente.id)
    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('agente/getAgente', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    agente: {
      get () {
        return this.$store.getters['agente/agente']
      },
      set (agente) {
        this.SET_UPDATE_AGENTE({ agente })
        this.$emit('update:agente', '')
        this.$store.commit('agente/SET_UPDATE_AGENTE', agente)
      }
    },
    allTipoAgentes () {
      return this.$store.getters['tipoAgente/allTipoAgente']
    }
  },
  components: {
    'create-edit-form': require('components/agente/createEditForm.vue').default,
    quadro: require('components/quadro/createEditFields.vue').default
  },
  methods: {
    ...mapActions('agente', ['getAllAgente', 'getAgente', 'addNewAgente', 'updateAgente', 'deleteAgente']),
    ...mapMutations('agente', ['SET_UPDATE_AGENTE']),
    removeAgente (agente) {
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
          message: 'A informação foi Removida com successo! [ ' + agente.numero + ' ]'
        })
        this.deleteAgente(agente)
        this.$router.go(-1)
      })
    },
    createAgente () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.updateAgente(this.localAgente).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.agente.numero + ' ]'
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
    editaAgente (agente) {
      this.editedIndex = 0
      this.agente = Object.assign({}, agente)
      this.localAgente = Object.assign({}, agente)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('agente/getAgente', this.$route.params.id)
      }
      this.show_dialog = false
      this.props = this.agente
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Agente',
        basicInformation: 'Informacção do Agente',
        numero: 'Número do Agente',
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
        title: 'Detalhes do Agente',
        basicInformation: 'Informacção do Agente',
        numero: 'Número do Agente',
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
