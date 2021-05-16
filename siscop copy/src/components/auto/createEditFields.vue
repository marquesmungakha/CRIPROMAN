<template>
<q-list>
      <q-item>
          <q-item-section>
              <q-input :value="numero"
              @input="$emit('update:numero', $event)"
              ref="numero"
              label="Número do Auto * "
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Número do Auto']" />
          </q-item-section>
      </q-item>
       <q-item>
        <q-item-section>
            <q-input :value="data_abertura"  label="Data de Abertura " mask="####-##-##" @input="$emit('update:data_abertura', $event)" :rules="['date']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date :value="data_abertura" @input="$emit('update:data_abertura', $event)">
                    <div class="row items-center justify-end">
                      <q-btn v-close-popup label="Close" color="primary" flat />
                    </div>
                  </q-date>
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
        </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
                <q-input :value="descricao"  type="textarea"  label="Descrição do Auto  " @input="$emit('update:descricao', $event)"/>
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
            <q-input
              @input="val => { anexo = val[0] }"
              type="file"
              hint="Anexo do Documento"
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
