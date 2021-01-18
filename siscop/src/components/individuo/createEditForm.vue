<template>
    <q-list>
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
            <q-input :value="dataNascimento"  label="Data de Nascimento " @input="$emit('update:dataNascimento', $event)" :rules="['####-##-##']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date :value="dataNascimento" minimal mask="YYYY-MM-DD" @input="$emit('update:dataNascimento', $event)">
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
            <q-select
              :value="pais"
              use-input
              fill-input
              hide-selected
              label="Nacionalidade *"
              input-debounce="0"
              :options="optionsPaises"
              option-label="nacionalidade"
              option-value="id"
              @filter="filterFn"
              @input="$emit('update:pais', $event)"
              abort="abortFilterFn"
              hint="Autocompleta o Texto">
              <template v-slot:no-option>
                <q-item>
                  <q-item-section class="text-grey">
                    No results
                  </q-item-section>
                </q-item>
              </template>
            </q-select>
            </q-item-section>
        </q-item>
        <q-item>
            <q-item-section>
            <q-select
              :value="provincia"
              use-input
              fill-input
              hide-selected
              label="Província *"
              input-debounce="0"
              :options="optionsProvincia"
              option-label="designacao"
              option-value="id"
              @filter="filterFnProvincia"
              @input="$emit('update:provincia', $event)"
              abort="abortFilterFn"
              hint="Autocompleta o Texto">
              <template v-slot:no-option>
                <q-item>
                  <q-item-section class="text-grey">
                    No results
                  </q-item-section>
                </q-item>
              </template>
            </q-select>
            </q-item-section>
        </q-item>
          <q-item>
            <q-item-section>
                <q-input :value="localNascimento"
                @input="$emit('update:localNascimento', $event)"
                ref="localNascimento"
                label="Local de Nascimento *"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza o Local de Nascimento']" />
            </q-item-section>
        </q-item>
          <q-item>
            <q-item-section>
                <q-input :value="naturalidade"
                @input="$emit('update:naturalidade', $event)"
                ref="naturalidade"
                label="Naturalidade *"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza a Naturalidade']" />
            </q-item-section>
        </q-item>
          <q-item>
            <q-item-section>
                <q-select :value="estadoCivil"
                          :options="optionsEstadoCivil"
                          label="Estado Civíl *"
                          @input="$emit('update:estadoCivil', $event)"/>
            </q-item-section>
        </q-item>
        <q-item>
            <q-item-section>
                <q-select :value="tipoDocumento"
                          :options="tipoDocumentos"
                           option-label="designacao"
                            option-value="id"
                          label="Tipo de Documento de Identificação *"
                          @input="$emit('update:tipoDocumento', $event)"/>
            </q-item-section>
        </q-item>
        <q-item>
            <q-item-section>
                <q-input :value="numDocumentoIndentificacao"
                @input="$emit('update:numDocumentoIndentificacao', $event)"
                ref="numDocumentoIndentificacao"
                label="Número de Documento de Indentificação *"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza o Número de Documento de Indentificação']" />
            </q-item-section>
        </q-item>
          <q-item>
            <q-item-section>
            <q-input :value="documentoValidade"  label="Data de Validade " @input="$emit('update:documentoValidade', $event)" :rules="['####-##-##']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date :value="documentoValidade" minimal mask="YYYY-MM-DD" @input="$emit('update:documentoValidade', $event)">
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
                    ref="telemovel2"
                    label="Telefone Alternativo *"
                    lazy-rules />
                </q-item-section>
            </q-item>
            <q-item>
                <q-item-section>
                    <q-input :value="morada"
                    @input="$emit('update:morada', $event)"
                    ref="morada"
                    label="Morada "
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
      selected_file: '',
      check_if_document_upload: false,
      optionsProvincia: [],
      optionsPaises: [],
      optionsTipoDocumento: [],
      optionsEstadoCivil: ['Casado(a)', 'Solteiro(a)', 'Viúvo(a)', 'Disvorciado(a)', 'Vive Maritalmente(a)', 'Outro(a)'],
      optionsGenero: ['Masculino', 'Femenino'],
      hasImage: false,
      image: null
    }
  },
  computed: {
  },
  methods: {
    filterFn (val, update, abort) {
      const stringOptions = this.paises
      if (val === '') {
        update(() => {
          this.optionsPaises = stringOptions.map(pais => pais)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsPaises = []
        })
      } else {
        update(() => {
          this.optionsPaises = stringOptions
            .map(pais => pais)
            .filter(pais => {
              return pais &&
                   pais.nacionalidade.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    filterFnProvincia (val, update, abort) {
      const stringOptions = this.provincias
      if (val === '') {
        update(() => {
          this.optionsProvincia = stringOptions.map(provincia => provincia)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.optionsProvincia = []
        })
      } else {
        update(() => {
          this.optionsProvincia = stringOptions
            .map(provincia => provincia)
            .filter(provincia => {
              return provincia &&
                   provincia.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setImage: function (output) {
      this.hasImage = true
      this.image = output
      console.log('Info', output.info)
      console.log('Exif', output.exif)
    }
  },
  props: ['show_dialog',
    'listErrors',
    'estadoCivil',
    'naturalidade',
    'nome',
    'sexo',
    'dataNascimento',
    'telemovel2',
    'apelido',
    'telemovel1',
    'localNascimento',
    'morada',
    'pais',
    'paises',
    'documentoValidade',
    'numDocumentoIndentificacao',
    'tipoDocumento',
    'tipoDocumentos',
    'provincia',
    'provincias',
    'close',
    'submitting',
    'createInspector',
    'removeInspector']
}
</script>
