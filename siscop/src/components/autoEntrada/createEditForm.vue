<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar Auto de Entrada!</div>
        </q-card-section>
                <q-card-section>
          <div v-if="listErrors.length > 0" class="q-pa-sm q-gutter-sm" style="max-width: 550px; max-height: 150px;border-radius: 10px; border: 1px solid #cb4646; margin: 5px; background-color: #ead8da">
            <ul class="list-group alert alert-danger">
              <li class="list-group-item text-negative q-pl-xs text-weight-regular text-caption"
                  v-for="item in listErrors" :key="item">
                {{ item }}
              </li>
            </ul>
          </div>
        </q-card-section>
        <q-separator />
        <q-card-section style="max-height: 50vh" class="scroll">
                <q-form @submit.prevent="createAutoEntrada" class="q-gutter-md">
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
                              :value="tipoAuto"
                              use-input
                              fill-input
                              hide-selected
                              label="Tipo de Auto *"
                              input-debounce="0"
                              :options="optionsTipoAutos"
                              option-label="designacao"
                              option-value="id"
                              @filter="filterFnTipoAuto"
                              @input="$emit('update:tipoAuto', $event)"
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
                              :value="classeJudicial"
                              use-input
                              fill-input
                              hide-selected
                              label="Família Delitiva  *"
                              input-debounce="0"
                              :options="optionsClasseJudicials"
                              option-label="designacao"
                              option-value="id"
                              @filter="filterFnFamiliaDelitiva"
                              @input="$emit('update:classeJudicial', $event)"
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
                              :value="crime"
                              use-input
                              fill-input
                              hide-selected
                              label="Tipo Legal de Crime * "
                              input-debounce="0"
                              :options="optionsCrime"
                              option-label="designacao"
                              option-value="id"
                              @filter="filterFnCrime"
                              @input="$emit('update:crime', $event)"
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
                                 <q-input :value="modusOperandi"  type="textarea"  label="Modus Operandi *  " @input="$emit('update:modusOperandi', $event)"/>
                            </q-item-section>
                        </q-item>
                        <q-separator/>
                        <q-item>
                            <q-item-section>
                                 <q-input :value="infraccao"  type="textarea"  label="Descrição da Infracção *  " @input="$emit('update:infraccao', $event)"/>
                            </q-item-section>
                        </q-item>
                        <q-item>
                            <q-item-section>
                                 <q-input :value="endereco"  type="textarea"  label="Descrição do Local da Infracção *  " @input="$emit('update:endereco', $event)"/>
                            </q-item-section>
                        </q-item>
                        <q-item>
                            <q-item-section>
                              <q-input :value="horaOcorrencia" @input="$emit('update:horaOcorrencia', $event)">
                                <template v-slot:prepend>
                                  <q-icon name="event" class="cursor-pointer">
                                    <q-popup-proxy transition-show="scale" transition-hide="scale">
                                      <q-date :value="horaOcorrencia" minimal mask="YYYY-MM-DD HH:mm" @input="$emit('update:horaOcorrencia', $event)">
                                        <div class="row items-center justify-end">
                                          <q-btn v-close-popup label="Close" color="primary" flat />
                                        </div>
                                      </q-date>
                                    </q-popup-proxy>
                                  </q-icon>
                                </template>

                                <template v-slot:append>
                                  <q-icon name="access_time" class="cursor-pointer">
                                    <q-popup-proxy transition-show="scale" transition-hide="scale">
                                      <q-time :value="horaOcorrencia" mask="YYYY-MM-DD HH:mm" format24h @input="$emit('update:horaOcorrencia', $event)">
                                        <div class="row items-center justify-end">
                                          <q-btn v-close-popup label="Close" color="primary" flat />
                                        </div>
                                      </q-time>
                                    </q-popup-proxy>
                                  </q-icon>
                                </template>
                              </q-input>
                          </q-item-section>
                        </q-item>
                        <q-item>
                            <q-item-section>
                                <q-input :value="responsavelLocal"
                                @input="$emit('update:responsavelLocal', $event)"
                                ref="responsavelLocal"
                                label="Responsável do Local " />
                            </q-item-section>
                        </q-item>
                         <q-item>
                            <q-item-section>
                                <q-input :value="contacto"
                                @input="$emit('update:contacto', $event)"
                                ref="contacto"
                                label="Contacto do Responsável "/>
                            </q-item-section>
                        </q-item>
                        <q-item>
                            <q-item-section>
                            <q-select
                              :value="inspector"
                              use-input
                              fill-input
                              hide-selected
                              label="Inspector Responsável *"
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
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createAutoEntrada" color="teal" label="Gravar" />
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
      optionsTipoAutos: [],
      optionsClasseJudicials: [],
      optionsCrime: [],
      optionsInspector: []
    }
  },
  computed: {
  },
  methods: {
    filterFnTipoAuto (val, update, abort) {
      const stringOptions = this.tipoAutos
      if (val === '') {
        update(() => {
          this.optionsTipoAutos = stringOptions.map(tipoAuto => tipoAuto)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsTipoAutos = []
        })
      } else {
        update(() => {
          this.optionsTipoAutos = stringOptions
            .map(tipoAuto => tipoAuto)
            .filter(tipoAuto => {
              return tipoAuto &&
                   tipoAuto.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnFamiliaDelitiva (val, update, abort) {
      const stringOptions = this.classeJudicials
      if (val === '') {
        update(() => {
          this.optionsClasseJudicials = stringOptions.map(classeJudicial => classeJudicial)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsclasseJudicial = []
        })
      } else {
        update(() => {
          this.optionsClasseJudicials = stringOptions
            .map(classeJudicial => classeJudicial)
            .filter(classeJudicial => {
              return classeJudicial &&
                   classeJudicial.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnCrime (val, update, abort) {
      const stringOptions = this.crimes
      if (val === '') {
        update(() => {
          this.optionsCrime = stringOptions.map(crime => crime)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsCrime = []
        })
      } else {
        update(() => {
          this.optionsCrime = stringOptions
            .map(crime => crime)
            .filter(crime => {
              return crime &&
                   crime.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
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
      'tipoAuto',
      'classeJudicial',
      'crime',
      'modusOperandi',
      'horaOcorrencia',
      'infraccao',
      'endereco',
      'responsavelLocal',
      'contacto',
      'inspector',
      'anexo',
      'inspectors',
      'crimes',
      'classeJudicials',
      'tipoAutos',
      'close',
      'submitting',
      'createAutoEntrada'
    ]

}
</script>
