<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar Despacho!</div>
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
                <q-form @submit.prevent="createParecerAuto" class="q-gutter-md">
                    <q-list>
                        <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Data de Registo *</div>
                                <q-input outlined tack-label class="w-field" :dense="true" :value="dataRegisto"   @input="$emit('update:dataRegisto', $event)" :rules="['####-##-##']">
                                <template v-slot:append>
                                  <q-icon name="event" class="cursor-pointer">
                                    <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                                      <q-date outlined tack-label class="w-field" :dense="true" :value="dataRegisto" minimal mask="YYYY-MM-DD" @input="$emit('update:dataRegisto', $event)">
                                        <div class="row items-center justify-end">
                                          <q-btn v-close-popup label="Close" color="primary" flat />
                                        </div>
                                      </q-date>
                                    </q-popup-proxy>
                                  </q-icon>
                                </template>
                              </q-input>
        </div>            
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Descrição do Despacho *</div>
                                 <q-input outlined tack-label class="w-field" :dense="true" :value="parecer"  type="textarea"   @input="$emit('update:parecer', $event)"/>
        </div>
                        </div>
                        <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Despacho *</div>
                            <q-select
                              outlined tack-label class="w-field" :dense="true" :value="tipoParecer"
                              use-input
                              fill-input
                              hide-selected
                              
                              input-debounce="0"
                              :options="optionsTipoParecer"
                              option-label="designacao"
                              option-value="id"
                              @filter="filterFnTipoParecer"
                              @input="$emit('update:tipoParecer', $event)"
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
                        </div>
                        <div class="row">                
                          <div class="row q-gutter-xs" style="width: 500px;">
                            <div class="col-4 text-left">Secção </div>
                               <q-input outlined tack-label class="w-field" :dense="true" :value="seccao"
                                @input="$emit('update:seccao', $event)"
                                ref="seccao"
                                 />
                          </div>            
                          <div class="row q-gutter-xs" style="width: 500px;">
                            <div class="col-4 text-left">Brigada </div>
                                <q-input outlined tack-label class="w-field" :dense="true" :value="brigada"
                                @input="$emit('update:brigada', $event)"
                                ref="brigada"
                                 />
                          </div>
                        </div>
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createParecerAuto" color="teal" label="Gravar" />
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
      optionsTipoParecer: [],
      optionsInspector: [],
      optionsDestino: []
    }
  },
  computed: {
  },
  methods: {
    filterFnTipoParecer (val, update, abort) {
      const stringOptions = this.tiposParecer
      if (val === '') {
        update(() => {
          this.optionsTipoParecer = stringOptions.map(tipoParecer => tipoParecer)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsTipoParecer = []
        })
      } else {
        update(() => {
          this.optionsTipoParecer = stringOptions
            .map(tipoParecer => tipoParecer)
            .filter(tipoParecer => {
              return tipoParecer &&
                   tipoParecer.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
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
      'dataRegisto',
      'parecer',
      'seccao',
      'brigada',
      'localDespacho',
      'tipoParecer',
      'inspector',
      'anexo',
      'inspectors',
      'tiposParecer',
      'close',
      'submitting',
      'createParecerAuto'
    ]

}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
