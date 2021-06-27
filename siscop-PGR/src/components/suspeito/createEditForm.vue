<template>

  <q-list>
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
     </div>
     <div class="row">        
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Data Situação Prisional </div>
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
  </q-list>

</template>
<script>

export default {

  data () {
    return {
      optionsProfissao: [],
      optionsSituacaoPrisional: []
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
    },
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
    'profissao',
    'profissaoList',
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
