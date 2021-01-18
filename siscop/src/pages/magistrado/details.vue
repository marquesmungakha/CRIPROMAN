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
                      <q-item-label class="text-grey-9">{{ magistrado.numero }}</q-item-label>
                    </q-item-section>
                  </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('nome') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ magistrado.nome }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('apelido') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ magistrado.apelido }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                 <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('sexo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ magistrado.sexo }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('idade') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ magistrado.idade }}</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('contacto1') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ magistrado.telemovel1 }}</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('contacto2') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ magistrado.telemovel2 }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('cargo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ magistrado.cargoOcupado }}</q-item-label>
                  </q-item-section>
                </q-item>
                <q-separator/>
                <q-item class="full-width">
                  <q-item-section>
                    <q-item-label lines="1" caption >{{ $t('descricao_cargo') }}</q-item-label>
                    <q-item-label class="text-grey-9">{{ magistrado.descricao }}</q-item-label>
                  </q-item-section>
                </q-item>
              </div>
            </div>
                </q-card-section>
                <q-card-actions align="right">
                    <q-btn class="glossy" label="Editar" color="teal" @click.stop="editaMagistrado(magistrado)" no-caps />
                    <q-btn class="glossy" label="Apagar" color="negative" @click.stop="removeMagistrado(magistrado)" no-caps/>
                </q-card-actions>
            </q-card>
            <div class="q-pa-sm q-gutter-sm">
                <q-dialog v-model="show_dialog" persistent>
                  <q-card style="width: 1100px; max-width: 90vw;">
                  <q-card-section>
                      <div class="text-h6">Editar Magistrado!</div>
                  </q-card-section>
                      <q-card-section>
                      <li v-for="item in listErrors" :key="item">
                      {{ item }}
                      </li>
                      </q-card-section>
                      <q-separator />
                      <q-card-section style="max-height: 70vh" class="scroll">
                              <q-form @submit.prevent="createMagistrado" class="q-gutter-md">
                                  <quadro :nome.sync="localMagistrado.nome"
                                          :sexo.sync="localMagistrado.sexo"
                                          :telemovel2.sync="localMagistrado.telemovel2"
                                          :apelido.sync="localMagistrado.apelido"
                                          :telemovel1.sync="localMagistrado.telemovel1"
                                          :fotografia.sync="localMagistrado.fotografia"
                                          :idade.sync="localMagistrado.idade"
                                          :numero.sync="localMagistrado.numero"/>
                                  <create-edit-form :descricao.sync="localMagistrado.descricao"
                                                    :cargoOcupado.sync="localMagistrado.cargoOcupado"/>
                                </q-form>
                      </q-card-section>
                      <q-separator />
                  <q-card-actions align="right">
                      <q-btn type="submit" :loading="submitting" @click.stop="createMagistrado" color="teal" label="Gravar" />
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
  name: 'Magistrado',
  data () {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      localMagistrado: {
        nome: '',
        sexo: '',
        idade: '',
        telemovel2: '',
        apelido: '',
        telemovel1: '',
        fotografia: '',
        class: 'org.devinthesky.quadro.Magistrado',
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
    // this.$store.dispatch('magistrado/getMagistrado', this.$route.params.id)
    // this.$store.dispatch('tipoMagistrado/getAllTipoMagistrado')
    // this.$store.dispatch('tipoMagistrado/getTipoMagistrado', this.magistrado.tipoMagistrado.id)
    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('magistrado/getMagistrado', currentRoute.params.id)
  },
  created () {
  },
  mounted () {
  },
  computed: {
    magistrado: {
      get () {
        return this.$store.getters['magistrado/magistrado']
      },
      set (magistrado) {
        this.SET_UPDATE_MAGISTRADO({ magistrado })
        this.$emit('update:magistrado', '')
        this.$store.commit('magistrado/SET_UPDATE_MAGISTRADO', magistrado)
      }
    },
    allTipoMagistrados () {
      return this.$store.getters['tipoMagistrado/allTipoMagistrado']
    }
  },
  components: {
    'create-edit-form': require('components/magistrado/createEditForm.vue').default,
    quadro: require('components/quadro/createEditFields.vue').default
  },
  methods: {
    ...mapActions('magistrado', ['getAllMagistrado', 'getMagistrado', 'addNewMagistrado', 'updateMagistrado', 'deleteMagistrado']),
    ...mapMutations('magistrado', ['SET_UPDATE_MAGISTRADO']),
    removeMagistrado (magistrado) {
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
          message: 'A informação foi Removida com successo! [ ' + magistrado.numero + ' ]'
        })
        this.deleteMagistrado(magistrado)
        this.$router.go(-1)
      })
    },
    createMagistrado () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.updateMagistrado(this.localMagistrado).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.magistrado.numero + ' ]'
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
    editaMagistrado (magistrado) {
      this.editedIndex = 0
      this.magistrado = Object.assign({}, magistrado)
      this.localMagistrado = Object.assign({}, magistrado)
      this.show_dialog = true
    },
    close () {
      if (this.$route.params.id !== null) {
        this.$store.dispatch('magistrado/getMagistrado', this.$route.params.id)
      }
      this.show_dialog = false
      this.props = this.magistrado
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    }
  },
  i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Magistrado',
        basicInformation: 'Informacção do Magistrado',
        numero: 'Número do Magistrado',
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
        title: 'Detalhes do Magistrado',
        basicInformation: 'Informacção do Magistrado',
        numero: 'Número do Magistrado',
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
