<template>
<q-card class="my-card" flat bordered>
<q-card-section class="bg-secondary text-white">
	<div class="text-h6">{{ $t('basicInformation') }}</div>
</q-card-section>
<q-separator/>
<q-card-section class="bg-white text-grey">
  <div class="row">
	<div class="col">
	<q-item class="full-width">
	  <q-item-section>
		<q-item-label lines="1" caption >{{ $t('numeroProcesso') }}</q-item-label>
		<q-item-label class="text-grey-9">{{ processoInvestigacao.numeroProcesso }}</q-item-label>
	  </q-item-section>
	</q-item>
	</div>
	 <div class="col">
	<q-item class="full-width">
	  <q-item-section>
		<q-item-label lines="1" caption >{{ $t('numeroAuto') }}</q-item-label>
		<q-item-label class="text-grey-9">{{ processoInvestigacao.numeroAuto.numero }}</q-item-label>
	  </q-item-section>
	</q-item>
	</div>
  </div>
	  <q-separator/>
	 <div class="row">
	 <div class="col">
		<q-item class="full-width">
		  <q-item-section>
			<q-item-label lines="1" caption >{{ $t('proveniencia') }}</q-item-label>
			<q-item-label class="text-grey-9">{{ processoInvestigacao.proveniencia }}</q-item-label>
		  </q-item-section>
		</q-item>
	 </div>
	  <div class="col">
		<q-item class="full-width">
		  <q-item-section>
			<q-item-label lines="1" caption >{{ $t('dataEntrada') }}</q-item-label>
			<q-item-label class="text-grey-9">{{ processoInvestigacao.dataEntrada }}</q-item-label>
		  </q-item-section>
		</q-item>
	  </div>
	 </div>
	<q-separator/>
	   <div class="row">
		<div class="col">
	<q-item class="full-width">
	  <q-item-section>
		<q-item-label lines="1" caption >{{ $t('formaProcesso') }}</q-item-label>
		<q-item-label class="text-grey-9">{{ processoInvestigacao.formaProcesso }}</q-item-label>
	  </q-item-section>
	</q-item>
		</div>
		 <div class="col">
	<q-item class="full-width">
	  <q-item-section>
		<q-item-label lines="1" caption >{{ $t('inspector') }}</q-item-label>
		<q-item-label class="text-grey-9">{{ processoInvestigacao.inspector }} </q-item-label>
	  </q-item-section>
	</q-item>
		 </div>
	   </div>
	<q-separator/>
	<div class="row">
		 <div class="col">
	<q-item class="full-width">
	  <q-item-section>
		<q-item-label lines="1" caption >{{ $t('magistrado') }}</q-item-label>
		<q-item-label class="text-grey-9">{{ processoInvestigacao.magistrado }}</q-item-label>
	  </q-item-section>
	</q-item>
		 </div>
		<div class="col">
	  <q-item class="full-width">
	  <q-item-section>
			<q-item-label caption lines="1">{{ $t('Documento Anexo') }}</q-item-label>
			<q-item-label class="text-grey-9">
			  <div class="file-upload">
				  <q-btn flat color="primary" label="VER DOCUMENTO" no-caps :disabled="!processoInvestigacao.anexo" @click.stop="forceFileDownload(processoInvestigacao,'Anexo.pdf')"/>
			  </div>
			</q-item-label>
		</q-item-section>
		</q-item>
	  </div>
	</div>
	<q-separator/>
	 <div class="row">
	 <div class="col">
		<q-item class="full-width">
		  <q-item-section>
			<q-item-label lines="1" caption >{{ $t('Processo com Autor') }}</q-item-label>
			<q-item-label class="text-grey-9">{{ processoInvestigacao.autor }}</q-item-label>
		  </q-item-section>
		</q-item>
	 </div>
	 </div>
	 <q-separator/>
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
     forceFileDownload(processoInvestigacao, title) {
      var bytes = btoa(new Uint8Array(processoInvestigacao.anexo).reduce((data, byte) => data + String.fromCharCode(byte), ''))
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
      'processoInvestigacao'
    ],
     i18n: {
    messages: {
      pt: {
        title: 'Informacção do Processo de Investigação - SERNIC',
        basicInformation: 'Informacção do Processo de Investigação - SERNIC',
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
        title: 'Informacção do Processo de Investigação - SERNIC',
        basicInformation: 'Informacção do Processo de Investigação - SERNIC',
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