<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Objectos Apreendidos!</div>
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
        <q-card-section style="max-height: 70vh" class="scroll">
                <q-form @submit.prevent="createObjectosApreendidos" class="q-gutter-md">
                    <q-list>
                    <q-item>
                        <div class="row">                    
                          <div class="row q-gutter-xs" style="width: 500px;">
                            <div class="col-4 text-left">Designação *</div>
                                <q-input outlined tack-label class="w-field" :dense="true" :value="designacao"
                                @input="$emit('update:designacao', $event)"
                                ref="designacao"
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza a Designação']" />
                          </div>
                          <div class="row q-gutter-xs" style="width: 500px;">
                            <div class="col-4 text-left">Tipo de Objecto *</div>
                                <q-input outlined tack-label class="w-field" :dense="true" :value="tipo"
                                @input="$emit('update:tipo', $event)"
                                ref="tipo"
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza o Tipo']" />              
                          </div>            
                          </div>
                    </q-item>
                        <q-item>
                        <div class="row">                
                          <div class="row q-gutter-xs" style="width: 500px;">
                            <div class="col-4 text-left">Estado do objecto *</div>
                              <q-input outlined tack-label class="w-field" :dense="true" :value="estado"
                                  @input="$emit('update:estado', $event)"
                                  ref="estado"
                                  lazy-rules
                                  :rules="[ val => val && val.length > 0 || 'Introduza o Estado do objecto']" />      
                          </div>
                        </div>
                        </q-item>
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createObjectosApreendidos" color="teal" label="Gravar" />
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
      options: []
    }
  },
  computed: {
  },
  methods: {
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.tipoObjectosApreendidos = val
    }
  },
  props: ['show_dialog',
    'listErrors',
    'designacao',
    'estado',
    'tipo',
    'close',
    'submitting',
    'createObjectosApreendidos',
    'removeObjectosApreendidos']
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
