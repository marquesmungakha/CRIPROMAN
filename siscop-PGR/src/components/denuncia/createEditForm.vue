<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar Auto de Denuncia!</div>
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
                <q-form @submit.prevent="createDenuncia" class="q-gutter-md">
                    <q-list>
                    <q-item>
        <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Número do Auto *</div>
                                <q-input outlined tack-label class="w-field" :dense="true" :value="numero"
                                @input="$emit('update:numero', $event)"
                                ref="numero"
                                
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza o Número do Auto']" />
        </div>           
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Data de Abertura *</div>
                                <q-input outlined tack-label class="w-field" :dense="true" :value="dataAbertura"   @input="$emit('update:dataAbertura', $event)" :rules="['####-##-##']">
                                <template v-slot:append>
                                  <q-icon name="event" class="cursor-pointer">
                                    <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                                      <q-date outlined tack-label class="w-field" :dense="true" :value="dataAbertura" minimal mask="YYYY-MM-DD" @input="$emit('update:dataAbertura', $event)">
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
                    </q-item>
                    <q-item>
        <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Descrição do Auto *</div>
            <q-input outlined tack-label class="w-field" :dense="true" :value="descricao"  type="textarea"   @input="$emit('update:descricao', $event)"/>
        </div>
                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Procedimento Criminal *</div>
                            <q-select
                              outlined tack-label class="w-field" :dense="true" :value="procedimentoCriminal"
                              use-input
                              fill-input
                              hide-selected
                              input-debounce="0"
                              :options="options"
                              @input="$emit('update:procedimentoCriminal', $event)">
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
                    </q-item>
                    <q-item>
                    <div class="row">
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Inspector Responsável *</div>
                      
                            <q-select
                              outlined tack-label class="w-field" :dense="true" :value="inspector"
                              use-input
                              fill-input
                              hide-selected
                              
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
        </div>              
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Anexo</div>
                            <q-file color="primary" outlined tack-label class="w-field" :dense="true" :value="anexo"   @input="$emit('update:anexo', $event)">
                              <template v-slot:prepend>
                                <q-icon name="attach_file" />
                              </template>
                            </q-file>
        </div>
                       </div>
                    </q-item>
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createDenuncia" color="teal" label="Gravar" />
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
      options: ['Sim', 'Não'],
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
      'procedimentoCriminal',
      'inspector',
      'anexo',
      'inspectors',
      'close',
      'submitting',
      'createDenuncia'
    ]

}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
