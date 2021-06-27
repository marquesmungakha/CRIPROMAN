<template>
    <q-list>
   <form ref="myForm" @submit.prevent.stop="findIndividuo" class="q-gutter-md">
    <div class="row">
        <div class="col col-md-8">
        <q-item>
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
        </q-item>
        <q-item>
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
        </q-item>
          <q-item>
         <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Gênero *</div>
                  <q-select outlined tack-label class="w-field" :dense="true" :value="sexo"
                  :options="optionsGenero"
                  ref="sexo"
                  @input="$emit('update:sexo', $event)"
                   lazy-rules 
                  :rules="[ val => val && val.length > 0 || 'Introduza o Sexo']" />
        </div>
         </div>
          </q-item>
         </div>
          <div class="col-6 col-md-4">
              <q-item>
                <q-item-section>
                   <q-btn
                        size="md"
                        class="q-px-xl q-py-xs"
                        color="primary"
                        label="Procurar" 
                        type="submit"/>
                </q-item-section>
          </q-item>
        </div>

      </div>
      <q-item>
       <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Tipo de Documento de Identificação *</div>
                <q-select outlined tack-label class="w-field" :dense="true" :value="tipoDocumento"
                          ref="tipoDocumento"
                          :options="tipoDocumentos"
                           option-label="designacao"
                           option-value="id"
                          @input="$emit('update:tipoDocumento', $event)"
                           lazy-rules />
        </div>  
       </div>   
       <div class="row">        
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Nº de Documento de Indentificação *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="numDocumentoIndentificacao"
                @input="$emit('update:numDocumentoIndentificacao', $event)"
                ref="numDocumentoIndentificacao"
                lazy-rules 
                :rules="[ val => val && val.length > 0 || 'Introduza o numero de Documento de Identificação']" />
        </div>
       </div>
      </q-item>
      </form>
    </q-list>
</template>
<script>

export default {

  data () {
    return {
      selected_file: '',
      optionsTipoDocumento: [],
      optionsGenero: ['Masculino', 'Femenino']
    }
  },
  computed: {
  },
  methods: {
  
  },
  props: [
    'nome',
    'sexo',
    'apelido',
    'numDocumentoIndentificacao',
    'tipoDocumento',
    'tipoDocumentos',
    'findIndividuo',
    ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
