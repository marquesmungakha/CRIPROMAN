<template>

  <q-list>
      <q-item>
          <q-item-section>
            <q-select
              :value="tipoMeio"
              use-input
              fill-input
              hide-selected
              label="Tipo de Meio Utilizado *"
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
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
            <q-select
              :value="marca"
              use-input
              fill-input
              hide-selected
              label="Marca *"
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
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
            <q-select
              :value="modelo"
              use-input
              fill-input
              hide-selected
              label="Modelo *"
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
          </q-item-section>
      </q-item>
     <q-item>
          <q-item-section>
              <q-input :value="matricula"
              @input="$emit('update:matricula', $event)"
              ref="matricula"
              label="Matrícula *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Matrícula']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="cor"
              @input="$emit('update:cor', $event)"
              ref="cor"
              label="Cor *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Cor']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="calibre"
              @input="$emit('update:calibre', $event)"
              ref="calibre"
              label="Calibre *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Calibre']" />
          </q-item-section>
      </q-item>
        <q-item>
          <q-item-section>
              <q-input :value="designacao"
              @input="$emit('update:designacao', $event)"
              ref="designacao"
              label="Designação *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Designação']" />
          </q-item-section>
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
