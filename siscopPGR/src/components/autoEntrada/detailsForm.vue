<template>
     <q-card bordered class="my-card" flat>
        <q-card-section class="bg-secondary text-white">
          <div class="text-h6">{{ $t('basicInformation') }}</div>
        </q-card-section>
        <q-separator/>
        <q-card-section class="bg-white text-grey">
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Número') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.numero }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Data Abertura') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.dataAbertura }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Descrição') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.descricao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Modus Operandi') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.modusOperandi }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Data e hora da Ocorrência') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.horaOcorrencia }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Infração') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.infraccao }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Endereço') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.endereco }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Responsável do Local') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.responsavelLocal }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Contacto') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.contacto }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Tipo Auto') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.tipoAuto }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Família Delitiva') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.classeJudicial }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Tipo Legal de Crime') }}</q-item-label>
                  <q-item-label class="text-grey-9">{{ autoEntrada.crime }}</q-item-label>
                </q-item-section>
              </q-item>
            </div>
          </div>
          <q-separator/>
          <div class="row">
            <div class="col">
              <q-item class="full-width">
                <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Inspector') }}</q-item-label>
                  <q-item-label class="text-grey-9">
                 {{ autoEntrada.inspector }}
                  </q-item-label>
                </q-item-section>
              </q-item>
            </div>
            <div class="col">
             <q-item class="full-width">
             <q-item-section>
                  <q-item-label caption lines="1">{{ $t('Documento Anexo') }}</q-item-label>
                  <q-item-label class="text-grey-9">
                    <div class="file-upload">
                         <q-btn flat color="primary" label="VER DOCUMENTO" no-caps :disabled="autoEntrada.anexo === null ? false : true" @click.stop="forceFileDownload(autoEntrada,'Anexo.pdf')"/>
                    </div>
                  </q-item-label>
              </q-item-section>
              </q-item>
            </div>
          </div>
        </q-card-section>
      </q-card>
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
     forceFileDownload(autoEntrada, title) {
      var bytes = btoa(new Uint8Array(autoEntrada.anexo).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      const url = "data:application/pdf;base64, " + bytes 
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', title)
      document.body.appendChild(link)
      link.click()
    },
  },
  props:
    [
      'autoEntrada'
    ],
     i18n: {
    messages: {
      pt: {
        title: 'Detalhes do Auto',
        basicInformation: 'Informacção do Processo',
        numeroProcesso: 'Número do Processo',
        numeroAuto: 'Número do Auto',
        proveniencia: 'Proveniência',
        dataEntrada: 'Data de Entrada',
        anexo: 'Anexo',
        formaProcesso: 'Forma Processo',
        inspector: 'Inspector',
        magistrado: 'Magistrado',
        agentes: 'Agentes'
      },
      en: {
        title: 'Detalhes do Auto',
        basicInformation: 'Informacção do Processo',
        numeroProcesso: 'Número do Processo',
        numeroAuto: 'Número do Auto',
        proveniencia: 'Proveniência',
        dataEntrada: 'Data de Entrada',
        anexo: 'Anexo',
        formaProcesso: 'Forma Processo',
        inspector: 'Inspector',
        magistrado: 'Magistrado',
        agentes: 'Agentes'
      }
    }
  } 
}
</script>
<style scoped>
.w-field{
  width: 300px
}
</style>