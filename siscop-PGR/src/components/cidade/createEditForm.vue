<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 600px; max-width: 60vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Cidade!</div>
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
                <q-form @submit.prevent="createCidade" class="q-gutter-md">
                    <q-list>
                        <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Código *</div>
                                <q-input outlined tack-label class="w-field" :dense="true" :value="codigo"
                                @input="$emit('update:codigo', $event)"
                                ref="codigo"
                                
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza o codigo da Cidade']" />
        </div>           
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Designação *</div>
                       
                                <q-input outlined tack-label class="w-field" :dense="true" :value="designacao"
                                @input="$emit('update:designacao', $event)"
                                ref="designacao"
                                
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza a Designação']" />
        </div>
                        </div>
                        <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Provícia *</div>
                            <q-select
                              filled
                              outlined tack-label class="w-field" :dense="true" :value="provincia"
                              use-input
                              fill-input
                              hide-selected
                              
                              input-debounce="0"
                              :options="optionsProvincias"
                              option-label="designacao"
                              option-value="id"
                              @filter="filterFnProvincia"
                              @input="$emit('update:provincia', $event)"
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
          <div class="col-4 text-left">Distrito </div>
                        
                            <q-select
                              filled
                              outlined tack-label class="w-field" :dense="true" :value="distrito"
                              use-input
                              fill-input
                              hide-selected
                              
                              input-debounce="0"
                              :options="optionsDistritos"
                              option-label="designacao"
                              option-value="id"
                              @filter="filterFnDistrito"
                              @input="$emit('update:distrito', $event)"
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
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createCidade" color="teal" label="Gravar" />
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
      optionsProvincias: [],
      optionsDistritos: []
    }
  },
  computed: {
  },
  methods: {
    filterFnProvincia (val, update, abort) {
      const stringOptions = this.provincias
      if (val === '') {
        update(() => {
          this.optionsProvincias = stringOptions.map(provincia => provincia)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsProvincias = []
        })
      } else {
        update(() => {
          this.optionsProvincias = stringOptions
            .map(provincia => provincia)
            .filter(provincia => {
              return provincia &&
                   provincia.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnDistrito (val, update, abort) {
      const stringOptions = this.distritos
      if (val === '') {
        update(() => {
          this.optionsDistritos = stringOptions.map(distrito => distrito)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsDistritos = []
        })
      } else {
        update(() => {
          this.optionsDistritos = stringOptions
            .map(distrito => distrito)
            .filter(distrito => {
              return distrito &&
                   distrito.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.provincia = val
    }
  },
  props: {
    show_dialog: {
      type: Boolean
    },
    listErrors: {
      type: Array
    },
    codigo: {
      type: String
    },
    designacao: {
      type: String
    },
    provincia: {
      type: Object
    },
    provincias: {
      type: Array
    },
    distrito: {
      type: Object
    },
    distritos: {
      type: Array
    },
    submitting: {
      type: Boolean
    },
    close: {
      type: Function
    },
    createCidade: {
      type: Function
    },
    removeCidade: {
      type: Function
    }
  }
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
