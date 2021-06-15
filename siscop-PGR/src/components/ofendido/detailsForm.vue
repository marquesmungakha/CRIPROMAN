<template>
 
    <q-dialog outlined tack-label class="w-field" :dense="true" :value="ofendido_details_dialog" persistent>
            <q-card class="my-card"  style="width: 900px; max-width: 90vw;" flat bordered>
              <q-card-section>
                <div class="text-h6">
                  Detalhes do Ofendido
                  <q-btn round flat dense icon="close" class="float-right" color="grey-8" @click="close"/>
                </div>
              </q-card-section>
              <q-card-section horizontal>
                <q-card-section class="q-pt-xs">
                  <div class="text-h5 q-mt-sm q-mb-xs">{{ this.ofendido.nome}} {{ this.ofendido.apelido}}</div>
                   <div class="text-overline">Dados Pessoais</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Idade:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.idade}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                       Data de Nascimento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.dataNascimento}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Gênero:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.sexo}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Local de Nascimento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.localNascimento}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Estado Civil:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.estadoCivil}}
                    </div>
                  </div>
                   <div class="text-overline">Documento de Identificação</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Tipo de Documento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.tipoDocumento.designacao}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Número de Documento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.numDocumentoIndentificacao}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Validade do Documento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.documentoValidade}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Nacionalidade:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.pais.nacionalidade}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Naturalidade:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.naturalidade}}
                    </div>
                  </div>
                   <div class="text-overline">Contactos</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Telemóvel:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.telemovel1}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Telemóvel Alternativo:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.telemovel2}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Morada:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.ofendido.morada}}
                    </div>
                  </div>
                </q-card-section>
                <q-card-section class="col-6 col-md-4">
                <q-item class="full-width">
                  <q-item-section>
                    <q-avatar size="205px" rounded>
                    <img :src="image">
                    </q-avatar>
                  </q-item-section>
                </q-item>
                </q-card-section>
              </q-card-section>
            </q-card>
          </q-dialog>

</template>
<script>
export default {
  props: ['pecaProcessoOfendido','ofendido','tipoDocumento','pais','image','ofendido_details_dialog','close'],
  methods: {
   
  }
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>
<style scoped>
  img {
    max-height: 300px;
  }
</style>