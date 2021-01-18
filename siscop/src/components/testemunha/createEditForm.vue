<template>

  <q-list>
      <q-item>
          <q-item-section>
            <q-select
              :value="profissao"
              use-input
              fill-input
              hide-selected
              label="Profissão *"
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
          </q-item-section>
      </q-item>
      <q-item>
        <q-item-section>
            <q-input :value="ocupacao"
            @input="$emit('update:ocupacao', $event)"
            ref="ocupacao"
            label="Ocupação "
            lazy-rules
              />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
                <q-input :value="depoimento"  type="textarea"  label="Depoimento *  " @input="$emit('update:depoimento', $event)"/>
          </q-item-section>
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
