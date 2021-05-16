<template>
<q-list>
    <q-item>
          <q-item-section>
              <q-input :value="numero"
              @input="$emit('update:numero', $event)"
              ref="numero"
              label="Número do Auto*"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Número do Auto']" />
          </q-item-section>
      </q-item>
      <q-item>
        <q-item-section>
            <q-input :value="dataAbertura"  label="Data de Abertura " @input="$emit('update:dataAbertura', $event)" :rules="['####-##-##']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date :value="dataAbertura" minimal mask="YYYY-MM-DD" @input="$emit('update:dataAbertura', $event)">
                    <div class="row items-center justify-end">
                      <q-btn v-close-popup label="Close" color="primary" flat />
                    </div>
                  </q-date>
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
        </q-item-section>
        </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="nome"
              @input="$emit('update:nome', $event)"
              ref="nome"
              label="Nome *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Nome']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="apelido"
              @input="$emit('update:apelido', $event)"
              ref="apelido"
              label="Apelido *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Apelido']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
                <q-select :value="sexo"
                :options="optionsGenero"
                label="Gênero *"
                @input="$emit('update:sexo', $event)"/>
          </q-item-section>
      </q-item>
        <q-item>
          <q-item-section>
              <q-input :value="idade"
              @input="$emit('update:idade', $event)"
              ref="idade"
              label="Idade *"
              type="number"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Idade']" />
          </q-item-section>
      </q-item>
      <q-item>
            <q-item-section>
                <q-input :value="telemovel1"
                @input="$emit('update:telemovel1', $event)"
                ref="telemovel1"
                label="Telefone Principal *"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza o Telefone Principal']" />
            </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="telemovel2"
              @input="$emit('update:telemovel2', $event)"
              ref="telemovel2"
              label="Telefone Alternativo *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Telefone Alternativo']" />
          </q-item-section>
      </q-item>
</q-list>
</template>
<script>

export default {
  data () {
    return {
      msg: 'Vue Image Upload and Resize Demo',
      hasImage: false,
      image: null,
      optionsGenero: ['Masculino', 'Femenino'],
      errorDialog: null,
      errorText: '',
      uploadFieldName: 'file',
      maxSize: 1024
    }
  },
  computed: {
  },
  methods: {
    handleFileUpload (event) {
      this.$emit('update:fotografia', event.target.files[0])
    },
    setImage: function (output) {
      this.hasImage = true
      this.image = output
      this.fotografia = output
      this.$emit('update:fotografia', output)
      console.log('Info', output.info)
      console.log('Exif', output.exif)
    }
  },
  props: [
    'nome',
    'sexo',
    'idade',
    'telemovel2',
    'apelido',
    'telemovel1',
    'fotografia',
    'numero',
    'close',
    'submitting'
  ]
}
</script>
