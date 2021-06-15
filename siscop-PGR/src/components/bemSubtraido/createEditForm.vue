<template>

  <q-list>
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
          <div class="col-4 text-left">Valor do Prejuízo *</div>
     
               <q-input  outlined tack-label class="w-field" :dense="true" :value="valorPrejuizo"
                          @input="$emit('update:valorPrejuizo', $event)"
                          ref="valorPrejuizo"
                          
                          lazy-rules
                          :rules="[ val => val && val.length > 0 || 'Introduza o Valor do Prejuízo']"
                          type="number"
                          suffix="Meticais"/>
        </div>
     </div>
  </q-list>

</template>
<script>

export default {

  data () {
    return {
    }
  },
  computed: {
  },
  components: {
  },
  methods: {
  },
  props: [
    'descricao',
    'valorPrejuizo'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
