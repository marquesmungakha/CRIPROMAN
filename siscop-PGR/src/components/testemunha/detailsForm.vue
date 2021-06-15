<template>
 
    <q-dialog outlined tack-label class="w-field" :dense="true" :value="testemunha_details_dialog" persistent>
            <q-card class="my-card"  style="width: 900px; max-width: 90vw;" flat bordered>
              <q-card-section>
                <div class="text-h6">
                  Detalhes do Testemunha
                  <q-btn round flat dense icon="close" class="float-right" color="grey-8" @click="close"/>
                </div>
              </q-card-section>
              <q-card-section horizontal>
                <q-card-section class="q-pt-xs">
                  <div class="text-h5 q-mt-sm q-mb-xs">{{ this.testemunha.nome}} {{ this.testemunha.apelido}}</div>
                   <div class="text-overline">Dados Pessoais</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Idade:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.idade}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                       Data de Nascimento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.dataNascimento}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Gênero:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.sexo}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Local de Nascimento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.localNascimento}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Estado Civil:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.estadoCivil}}
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
                      {{ this.testemunha.numDocumentoIndentificacao}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Validade do Documento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.documentoValidade}}
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
                      {{ this.testemunha.naturalidade}}
                    </div>
                  </div>
                   <div class="text-overline">Contactos</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Telemóvel:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.telemovel1}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Telemóvel Alternativo:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.telemovel2}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Morada:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.testemunha.morada}}
                    </div>
                  </div>
                   <div class="text-overline">Outros Dados</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Ocupação:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.getOcupacao()}}
                    </div>
                  </div>
                   <div class="row">
                    <div class="col text-caption text-grey">
                      Local Trabalho:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.getLocalTrabalho()}}
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
              <q-separator/>
              <q-card-section>
                  <div class="text-overline">Depoimento</div>
                {{ this.getDepoimento()}}
              </q-card-section>
            </q-card>
          </q-dialog>

</template>
<script>
export default {
  props: ['autoEntradaTestemunha','pecaProcessoTestemunha','testemunha','tipoDocumento','pais','image','testemunha_details_dialog','close'],
  methods: {
    getDepoimento(){
      if(this.pecaProcessoTestemunha !== null && this.pecaProcessoTestemunha !== undefined)
          return this.pecaProcessoTestemunha.depoimento
      else
          return this.autoEntradaTestemunha.depoimento
    },
    getLocalTrabalho(){
      if(this.pecaProcessoTestemunha !== null && this.pecaProcessoTestemunha !== undefined)
          return this.pecaProcessoTestemunha.localTrabalho
      else
          return this.autoEntradaTestemunha.localTrabalho
    },
    getOcupacao(){
      if(this.pecaProcessoTestemunha !== null && this.pecaProcessoTestemunha !== undefined)
          return this.pecaProcessoTestemunha.ocupacao
      else
          return this.autoEntradaTestemunha.ocupacao
    }

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