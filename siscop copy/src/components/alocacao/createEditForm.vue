<template>

  <q-list>
    <q-item>
      <q-item-section>
          <q-input :value="data"  label="Data da alocação " @input="$emit('update:data', $event)" :rules="['####-##-##']">
          <template v-slot:append>
            <q-icon name="event" class="cursor-pointer">
              <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                <q-date :value="data" minimal mask="YYYY-MM-DD" @input="$emit('update:data', $event)">
                  <div class="row items-center justify-end">
                    <q-btn v-close-popup label="Close" color="primary" flat />
                  </div>
                </q-date>
              </q-popup-proxy>
            </q-icon>
          </template>
        </q-input>
      </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="descricao"
              @input="$emit('update:descricao', $event)"
              ref="descricao"
              label="Descrição da Alocação *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Descrição da Alocação']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
            <q-select
              :value="funcao"
              use-input
              fill-input
              hide-selected
              label="Função *"
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
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
            <q-select
              :value="quadro"
              use-input
              fill-input
              hide-selected
              label="Membro *"
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
          </q-item-section>
      </q-item>

      <q-item>
          <q-item-section>
           <q-checkbox indeterminate-value="maybe" :value="activo" label="Activo" @input="$emit('update:descricao', $event)"/>
          </q-item-section>
      </q-item>
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
