<template>

  <q-list>
    <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Local de trabalho </div>
            <q-input outlined tack-label class="w-field" :dense="true" :value="localTrabalho"
            @input="$emit('update:localTrabalho', $event)"
            ref="localTrabalho"
            
            lazy-rules
              />
        </div>            
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Ocupação</div>
            <q-input outlined tack-label class="w-field" :dense="true" :value="ocupacao"
            @input="$emit('update:ocupacao', $event)"
            ref="ocupacao"
            
            lazy-rules
              />
        </div>
    </div>
      <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Descrição da Ocorrência *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="descricaoOcorrencia"  type="textarea"   @input="$emit('update:descricaoOcorrencia', $event)"/>
        </div>          
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Na qualidade De? *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="qualidadeDe"
              use-input
              fill-input
              hide-selected
              
              input-debounce="0"
              :options="options"
              option-label="designacao"
              option-value="id"
              @filter="filterFn"
              @input="$emit('update:qualidadeDe', $event)"
              abort="abortFilterFn"
              hint="Autocompleta o Texto">
              <template v-slot:no-option>
                <q-item>
                  <q-item-section class="text-grey">
                    No results
                  </q-item-section>
                </q-item>
              </template>
            </q-select>
        </div>
      </div>
  </q-list>

</template>
<script>

export default {

  data () {
    return {
      options: ['Familiar', 'Amigo', 'Colega', 'Outro']
    }
  },
  computed: {
  },
  components: {
  },
  methods: {
    filterFn (val, update, abort) {
      const stringOptions = this.options
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(qualidadeDe => qualidadeDe)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(qualidadeDe => qualidadeDe)
            .filter(qualidadeDe => {
              return qualidadeDe &&
                   qualidadeDe.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    }
  },
  props: [
    'descricaoOcorrencia',
    'localTrabalho',
    'ocupacao',
    'qualidadeDe'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
