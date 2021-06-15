<template>

  <q-list>
      <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Cargo Ocupado *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="cargoOcupado"
              @input="$emit('update:cargoOcupado', $event)"
              ref="cargoOcupado"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Cargo Ocupado']" />
        </div>             
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Descrição *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="descricao"
              @input="$emit('update:descricao', $event)"
              ref="descricao"
              
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a descrição do Cargo']" />
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
    'cargoOcupado'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
