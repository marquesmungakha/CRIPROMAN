<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar Processo!</div>
        </q-card-section>
                <q-card-section>
          <div v-if="listErrors.length > 0" class="q-pa-sm q-gutter-sm" style="max-width: 650px; max-height: 150px;border-radius: 10px; border: 1px solid #cb4646; margin: 5px; background-color: #ead8da">
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
                <q-form @submit.prevent="createProcesso" class="q-gutter-md">
                    <q-list>
                    <div class="row">
                      <div class="row q-gutter-xs" style="width: 500px;">
                          <div class="col-4 text-left">Número do Processo *</div>
                             <q-input :value="numeroProcesso"
                                  @input="$emit('update:numeroProcesso', $event)"
                                  ref="numeroProcesso"
                                  lazy-rules
                                  outlined
                                  stack-label
                                  class="w-field"
                                  :dense="true"
                                  :rules="[ val => val && val.length > 0 || 'Introduza o Número do Processo']" >
                                  <!--template v-slot:before>
                                  <div class="text-subtitle1"> Número do Processo *</div>
                                  </template-->
                                  </q-input>
                        </div>
                    </div>

                       <div class="row">
                        <div class="row q-gutter-xs" style="width: 500px;">
                          <div class="col-4 text-left">Número do Auto *</div>
                            <q-select
                              :value="numeroAuto"
                              use-input
                              fill-input
                              hide-selected
                              outlined
                              stack-label
                              class="w-field"
                              :dense="true"
                              input-debounce="0"
                              :options="optionsAutoEntrada"
                              option-label="numero"
                              option-value="id"
                              @filter="filterFnAutoEntrada"
                              @input="$emit('update:numeroAuto', $event)"
                              abort="abortFilterFn"
                              hint="Autocompleta o Texto">
                              <template v-slot:no-option>
                                <q-item>
                                  <q-item-section class="text-grey">
                                    No results
                                  </q-item-section>
                                </q-item>
                              </template>
                               <!--template v-slot:before>
                                 <div class="text-subtitle1"> Número do Auto *</div>
                                </template-->
                            </q-select>
                          </div>
                      <div class="row q-gutter-xs" style="width: 500px;">
                        <div class="col-4 text-left">Data de Entrada *</div>
                                <q-input :value="dataEntrada"  
                                outlined
                                stack-label
                                class="w-field"
                                :dense="true"
                                @input="$emit('update:dataEntrada', $event)" 
                                :rules="['####-##-##']">
                                <!--template v-slot:before>
                                 <div class="text-subtitle1"> Data de Entrada *</div>
                                </template-->
                                <template v-slot:append>
                                  <q-icon name="event" class="cursor-pointer">
                                    <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                                      <q-date :value="dataEntrada" minimal mask="YYYY-MM-DD" @input="$emit('update:dataEntrada', $event)">
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
                          <div class="col-4 text-left">Proveniência *</div>
                                <q-input :value="proveniencia"
                                @input="$emit('update:proveniencia', $event)"
                                ref="proveniencia"
                                lazy-rules
                                outlined
                                stack-label
                                class="w-field"
                                :dense="true"
                                :rules="[ val => val && val.length > 0 || 'Introduza a Proveniência']" >
                                <!--template v-slot:before>
                                 <div class="text-subtitle1"> Proveniência *</div>
                                </template-->
                                </q-input>
                    </div>
                  <div class="row q-gutter-xs" style="width: 500px;">
                    <div class="col-4 text-left">Forma de Processo *</div>
                            <q-select
                              :value="formaProcesso"
                              use-input
                              fill-input
                              hide-selected
                              outlined
                              stack-label
                              class="w-field"
                              :dense="true"
                              input-debounce="0"
                              :options="optionsFormaProcesso"
                              option-label="designacao"
                              option-value="id"
                              @filter="filterFnFormaProcesso"
                              @input="$emit('update:formaProcesso', $event)"
                              abort="abortFilterFn"
                              hint="Autocompleta o Texto">
                              <template v-slot:no-option>
                                <q-item>
                                  <q-item-section class="text-grey">
                                    No results
                                  </q-item-section>
                                </q-item>
                              </template>
                              <!--template v-slot:before>
                                 <div class="text-subtitle1"> </div>
                                </template-->
                            </q-select>
                    </div>
                    </div>
                    <div class="row">  
                    <div class="row q-gutter-xs" style="width: 500px;">
                        <div class="col-4 text-left">Inspector Responsável *</div>
                            <q-select
                              :value="inspector"
                              use-input
                              fill-input
                              hide-selected
                              outlined
                              stack-label
                              class="w-field"
                              :dense="true"
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
                              <!--template v-slot:before>
                                 <div class="text-subtitle1"> Inspector Responsável *</div>
                                </template-->
                            </q-select>
                    </div>
                  <div class="row q-gutter-xs" style="width: 500px;">
                          <div class="col-4 text-left">Magistrado *</div>
                            <q-select
                              :value="magistrado"
                              use-input
                              fill-input
                              hide-selected
                              outlined
                              tack-label
                              class="w-field"
                              :dense="true"
                              input-debounce="0"
                              :options="optionsMagistrado"
                              option-label="numero"
                              option-value="id"
                              @filter="filterFnMagistrado"
                              @input="$emit('update:magistrado', $event)"
                              abort="abortFilterFn"
                              hint="Autocompleta o Texto">
                              <template v-slot:no-option>
                                <q-item>
                                  <q-item-section class="text-grey">
                                    No results
                                  </q-item-section>
                                </q-item>
                              </template>
                              <!--template v-slot:before>
                                 <div class="text-subtitle1"> Magistrado *</div>
                                </template-->
                            </q-select>
                        </div>
                    </div>
                    <div class="row">                         
                      <div class="row q-gutter-xs">
                        <div class="text-left">Processo com Autor? *</div>
                          <q-radio :value="autor" val="Conhecido" label="Conhecido" @input="$emit('update:autor', $event)"/>
                          <q-radio :value="autor" val="Desconhecido" label="Desconhecido" @input="$emit('update:autor', $event)"/>
                          <q-radio :value="autor" val="Esclarecido" label="Esclarecido" @input="$emit('update:autor', $event)"/>
                      </div>
                    </div>
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createProcesso" color="teal" label="Gravar" />
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
      optionsFormaProcesso: [],
      optionsAutoEntrada: [],
      optionsInspector: [],
      optionsMagistrado: []
    }
  },
  computed: {
  },
  methods: {
    filterFnFormaProcesso (val, update, abort) {
      const stringOptions = this.formaProcessos
      if (val === '') {
        update(() => {
          this.optionsFormaProcesso = stringOptions.map(formaProcesso => formaProcesso)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsFormaProcesso = []
        })
      } else {
        update(() => {
          this.optionsFormaProcesso = stringOptions
            .map(formaProcesso => formaProcesso)
            .filter(formaProcesso => {
              return formaProcesso &&
                   formaProcesso.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnAutoEntrada (val, update, abort) {
      const stringOptions = this.autoEntradas
      if (val === '') {
        update(() => {
          this.optionsAutoEntrada = stringOptions.map(autoEntrada => autoEntrada)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsAutoEntrada = []
        })
      } else {
        update(() => {
          this.optionsAutoEntrada = stringOptions
            .map(autoEntrada => autoEntrada)
            .filter(autoEntrada => {
              return autoEntrada &&
                   autoEntrada.numero.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnMagistrado (val, update, abort) {
      const stringOptions = this.magistrados
      if (val === '') {
        update(() => {
          this.optionsMagistrado = stringOptions.map(magistrado => magistrado)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsMagistrado = []
        })
      } else {
        update(() => {
          this.optionsMagistrado = stringOptions
            .map(magistrado => magistrado)
            .filter(magistrado => {
              return magistrado &&
                   magistrado.numero.toLowerCase().indexOf(val.toLowerCase()) !== -1
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
      'numeroProcesso',
      'numeroAuto',
      'proveniencia',
      'dataEntrada',
      'formaProcesso',
      'magistrado',
      'inspector',
      'anexo',
      'inspectors',
      'magistrados',
      'formaProcessos',
      'autoEntradas',
      'autor',
      'close',
      'submitting',
      'createProcesso'
    ]

}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>