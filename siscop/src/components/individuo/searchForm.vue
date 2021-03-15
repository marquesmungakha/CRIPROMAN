<template>
    <q-list>
   <form ref="myForm" @submit.prevent.stop="findIndividuo" class="q-gutter-md">
    <div class="row">
        <div class="col col-md-8">
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
                  ref="sexo"
                  label="Gênero *"
                  @input="$emit('update:sexo', $event)"
                   lazy-rules 
                  :rules="[ val => val && val.length > 0 || 'Introduza o Sexo']" />
            </q-item-section>
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
            <q-item-section>
                <q-select :value="tipoDocumento"
                          ref="tipoDocumento"
                          :options="tipoDocumentos"
                           option-label="designacao"
                           option-value="id"
                          label="Tipo de Documento de Identificação *"
                          @input="$emit('update:tipoDocumento', $event)"
                           lazy-rules />
            </q-item-section>
        </q-item>
        <q-item>
            <q-item-section>
                <q-input :value="numDocumentoIndentificacao"
                @input="$emit('update:numDocumentoIndentificacao', $event)"
                ref="numDocumentoIndentificacao"
                label="Número de Documento de Indentificação *"
                lazy-rules 
                :rules="[ val => val && val.length > 0 || 'Introduza o numero de Documento de Identificação']" />
            </q-item-section>
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
