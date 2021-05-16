<template>

<q-list>
 <div class="row">
        <div class="col col-md-8">
        <q-item>
          <q-item-section>
              <q-input :value="numero"
              @input="$emit('update:numero', $event)"
              ref="numero"
              label="Número de Identificação*"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Número de Identificação']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="nome"
              @input="$emit('update:nome', $event)"
              ref="nome"
              label="Nome *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Nome']" />
          </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="apelido"
              @input="$emit('update:apelido', $event)"
              ref="apelido"
              label="Apelido *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Apelido']" />
          </q-item-section>
      </q-item>
        </div>
        <div class="col-6 col-md-4">
          <div class="text-overline text-center">
            <q-avatar size="205px" rounded>
              <img :src="image">
            </q-avatar>
            </div>
              <q-item>
                <q-item-section>
                  <q-input type="file" filled  :value="fotografia" @change="onFileChange" stack-label label="Fotografia" @input="$emit('update:fotografia', $event)" >
                    <template v-slot:prepend>
                      <q-icon name="attach_file" @click.stop />
                    </template>
                    <template v-slot:append>
                      <q-icon name="close" @click.stop="fotografia = null" class="cursor-pointer" />
                    </template>
                  </q-input>
                </q-item-section>
          </q-item>
        </div>
      </div>
       <q-item>
          <q-item-section>
                <q-select :value="sexo"
                :options="optionsGenero"
                label="Gênero *"
                @input="$emit('update:sexo', $event)"/>
          </q-item-section>
      </q-item>
        <q-item>
          <q-item-section>
              <q-input :value="idade"
              @input="$emit('update:idade', $event)"
              ref="idade"
              label="Idade *"
              type="number"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza a Idade']" />
          </q-item-section>
      </q-item>
      <q-item>
            <q-item-section>
                <q-input :value="telemovel1"
                @input="$emit('update:telemovel1', $event)"
                ref="telemovel1"
                label="Telefone Principal *"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza o Telefone Principal']" />
            </q-item-section>
      </q-item>
      <q-item>
          <q-item-section>
              <q-input :value="telemovel2"
              @input="$emit('update:telemovel2', $event)"
              id="fotografia"
              ref="telemovel2"
              label="Telefone Alternativo *"
              lazy-rules
              :rules="[ val => val && val.length > 0 || 'Introduza o Telefone Alternativo']" />
          </q-item-section>
      </q-item>
      
</q-list>
</template>
<script>

export default {
  data () {
    return {
      msg: 'Vue Image Upload and Resize Demo',
      hasImage: false,
      optionsGenero: ['Masculino', 'Femenino'],
      errorDialog: null,
      errorText: '',
      uploadFieldName: 'file',
      maxSize: 1024
    }
  },
  computed: {
  },
  methods: {
  },
  props: [
    'nome',
    'sexo',
    'idade',
    'telemovel2',
    'apelido',
    'telemovel1',
    'fotografia',
    'numero',
    'close',
    'submitting',
    'onFileChange',
    'image'
  ]
}
</script>
