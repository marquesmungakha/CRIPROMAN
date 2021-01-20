<template>
    <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Orgão!</div>
        </q-card-section>
               <q-card-section>
          <div v-if="listErrors.length > 0" class="q-pa-sm q-gutter-sm" style="max-width: 550px; max-height: 150px;border-radius: 10px; border: 1px solid #cb4646; margin: 5px; background-color: #ead8da">
            <ul class="list-group alert alert-danger">
              <li class="list-group-item text-negative q-pl-xs text-weight-regular text-caption"
                  v-for="item in listErrors" :key="item">
                {{ item }}
              </li>
            </ul>
          </div>
        </q-card-section>
        <q-separator />
        <q-card-section style="max-height: 70vh" class="scroll">
                <q-form @submit.prevent="createOrgao" class="q-gutter-md">
                    <q-list>
                        <q-item>
                            <q-item-section>
                                <q-input :value="sigla"
                                @input="$emit('update:sigla', $event)"
                                ref="sigla"
                                label="Sigla *"
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza a sigla da Orgão']" />
                            </q-item-section>
                        </q-item>
                        <q-item>
                            <q-item-section>
                                <q-input :value="designacao"
                                @input="$emit('update:designacao', $event)"
                                ref="designacao"
                                label="Designacao *"
                                lazy-rules
                                :rules="[ val => val && val.length > 0 || 'Introduza a Designação']" />
                            </q-item-section>
                        </q-item>
                        <q-item>
                            <q-item-section>
                            <q-select
                              filled
                              :value="tipoOrgao"
                              use-input
                              fill-input
                              hide-selected
                              label="Tipo de Orgão *"
                              input-debounce="0"
                              :options="options"
                              option-label="designacao"
                              option-value="id"
                              @filter="filterFn"
                              @input="$emit('update:tipoOrgao', $event)"
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
                                 <q-select :value="provincia"
                                  :options="provincias"
                                  label="Província *"
                                  option-label="designacao"
                                  option-value="id"
                                  @input="$emit('update:provincia', $event)"/>
                            </q-item-section>
                        </q-item>
                         <q-item>
                            <q-item-section>
                                <q-select :value="distrito"
                                          :options="distritos"
                                          label="Distrito *"
                                          option-label="designacao"
                                          option-value="id"
                                          @input="$emit('update:distrito', $event)"/>
                            </q-item-section>
                        </q-item>
                         <!--q-item>
                            <q-item-section>
                                <q-select :value="cidade"
                                          :options="cidades"
                                          label="Cidade "
                                          option-label="designacao"
                                          option-value="id"
                                          @input="$emit('update:cidade', $event)"/>
                            </q-item-section>
                        </q-item>
                         <q-item>
                            <q-item-section>
                                 <q-select :value="postoAdministrativo"
                                           :options="postoAdministrativos"
                                           label="Posto Administrativo"
                                            option-label="designacao"
                                            option-value="id"
                                           @input="$emit('update:postoAdministrativo', $event)"/>
                            </q-item-section>
                        </q-item>
                         <q-item>
                            <q-item-section>
                                <q-select :value="bairro"
                                          :options="bairros"
                                          label="Bairro "
                                          option-label="designacao"
                                          option-value="id"
                                          @input="$emit('update:bairro', $event)"/>
                            </q-item-section>
                        </q-item>
                         <q-item>
                            <q-item-section>
                                  <q-select :value="localidade"
                                            :options="localidades"
                                            label="Localidade "
                                            option-label="designacao"
                                            option-value="id"
                                            @input="$emit('update:localidade', $event)"/>
                            </q-item-section>
                        </q-item>
                         <q-item>
                            <q-item-section>
                            <q-input :value="dataCriacao"  label="Data de Criação " mask="####-##-##" @input="$emit('update:dataCriacao', $event)" :rules="['date']">
                            <template v-slot:append>
                              <q-icon name="event" class="cursor-pointer">
                                <q-popup-proxy ref="qDateProxy" transition-show="scale" transition-hide="scale">
                                  <q-date :value="dataCriacao" @input="$emit('update:dataCriacao', $event)">
                                    <div class="row items-center justify-end">
                                      <q-btn v-close-popup label="Close" color="primary" flat />
                                    </div>
                                  </q-date>
                                </q-popup-proxy>
                              </q-icon>
                            </template>
                          </q-input>
                            </q-item-section>
                        </q-item-->
                          <q-item>
                            <q-item-section>
                                 <q-input :value="nivel" type="number"  label="Nível do Orgão "  @input="$emit('update:nivel', $event)"/>
                            </q-item-section>
                        </q-item>
                          <q-item>
                            <q-item-section>
                                 <q-input :value="endereco"  type="textarea"  label="Endereço  " @input="$emit('update:endereco', $event)"/>
                            </q-item-section>
                        </q-item>
                    </q-list>
                </q-form>
        </q-card-section>
        <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createOrgao" color="teal" label="Gravar" />
            <q-btn label="Cancelar" type="reset" @click="close" color="negative" v-close-popup />
        </q-card-actions>
        </q-card>
    </q-dialog>
   </div>
