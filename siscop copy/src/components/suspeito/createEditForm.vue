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
            <q-select
              :value="situacaoPrisional"
              use-input
              fill-input
              hide-selected
              label="Situação Prisional *"
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
          </q-item-section>
      </q-item>
      <q-item>
            <q-item-section>
            <q-input :value="dataSituacaoPrisional"  label="Data Situação Prisional " @input="$emit('update:dataSituacaoPrisional', $event)" :rules="['####-##-##']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date :value="dataSituacaoPrisional" minimal mask="YYYY-MM-DD" @input="$emit('update:dataSituacaoPrisional', $event)">
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
