<template>
  <q-list>
   <div class="row">
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Situação Prisional *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="situacaoPrisional"
              use-input
              fill-input
              hide-selected
              
              input-debounce="0"
              :options="optionsSituacaoPrisional"
              option-label="designacao"
              option-value="id"
              @filter="filterFnSituacaoPrisional"
              @input="$emit('update:situacaoPrisional', $event)"
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
          <div class="col-4 text-left">Data Situação Prisional *</div>
     
            <q-input outlined tack-label class="w-field" :dense="true" :value="dataSituacaoPrisional"   @input="$emit('update:dataSituacaoPrisional', $event)" :rules="['####-##-##']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date outlined tack-label class="w-field" :dense="true" :value="dataSituacaoPrisional" minimal mask="YYYY-MM-DD" @input="$emit('update:dataSituacaoPrisional', $event)">
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
          <div class="col-4 text-left">Declaração *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="declaracao"  type="textarea"   @input="$emit('update:declaracao', $event)"/>
        </div>
     </div>
      
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
  </q-list>
</template>
<script>

export default {

  data () {
    return {
     optionsSituacaoPrisional: []
    }
  },
  computed: {
  },
  components: {
  },
  methods: {
    filterFnSituacaoPrisional (val, update, abort) {
      const stringOptions = this.situacaoPrisionalList
      if (val === '') {
        update(() => {
          this.optionsSituacaoPrisional = stringOptions.map(situacaoPrisional => situacaoPrisional)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsSituacaoPrisional = []
        })
      } else {
        update(() => {
          this.optionsSituacaoPrisional = stringOptions
            .map(situacaoPrisional => situacaoPrisional)
            .filter(situacaoPrisional => {
              return situacaoPrisional &&
                   situacaoPrisional.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    }
  },
  
  props: [
    'declaracao',
    'localTrabalho',
    'ocupacao',
    'situacaoPrisional',
    'situacaoPrisionalList',
    'dataSituacaoPrisional'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