</template>
<script>

export default {

  data () {
    return {
      options: []
    }
  },
  computed: {
  },
  methods: {
    filterFn (val, update, abort) {
      const stringOptions = this.tipoOrgaos
      if (val === '') {
        update(() => {
          this.options = stringOptions.map(tipoOrgao => tipoOrgao)
        })
      } else if (stringOptions.length === 0) {
        update(() => {
          this.options = []
        })
      } else {
        update(() => {
          this.options = stringOptions
            .map(tipoOrgao => tipoOrgao)
            .filter(tipoOrgao => {
              return tipoOrgao &&
                   tipoOrgao.designacao.toLowerCase().indexOf(val.toLowerCase()) !== -1
            })
        })
      }
    },
    abortFilterFn () {
      // console.log('delayed filter aborted')
    },
    setModel (val) {
      this.tipoOrgao = val
    }
  },
  props: ['show_dialog',
    'listErrors',
    'sigla',
    'designacao',
    'dataCriacao',
    'nivel',
    'endereco',
    'provincia',
    'provincias',
    'distrito',
    'distritos',
    'tipoOrgao',
    'tipoOrgaos',
    'close',
    'submitting',
    'createOrgao',
    'removeOrgao']
  // props: {
  //   show_dialog: {
  //     type: Boolean
  //   },
  //   listErrors: {
  //     type: Array
  //   },
  //   sigla: {
  //     type: String
  //   },
  //   designacao: {
  //     type: String
  //   },
  //   dataCriacao: {
  //     type: Date,
  //     required: false
  //   },
  //   nivel: {
  //     type: Number,
  //     default: function () { return 0 }
  //   },
  //   endereco: {
  //     type: String
  //   },
  //   provincia: {
  //     type: Object
  //   },
  //   distrito: {
  //     type: Object
  //   },
  //   cidade: {
  //     type: Object
  //   },
  //   postoAdministrativo: {
  //     type: Object,
  //     required: false,
  //     default: undefined
  //   },
  //   bairro: {
  //     type: Object
  //   },
  //   localidade: {
  //     type: Object
  //   },
  //   tipoOrgao: {
  //     type: Object
  //   },
  //   tipoOrgaos: {
  //     type: Array
  //   },
  //   provincias: {
  //     type: Array
  //   },
  //   distritos: {
  //     type: Array
  //   },
  //   cidades: {
  //     type: Array
  //   },
  //   postAdminisyrativos: {
  //     type: Array
  //   },
  //   bairros: {
  //     type: Array
  //   },
  //   localidades: {
  //     type: Array
  //   },
  //   submitting: {
  //     type: Boolean
  //   },
  //   close: {
  //     type: Function
  //   },
  //   createOrgao: {
  //     type: Function
  //   },
  //   removeOrgao: {
  //     type: Function
  //   }
  // }
}
</script>
