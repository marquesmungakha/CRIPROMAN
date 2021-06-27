<template>

  <q-list>
  <q-item>
      <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Tipo de Meio Utilizado *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="tipoMeio"
              use-input
              fill-input
              hide-selected
              
              input-debounce="0"
              :options="optionsTipoMeio"
              option-label="designacao"
              option-value="id"
              @filter="filterFnTipoMeio"
              @input="$emit('update:tipoMeio', $event)"
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
          <div class="col-4 text-left">Marca *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="marca"
              use-input
              fill-input
              hide-selected
              
              input-debounce="0"
              :options="optionsMarca"
              option-label="designacao"
              option-value="id"
              @filter="filterFnMarca"
              @input="$emit('update:marca', $event)"
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
  </q-item>
  <q-item>
     <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Modelo *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="modelo"
              use-input
              fill-input
              hide-selected
              
              input-debounce="0"
              :options="optionsModelo"
              option-label="designacao"
              option-value="id"
              @filter="filterFnModelo"
              @input="$emit('update:modelo', $event)"
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
          <div class="col-4 text-left">Matrícula *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="matricula"
              @input="$emit('update:matricula', $event)"
              ref="matricula"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Matrícula']" />
        </div>
     </div>
  </q-item>
  <q-item>
       <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Número do Motor </div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="numeroMotor"
              @input="$emit('update:numeroMotor', $event)"
              ref="numeroMotor"
              
              lazy-rules
              />
        </div>            
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Número do Chassi </div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="chassi"
              @input="$emit('update:chassi', $event)"
              ref="chassi"
              
              lazy-rules
              />
        </div>
       </div>
  </q-item>
  <q-item>
      <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Cor *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="cor"
              @input="$emit('update:cor', $event)"
              ref="cor"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Cor']" />
        </div>            
        <!--div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Calibre </div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="calibre"
              @input="$emit('update:calibre', $event)"
              ref="calibre"
              
              lazy-rules
             />
        </div-->
      </div>
  </q-item>
  <q-item>
         <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Designação *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="designacao"
              @input="$emit('update:designacao', $event)"
              ref="designacao"

              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Designação']" />
        </div>
         </div>
  </q-item>
  </q-list>

</template>
<script>

export default {

  data () {
    return {
      optionsTipoMeio: [],
      optionsMarca: [],
      optionsModelo: []
    }
  },
  computed: {
  },
  components: {
  },
  methods: {
    filterFnMarca (val, update, abort) {
      const stringOptions = this.marcaList
      if (val === '') {
        update(() => {
          this.optionsMarca = stringOptions.map(marca => marca)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsMarca = []
        })
      } else {
        update(() => {
          this.optionsMarca = stringOptions
            .map(marca => marca)
            .filter(marca => {
              return marca &&
                   marca.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnTipoMeio (val, update, abort) {
      const stringOptions = this.tipoMeioList
      if (val === '') {
        update(() => {
          this.optionsTipoMeio = stringOptions.map(tipoMeio => tipoMeio)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsTipoMeio = []
        })
      } else {
        update(() => {
          this.optionsTipoMeio = stringOptions
            .map(tipoMeio => tipoMeio)
            .filter(tipoMeio => {
              return tipoMeio &&
                   tipoMeio.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnModelo (val, update, abort) {
      const stringOptions = this.modeloList
      if (val === '') {
        update(() => {
          this.optionsModelo = stringOptions.map(modelo => modelo)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsModelo = []
        })
      } else {
        update(() => {
          this.optionsModelo = stringOptions
            .map(modelo => modelo)
            .filter(modelo => {
              return modelo &&
                   modelo.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    }
  },
  props: [
    'matricula',
    'cor',
    'numeroMotor',
    'chassi',
    'calibre',
    'designacao',
    'marca',
    'marcaList',
    'tipoMeio',
    'tipoMeioList',
    'modelo',
    'modeloList'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
