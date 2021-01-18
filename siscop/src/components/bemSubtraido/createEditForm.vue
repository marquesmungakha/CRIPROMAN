<template>

  <q-list>
     <q-item>
          <q-item-section>
              <q-input :value="descricao"
              @input="$emit('update:descricao', $event)"
              ref="descricao"
              label="Descrição *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Descrição']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
               <q-input  :value="valorPrejuizo"
                          @input="$emit('update:valorPrejuizo', $event)"
                          ref="valorPrejuizo"
                          label="Valor do Prejuizo *"
                          lazy-rules
                          :rules="[ val => val && val.length > 0 || 'Introduza o Valor do Prejuizo']"
                          type="number"
                          suffix="Meticais"/>
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
    'descricao',
    'valorPrejuizo'
  ]
}
</script>
