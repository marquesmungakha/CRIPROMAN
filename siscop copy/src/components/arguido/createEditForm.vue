<template>
  <q-list>
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
      <q-item>
          <q-item-section>
                <q-input :value="declaracao"  type="textarea"  label="Declaração *  " @input="$emit('update:declaracao', $event)"/>
          </q-item-section>
      </q-item>
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
