<template>
<q-list>
<q-item>
      <div class="row">
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Número do Auto *</div>
              <q-input outlined tack-label class="w-field" :dense="true" :value="numero"
              @input="$emit('update:numero', $event)"
              ref="numero"
              label="Número do Auto * "
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Número do Auto']" />
        </div>
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Data de Abertura *</div>
      
            <q-input outlined tack-label class="w-field" :dense="true" :value="data_abertura"   mask="####-##-##" @input="$emit('update:data_abertura', $event)" :rules="['date']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date outlined tack-label class="w-field" :dense="true" :value="data_abertura" @input="$emit('update:data_abertura', $event)">
                    <div class="row items-center justify-end">
                      <q-btn v-close-popup label="Close" color="primary" flat />
                    </div>
                  </q-date>
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
        </div>
      </div>
</q-item>
<q-item>
     <div class="row">
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Descrição do Auto *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="descricao"  type="textarea"   @input="$emit('update:descricao', $event)"/>
        </div>
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Anexo</div>
            <q-input
              @input="val => { anexo = val[0] }"
              type="file"
              hint="Anexo do Documento"
            />
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
  methods: {
  },
  props: [
    'anexo',
    'numero',
    'data_abertura',
    'descricao'
  ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
