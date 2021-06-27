<template>

  <q-list>
   <div class="row">
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Data da alocação *</div>
          <q-input outlined tack-label class="w-field" :dense="true" :value="data" @input="$emit('update:data', $event)" :rules="['####-##-##']">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                <q-date outlined tack-label class="w-field" :dense="true" :value="data" minimal mask="YYYY-MM-DD" @input="$emit('update:data', $event)">
                  <div class="row items-center justify-end">
                    <q-btn v-close-popup label="Close" color="primary" flat />
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
        </div>
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Descrição da Alocação *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="descricao"
              @input="$emit('update:descricao', $event)"
              ref="descricao"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Descrição da Alocação']" />
        </div>
   </div>

      <div class="row">
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Função *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="funcao"
              use-input
              fill-input
              hide-selected
              
              input-debounce="0"
              :options="options"
              option-label="designacao"
              option-value="id"
              @filter="filterFn"
              @input="$emit('update:funcao', $event)"
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
          <div class="col-4 text-left">Membro *</div>
      
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="quadro"
              use-input
              fill-input
              hide-selected
              
              input-debounce="0"
              :options="optionsQuadro"
              option-label="numero"
              option-value="id"
              @filter="filterFnQuadro"
              @input="$emit('update:quadro', $event)"
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

  <div class="row">
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Activo</div>
           <q-checkbox indeterminate-value="maybe" outlined tack-label class="w-field" :dense="true" :value="activo"  @input="$emit('update:activo', $event)"/>
        </div>
  </div>
  </q-list>

</template>
<script>

export default {

  data () {
    return {
      options: [],
      optionsQuadro: []
    }
  },
  computed: {
  },
  components: {
  },
  methods: {
    filterFn (val, update, abort) {
      const stringOptions = this.funcoes
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(funcao => funcao)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(funcao => funcao)
            .filter(funcao => {
              return funcao &&
                   funcao.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnQuadro (val, update, abort) {
      const stringOptions = this.quadros
      if (val === '') {
        update(() => {
          this.optionsQuadro = stringOptions.map(quadro => quadro)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsQuadro = []
        })
      } else {
        update(() => {
          this.optionsQuadro = stringOptions
            .map(quadro => quadro)
            .filter(quadro => {
              return quadro &&
                   quadro.numero.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    }
  },
  props: [
    'data',
    'descricao',
    'funcao',
    'quadro',
    'activo',
    'funcoes',
    'quadros'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
