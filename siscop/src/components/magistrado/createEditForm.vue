<template>

  <q-list>
      <q-item>
          <q-item-section>
              <q-input :value="cargoOcupado"
              @input="$emit('update:cargoOcupado', $event)"
              ref="cargoOcupado"
              label="Cargo Ocupado *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Cargo Ocupado']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="descricao"
              @input="$emit('update:descricao', $event)"
              ref="descricao"
              label="Descrição *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a descrição do Cargo']" />
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
    'cargoOcupado'
  ]
}
</script>
