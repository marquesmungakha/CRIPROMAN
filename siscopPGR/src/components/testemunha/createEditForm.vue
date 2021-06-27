<template>

  <q-list>
  <q-item>
    <div class="row">        
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Profissão *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="profissao"
              use-input
              fill-input
              hide-selected
              
              input-debounce="0"
              :options="optionsProfissao"
              option-label="designacao"
              option-value="id"
              @filter="filterFnProfissao"
              @input="$emit('update:profissao', $event)"
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
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Ocupação</div>
            <q-input outlined tack-label class="w-field" :dense="true" :value="ocupacao"
            @input="$emit('update:ocupacao', $event)"
            ref="ocupacao"
            
            lazy-rules
              />
        </div>
    </div>
  </q-item>
  <q-item>
      <div class="row">        
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Cargo Ocupado *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="depoimento"  type="textarea"  label="Depoimento *  " @input="$emit('update:depoimento', $event)"/>
        </div>
      </div>
  </q-item>
  </q-list>

</template>
<script>

export default {

  data () {
    return {
      optionsProfissao: []
    }
  },
  computed: {
  },
  components: {
  },
  methods: {
    filterFnProfissao (val, update, abort) {
      const stringOptions = this.profissaoList
      if (val === '') {
        update(() => {
          this.optionsProfissao = stringOptions.map(profissao => profissao)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsProfissao = []
        })
      } else {
        update(() => {
          this.optionsProfissao = stringOptions
            .map(profissao => profissao)
            .filter(profissao => {
              return profissao &&
                   profissao.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    }
  },
  props: [
    'profissao',
    'profissaoList',
    'depoimento',
    'ocupacao'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
