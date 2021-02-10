<template>
  <q-list>
      <q-item>
          <q-item-section>
                <q-input :value="declaracao"  type="textarea"  label="Declaração *  " @input="$emit('update:declaracao', $event)"/>
          </q-item-section>
      </q-item>
      <q-item>
        <q-item-section>
            <q-input :value="localTrabalho"
            @input="$emit('update:localTrabalho', $event)"
            ref="localTrabalho"
            label="Local de trabalho "
            lazy-rules
              />
          </q-item-section>
      </q-item>
      <q-item>
        <q-item-section>
            <q-input :value="ocupacao"
            @input="$emit('update:ocupacao', $event)"
            ref="ocupacao"
            label="Ocupação "
            lazy-rules
              />
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
    'declaracao',
    'localTrabalho',
    'ocupacao'

  ]
}
</script>
