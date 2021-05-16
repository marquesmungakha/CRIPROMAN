<template>
 
    <q-dialog :value="vitima_details_dialog" persistent>
            <q-card class="my-card"  style="width: 900px; max-width: 90vw;" flat bordered>
              <q-card-section>
                <div class="text-h6">
                  Detalhes do Vitima
                  <q-btn round flat dense icon="close" class="float-right" color="grey-8" @click="close"/>
                </div>
              </q-card-section>
              <q-card-section horizontal>
                <q-card-section class="q-pt-xs">
                  <div class="text-h5 q-mt-sm q-mb-xs">{{ this.vitima.nome}} {{ this.vitima.apelido}}</div>
                   <div class="text-overline">Dados Pessoais</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Idade:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.idade}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                       Data de Nascimento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.dataNascimento}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Gênero:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.sexo}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Local de Nascimento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.localNascimento}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Estado Civil:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.estadoCivil}}
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
                      Númenero de Documento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.numDocumentoIndentificacao}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Validade do Documento:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.documentoValidade}}
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
                      {{ this.vitima.naturalidade}}
                    </div>
                  </div>
                   <div class="text-overline">Contactos</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Telemóvel:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.telemovel1}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Telemóvel Alternativo:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.telemovel2}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Morada:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.vitima.morada}}
                    </div>
                  </div>
                   <!-- <div class="text-overline">Outros Dados</div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Ocupação:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.pecaProcessoVitima.ocupacao}}
                    </div>
                  </div>
                   <div class="row">
                    <div class="col text-caption text-grey">
                      Local de Trabalho:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.pecaProcessoVitima.localTrabalho}}
                    </div>
                  </div>
                  <div class="row">
                    <div class="col text-caption text-grey">
                      Vitima na qualidade De:
                    </div>
                    <div class="col text-caption text-grey">
                      {{ this.pecaProcessoVitima.qualidadeDe}}
                    </div>
                  </div> -->
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
                  <div class="text-overline">Descrição da Ocorrência</div>
                {{ this.pecaProcessoVitima.declaracao }}
              </q-card-section>
            </q-card>
          </q-dialog>

</template>
<script>
export default {
  props: ['pecaProcessoVitima','vitima','tipoDocumento','pais','image','vitima_details_dialog','close'],
  methods: {
   
  }
}
</script>
<style scoped>
  img {
    max-height: 300px;
  }
</style>