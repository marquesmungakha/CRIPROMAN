<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar Auto de Acarreação!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
        <q-separator />
        <q-card-section style="max-height: 50vh" class="scroll">
                <q-form @submit.prevent="createAcarreacao" class="q-gutter-md">
                    <q-list>
                        <q-item>
                            <q-item-section>
                                <q-input :value="numero"
                                @input="$emit('update:numero', $event)"
                                ref="numero"
                                label="Número do Auto *"
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza o Número do Auto']" />
                            </q-item-section>
                        </q-item>
                         <q-item>
                            <q-item-section>
                                <q-input :value="dataAbertura"  label="Data de Abertura " @input="$emit('update:dataAbertura', $event)" :rules="['####-##-##']">
                                <template v-slot:append>
                                  <q-icon name="event" class="cursor-pointer">
                                    <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                                      <q-date :value="dataAbertura" minimal mask="YYYY-MM-DD" @input="$emit('update:dataAbertura', $event)">
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
                                 <q-input :value="descricao"  type="textarea"  label="Descrição do Auto  " @input="$emit('update:descricao', $event)"/>
                            </q-item-section>
                        </q-item>
                        <q-item>
                            <q-item-section>
                            <q-select
                              :value="inspector"
                              use-input
                              fill-input
                              hide-selected
                              label="Inspector Responsável "
                              input-debounce="0"
                              :options="optionsInspector"
                              option-label="numero"
                              option-value="id"
                              @filter="filterFnInspector"
                              @input="$emit('update:inspector', $event)"
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
                            <q-file color="primary" :value="anexo" label="Label"  @input="$emit('update:anexo', $event)">
                              <template v-slot:prepend>
                                <q-icon name="attach_file" />
                              </template>
                            </q-file>
                          </q-item-section>
                        </q-item>
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createAcarreacao" color="teal" label="Gravar" />
            <q-btn label="Cancelar" type="reset" @click="close" color="negative" v-close-popup />
        </q-card-actions>
        </q-card>
    </q-dialog>
   </div>
</template>
<script>

export default {

  data () {
    return {
      optionsInspector: []
    }
  },
  computed: {
  },
  methods: {
    filterFnInspector (val, update, abort) {
      const stringOptions = this.inspectors
      if (val === '') {
        update(() => {
          this.optionsInspector = stringOptions.map(inspector => inspector)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsInspector = []
        })
      } else {
        update(() => {
          this.optionsInspector = stringOptions
            .map(inspector => inspector)
            .filter(inspector => {
              return inspector &&
                   inspector.numero.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.distrito = val
    }
  },
  props:
    [
      'show_dialog',
      'listErrors',
      'numero',
      'dataAbertura',
      'descricao',
      'inspector',
      'anexo',
      'inspectors',
      'close',
      'submitting',
      'createAcarreacao'
    ]

}
</script>
