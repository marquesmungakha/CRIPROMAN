<template>

<q-list>
 <div class="row">
        <div class="col col-md-8">
       <div class="row">        
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Número de Identificação *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="numero"
              @input="$emit('update:numero', $event)"
              ref="numero"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Número de Identificação']" />
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
     </div>
     <div class="row">        
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Apelido *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="apelido"
              @input="$emit('update:apelido', $event)"
              ref="apelido"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Apelido']" />
        </div>
     </div>
        </div>

        <div class="col-6 col-md-4">
          <div class="text-overline text-center">
            <q-avatar size="205px" rounded>
              <img :src="image">
            </q-avatar>
            </div>
              <q-item>
                <q-item-section>
                  <q-input type="file" filled  outlined tack-label class="w-field" :dense="true" :value="fotografia" @change="onFileChange" stack-label label="Fotografia" @input="$emit('update:fotografia', $event)" >
                    <template v-slot:prepend>
                      <q-icon name="attach_file" @click.stop />
                    </template>
                    <template v-slot:append>
                      <q-icon name="close" @click.stop="fotografia = null" class="cursor-pointer" />
                    </template>
                  </q-input>
                </q-item-section>
          </q-item>
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
              id="fotografia"
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
    'submitting',
    'onFileChange',
    'image'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
