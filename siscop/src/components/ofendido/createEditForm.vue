<template>

  <q-list>
    <q-item>
        <q-item-section>
            <q-input :value="localTrabalho"
            @input="$emit('update:localTrabalho', $event)"
            ref="localTrabalho"
            label="Local de trabalho "
            lazy-rules
              />
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
                <q-input :value="descricaoOcorrencia"  type="textarea"  label="Descrição da Ocorrência *  " @input="$emit('update:descricaoOcorrencia', $event)"/>
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
            <q-select
              :value="qualidadeDe"
              use-input
              fill-input
              hide-selected
              label="Na qualidade De? *"
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
          </q-item-section>
      </q-item>
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
