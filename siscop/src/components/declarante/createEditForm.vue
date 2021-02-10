<template>
  <q-list>
      <q-item>
          <q-item-section>
                <q-input :value="declaracao"  type="textarea"  label="Declaração *  " @input="$emit('update:declaracao', $event)"/>
          </q-item-section>
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
