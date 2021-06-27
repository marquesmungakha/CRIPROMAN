<template>
    <q-list>
    <q-item>
        <div class="row">                    
          <div class="row q-gutter-xs" style="width: 500px;">
            <div class="col-4 text-left">Descrição *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="descricao"
                @input="$emit('update:descricao', $event)"
                ref="descricao"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza a Descrição']" />
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
  props: [
    'descricao',
    'estado',
    'tipo']
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
