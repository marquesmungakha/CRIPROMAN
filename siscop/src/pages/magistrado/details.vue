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
                <q-item-label caption lines="1">{{ $t('numero') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.numero }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('nome') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.nome }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('apelido') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.apelido }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('sexo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.sexo }}</q-item-label>
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
                <q-item-label caption lines="1">{{ $t('idade') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.idade }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('contacto1') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.telemovel1 }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('contacto2') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.telemovel2 }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('cargo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.cargoOcupado }}</q-item-label>
              </q-item-section>
            </q-item>
            <q-separator/>
            <q-item class="full-width">
              <q-item-section>
                <q-item-label caption lines="1">{{ $t('descricao_cargo') }}</q-item-label>
                <q-item-label class="text-grey-9">{{ magistrado.descricao }}</q-item-label>
              </q-item-section>
            </q-item>
          </div>
        </div>
      </div>
      </q-card-section>
      <div class="row">
        <div class="col">
          <q-card-actions align="left">
            <q-btn v-go-back=" '/magistrado' " class="glossy" color="primary" label="Voltar" no-caps/>
          </q-card-actions>
        </div>
        <div class="col">
          <q-card-actions align="right">
            <q-btn class="glossy" color="teal" label="Editar" no-caps @click.stop="editaMagistrado(magistrado)"/>
            <q-btn class="glossy" color="negative" label="Apagar" no-caps @click.stop="removeMagistrado(magistrado)"/>
          </q-card-actions>
        </div>
      </div>
    </q-card>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Editar Magistrado!</div>
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
            <q-form class="q-gutter-md" @submit.prevent="createMagistrado">
              <quadro :apelido.sync="localMagistrado.apelido"
                      :fotografia.sync="localMagistrado.fotografia"
                      :idade.sync="localMagistrado.idade"
                      :nome.sync="localMagistrado.nome"
                      :numero.sync="localMagistrado.numero"
                      :sexo.sync="localMagistrado.sexo"
                      :telemovel1.sync="localMagistrado.telemovel1"
                      :telemovel2.sync="localMagistrado.telemovel2"
                      :onFileChange.sync="onFileChange"
                      :image.sync="image"/>
              <create-edit-form :cargoOcupado.sync="localMagistrado.cargoOcupado"
                                :descricao.sync="localMagistrado.descricao"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createMagistrado"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import Magistrado from 'src/store/models/magistrado/magistrado'

export default {
  name: 'Magistrado',
  data() {
    return {
      listErrors: [],
      options: [],
      submitting: false,
      show_dialog: false,
      splitterModel: 20,
      image: '',
      localMagistrado: {
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
      }
    }
  },
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
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
    return Magistrado.query().find(currentRoute.params.id)
  },
  created() {
  },
  mounted() {
  },
  computed: {
    magistrado: {
      get() {
        return Magistrado.query().find(this.$route.params.id)
      },
      set(magistrado) {
        this.$emit('update:magistrado', '')
        Magistrado.update(magistrado)
      }
    },
    dataUrl(){
        return 'data:image/jpeg;base64,' + btoa(
            new Uint8Array(this.magistrado.fotografia)
            .reduce((data, byte) => data + String.fromCharCode(byte), '')
        );
    }
  },
  components: {
    'create-edit-form': require('components/magistrado/createEditForm.vue').default,
    quadro: require('components/quadro/createEditFields.vue').default
  },
  methods: {
    removeMagistrado(magistrado) {
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
        Magistrado.api().delete("/magistrado/" + magistrado.id)
        Magistrado.delete(magistrado.id)
        this.$router.go(-1)
      })
    },
    createMagistrado() {
      this.listErrors = []
      this.submitting = true
      //setTimeout(() => {
 //       this.submitting = false
 //     }, 300)

      let formData = new FormData();
      formData.append("nome", this.localMagistrado.nome);
      formData.append("apelido", this.localMagistrado.apelido);
      formData.append("sexo", this.localMagistrado.sexo);
      formData.append("idade", this.localMagistrado.idade);
      formData.append("telemovel2", this.localMagistrado.telemovel2);
      formData.append("telemovel1", this.localMagistrado.telemovel1);
      formData.append("fotografia", this.localMagistrado.fotografia);
      formData.append("descricao", this.localMagistrado.descricao);
      formData.append("numero", this.localMagistrado.numero);
      formData.append("cargoOcupado", this.localMagistrado.cargoOcupado);

      Magistrado.api().patch("/magistrado/" + this.localMagistrado.id, formData).then(resp => {
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
          message: 'A informação foi actualizada com successo!! [ ' + this.localMagistrado.numero + ' ]'
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
      this.localMagistrado.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    editaMagistrado(magistrado) {
      this.editedIndex = 0
      this.magistrado = Object.assign({}, magistrado)
      this.localMagistrado = Object.assign({}, magistrado)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(magistrado.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    close() {
      this.image = ''
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
        cargo: 'Cargo que ocupa',
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
        cargo: 'Cargo que ocupa',
        descricao_cargo: 'Descrição do Cargo',
        contacto1: 'Contacto Principal',
        contacto2: 'Contacto Alternativo'
      }
    }
  }
}
</script>
