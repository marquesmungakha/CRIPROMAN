<template>
    <q-list>
    <div class="row">
        <div class="col col-md-8">

        <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Nome *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="nome"
                @input="$emit('update:nome', $event)"
                ref="nome"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza a Nome']" />
          </div>
                     
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Apelido *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="apelido"
                @input="$emit('update:apelido', $event)"
                ref="apelido"
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza o Apelido']" />
        </div>
        </div>
        <div class="row">                
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Gênero *</div>
                  <q-select outlined tack-label class="w-field" :dense="true" :value="sexo"
                  :options="optionsGenero"
                  @input="$emit('update:sexo', $event)"/>
        </div>
        <div class="row q-gutter-xs" style="width: 500px; height:100px">
          <div class="col-4 text-left">Data de Nascimento *</div>
            <q-input outlined tack-label class="w-field" :dense="true" :value="dataNascimento"   @input="$emit('update:dataNascimento', $event)" :rules="['####-##-##']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date outlined tack-label class="w-field" :dense="true" :value="dataNascimento" minimal mask="YYYY-MM-DD" @input="$emit('update:dataNascimento', $event)">
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
         </div>
        <!--div class="col-6 col-md-4">
        <q-item>
          <div class="text-overline text-center">
            <q-avatar size="205px" rounded>
              <img :src="image">
            </q-avatar>
              <q-item>
                <q-item-section>
                  <q-input type="file" filled  outlined tack-label class="w-field" :dense="true" :value="fotografia" @change="onFileChange" stack-label label="Fotografia" @input="$emit('update:fotografia', $event)" >
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

        </q-item>
        </div-->
      </div>

      <div class="row">
      <q-item>
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Nacionalidade *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="pais"
              use-input
              fill-input
              hide-selected
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
        </div>           
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Província *</div>
            <q-select
              outlined tack-label class="w-field" :dense="true" :value="provincia"
              use-input
              fill-input
              hide-selected
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
        </div>
      </q-item>
      </div>
       <div class="row">
      <q-item>
        <div class="row q-gutter-xs" style="width: 500px; ">
          <div class="col-4 text-left">Local de Nascimento </div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="localNascimento"
                @input="$emit('update:localNascimento', $event)"
                ref="localNascimento"
                lazy-rules
                />
        </div>            
        <div class="row q-gutter-xs" style="width: 500px; ">
          <div class="col-4 text-left">Naturalidade</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="naturalidade"
                @input="$emit('update:naturalidade', $event)"
                ref="naturalidade"
                
                lazy-rules
                />
        </div>
      </q-item>
       </div>
      <div class="row">
      <q-item>
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Estado Civíl *</div>
                <q-select outlined tack-label class="w-field" :dense="true" :value="estadoCivil"
                          :options="optionsEstadoCivil"
                          
                          @input="$emit('update:estadoCivil', $event)"/>
        </div>         
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Tipo de Documento de Identificação *</div>
                <q-select outlined tack-label class="w-field" :dense="true" :value="tipoDocumento"
                          :options="tipoDocumentos"
                           option-label="designacao"
                           option-value="id"
                          
                           lazy-rules
                          @input="$emit('update:tipoDocumento', $event)"/>
        </div>
      </q-item>
        </div>
      <div class="row">
      <q-item>
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Número de Documento de Indentificação *</div>
                <q-input outlined tack-label class="w-field" :dense="true" :value="numDocumentoIndentificacao"
                @input="$emit('update:numDocumentoIndentificacao', $event)"
                ref="numDocumentoIndentificacao"
                
                lazy-rules
                :rules="[ val => val && val.length > 0 || 'Introduza o Número de Documento de Indentificação']" />
        </div>           
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Data de Validade </div>
            <q-input outlined tack-label class="w-field" :dense="true" :value="documentoValidade"   @input="$emit('update:documentoValidade', $event)" :rules="['####-##-##']">
            <template v-slot:append>
              <q-icon name="event" class="cursor-pointer">
                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                  <q-date outlined tack-label class="w-field" :dense="true" :value="documentoValidade" minimal mask="YYYY-MM-DD" @input="$emit('update:documentoValidade', $event)">
                    <div class="row items-center justify-end">
                      <q-btn v-close-popup label="Close" color="primary" flat />
                    </div>
                  </q-date>
                </q-popup-proxy>
              </q-icon>
            </template>
          </q-input>
        </div>
      </q-item>
      </div>
      <div class="row">
      <q-item>            
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Telefone Principal </div>
                    <q-input outlined tack-label class="w-field" :dense="true" :value="telemovel1"
                    @input="$emit('update:telemovel1', $event)"
                    ref="telemovel1"
                    
                    lazy-rules
                    />
        </div>            
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Telefone Alternativo </div>
                    <q-input outlined tack-label class="w-field" :dense="true" :value="telemovel2"
                    @input="$emit('update:telemovel2', $event)"
                    ref="telemovel2"
                    
                    lazy-rules />
        </div>
      </q-item>
      </div>
      <div class="row">
      <q-item>   
        <div class="row q-gutter-xs" style="width: 500px;">
          <div class="col-4 text-left">Morada </div>
                    <q-input outlined tack-label class="w-field" :dense="true" :value="morada"
                    @input="$emit('update:morada', $event)"
                    ref="morada"
                    
                    lazy-rules
                   />
        </div>
      </q-item>
      </div>
    </q-list>

</template>
<script>
import treeList from '../listaArvore/treeList.vue'

export default {
  components: { treeList },

  data () {
    return {
      selected_file: '',
      check_if_document_upload: false,
      optionsProvincia: [],
      optionsPaises: [],
      optionsTipoDocumento: [],
      optionsEstadoCivil: ['Casado(a)', 'Solteiro(a)', 'Viúvo(a)', 'Disvorciado(a)', 'Vive Maritalmente(a)', 'Outro(a)'],
      optionsGenero: ['Masculino', 'Femenino'],
      hasImage: false
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
    'fotografia',
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
    'removeInspector',
    'onFileChange',
    'image'
    ]
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
