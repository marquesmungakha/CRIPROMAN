<template>
<q-list>
    <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Cargo Ocupado *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="numero"
              @input="$emit('update:numero', $event)"
              ref="numero"
              label="Número do Auto*"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Número do Auto']" />
        </div>        
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Data de Abertura *</div>
            <q-input outlined tack-label class="w-field" :dense="true" :value="dataAbertura"   @input="$emit('update:dataAbertura', $event)" :rules="['####-##-##']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date outlined tack-label class="w-field" :dense="true" :value="dataAbertura" minimal mask="YYYY-MM-DD" @input="$emit('update:dataAbertura', $event)">
                    <div class="row items-center justify-end">
                      <q-btn v-close-popup label="Close" color="primary" flat />
                    </div>
                  </q-date>
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
        </div>
    </div>
       <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Nome *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="nome"
              @input="$emit('update:nome', $event)"
              ref="nome"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Nome']" />
        </div>            
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Apelido *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="apelido"
              @input="$emit('update:apelido', $event)"
              ref="apelido"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Apelido']" />
        </div>
       </div>

      <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Gênero *</div>
                <q-select outlined tack-label class="w-field" :dense="true" :value="sexo"
                :options="optionsGenero"
                
                @input="$emit('update:sexo', $event)"/>
        </div>            
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Idade *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="idade"
              @input="$emit('update:idade', $event)"
              ref="idade"
              
              type="number"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Idade']" />
        </div>
      </div>
      <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Telefone Principal *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="telemovel1"
                @input="$emit('update:telemovel1', $event)"
                ref="telemovel1"
                
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza o Telefone Principal']" />
        </div>            
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Telefone Alternativo *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="telemovel2"
              @input="$emit('update:telemovel2', $event)"
              ref="telemovel2"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Telefone Alternativo']" />
        </div>
      </div>
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
<style scoped>
.w-field{
  width: 300px
}
</style>
