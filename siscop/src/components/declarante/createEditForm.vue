<template>
  <q-list>
  <q-item>
      <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Declaração *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="declaracao"  type="textarea"   @input="$emit('update:declaracao', $event)"/>
        </div>
      </div>
  </q-item>
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
    'declaracao'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
