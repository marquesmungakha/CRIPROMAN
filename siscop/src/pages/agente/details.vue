<template>
  <q-page>
    <q-card bordered class="my-card" flat>
      <q-card-section class="bg-secondary text-white">
        <div class="text-h6">{{ $t('basicInformation') }}</div>
      </q-card-section>
      <q-separator/>
      <q-card-section class="bg-white text-grey">
      <div class="q-pa-md q-gutter-sm">

      <div class="row">
        <div class="col col-md-8">
        <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Número') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.numero }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Nome') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.nome }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Apelido') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.apelido }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Sexo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.sexo }}</q-item-label>
              </q-item-section>
            </q-item>
        </div>
        <div class="col-6 col-md-4">
            <q-item class="full-width">
              <q-item-section>
              <q-avatar size="205px" rounded>
                <img :src="dataUrl">
              </q-avatar>
              </q-item-section>
            </q-item>
        </div>
      </div>
        <div class="row">
          <div class="col-12">
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Idade') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.idade }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Contacto Principal') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.telemovel1 }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Contacto Alternativo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.telemovel2 }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Cargo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.cargoOcupado }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('Descrição do Cargo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ agente.descricao }}</q-item-label>
              </q-item-section>
            </q-item>
          </div>
        </div>
      </div>
      </q-card-section>
      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn v-go-back=" '/agente' " class="glossy" color="primary" label="Voltar" no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaAgente(agente)"/>
            <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeAgente(agente)"/>
          </q-card-actions>
        </div>
      </div>
    </q-card>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Editar Agente!</div>
          </q-card-section>
          <q-card-section>
            <div v-if="listErrors.length > 0" class="q-pa-sm q-gutter-sm"
                 style="max-width: 550px; max-height: 150px;border-radius: 10px; border: 1px solid #cb4646; margin: 5px; background-color: #ead8da">
              <ul class="list-group alert alert-danger">
                <li v-for="item in listErrors"
                    :key="item" class="list-group-item text-negative q-pl-xs text-weight-regular text-caption">
                  {{ item }}
                </li>
              </ul>
            </div>
          </q-card-section>
          <q-separator/>
          <q-card-section class="scroll" style="max-height: 80vh">
            <q-form class="q-gutter-md" @submit.prevent="createAgente">
              <quadro :apelido.sync="localAgente.apelido"
                      :fotografia.sync="localAgente.fotografia"
                      :idade.sync="localAgente.idade"
                      :nome.sync="localAgente.nome"
                      :numero.sync="localAgente.numero"
                      :sexo.sync="localAgente.sexo"
                      :telemovel1.sync="localAgente.telemovel1"
                      :telemovel2.sync="localAgente.telemovel2"
                      :onFileChange.sync="onFileChange"
                      :image.sync="image"/>
              <create-edit-form :cargoOcupado.sync="localAgente.cargoOcupado"
                                :descricao.sync="localAgente.descricao"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createAgente"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import Agente from 'src/store/models/agente/agente'

export default {
  name: 'Agente',
  data() {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      image: '',
      localAgente: {
        nome: '',
        sexo: '',
        idade: '',
        telemovel2: '',
        apelido: '',
        telemovel1: '',
        fotografia: '',
        descricao: '',
        numero: '',
        cargoOcupado: ''
      },
      info:{
        image: ''
      }
    }
  },
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
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
    return Agente.query().find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    agente: {
      get() {
        return Agente.query().find(this.$route.params.id)
      },
      set(agente) {
        this.$emit('update:agente', '')
        Agente.update(agente)
      }
    },
    dataUrl(){
        return 'data:image/jpeg;base64,' + btoa(
            new Uint8Array(this.agente.fotografia)
            .reduce((data, byte) => data + String.fromCharCode(byte), '')
        );
    }
  },
  components: {
    'create-edit-form': require('components/agente/createEditForm.vue').default,
    quadro: require('components/quadro/createEditFields.vue').default
  },
  methods: {
    removeAgente(agente) {
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
        Agente.api().delete("/agente/" + agente.id)
        Agente.delete(agente.id)
        this.$router.go(-1)
      })
    },
    createAgente() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)

      let formData = new FormData();
      formData.append("nome", this.localAgente.nome);
      formData.append("apelido", this.localAgente.apelido);
      formData.append("sexo", this.localAgente.sexo);
      formData.append("idade", this.localAgente.idade);
      formData.append("telemovel2", this.localAgente.telemovel2);
      formData.append("telemovel1", this.localAgente.telemovel1);
      formData.append("fotografia", this.localAgente.fotografia);
      formData.append("descricao", this.localAgente.descricao);
      formData.append("numero", this.localAgente.numero);
      formData.append("cargoOcupado", this.localAgente.cargoOcupado);

      Agente.api().patch("/agente/" + this.localAgente.id, formData).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localAgente.numero + ' ]'
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
    onFileChange(event){
      this.agente.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    editaAgente(agente) {
      this.editedIndex = 0
      this.agente = Object.assign({}, agente)
      this.localAgente = Object.assign({}, agente)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(agente.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    close() {
      this.image = ''
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
