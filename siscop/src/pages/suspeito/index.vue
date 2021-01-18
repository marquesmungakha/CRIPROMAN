<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Suspeito" :data="allSuspeitosFromPecaProcesso" :columns="columns" row-key="name" binary-state-sort :filter="filter">

      <template v-slot:top-right>
      <q-input v-if="show_filter" filled borderless dense debounce="300" v-model="filter" placeholder="Pesquisa">
              <template v-slot:append>
                <q-icon name="search"/>
              </template>
            </q-input>

      <div class="q-pa-md q-gutter-sm">
      <q-btn class="q-ml-sm" icon="filter_list" @click="show_filter=!show_filter" flat/>
        <q-btn outline rounded color="primary" label="Adicionar Novo" @click="show_dialog = true" no-caps/>
        <q-btn rounded color="primary" icon-right="archive" label="Imprimir em Excel" no-caps @click="exportTable"/>
      </div>
      </template>
      <template v-slot:body="props">
          <q-tr :props="props">
            <q-td key="nome" :props="props">
              {{ props.row.nome }}
              <q-popup-edit v-model="props.row.nome">
                <q-input v-model="props.row.nome" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="apelido" :props="props">
              {{ props.row.apelido }}
              <q-popup-edit v-model="props.row.apelido">
                <q-input v-model="props.row.apelido" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="sexo" :props="props">
              {{ props.row.sexo }}
              <q-popup-edit v-model="props.row.sexo">
                <q-input v-model="props.row.sexo" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="dataNascimento" :props="props">
              {{ props.row.dataNascimento }}
              <q-popup-edit v-model="props.row.dataNascimento">
                <q-input v-model="props.row.dataNascimento" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="naturalidade" :props="props">
              {{ props.row.naturalidade }}
              <q-popup-edit v-model="props.row.naturalidade">
                <q-input v-model="props.row.naturalidade" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="nacionalidade" :props="props">
              {{ getPais (props.row.nacionalidade.id).nacionalidade }}
              <q-popup-edit v-model="props.row.nacionalidade">
                <q-input v-model="props.row.nacionalidade" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="provincia" :props="props">
              {{ getProvincia (props.row.provincia.id).designacao }}
              <q-popup-edit v-model="props.row.provincia">
                <q-input v-model="props.row.provincia" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="localNascimento" :props="props">
              {{ props.row.localNascimento }}
              <q-popup-edit v-model="props.row.localNascimento">
                <q-input v-model="props.row.localNascimento" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="estadoCivil" :props="props">
              {{ props.row.estadoCivil }}
              <q-popup-edit v-model="props.row.estadoCivil">
                <q-input v-model="props.row.estadoCivil" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="tipoDocumento" :props="props">
              {{ getTipoDocumento(props.row.tipoDocumento.id).designacao  }}
              <q-popup-edit v-model="props.row.tipoDocumento">
                <q-input v-model="props.row.tipoDocumento" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="numDocumentoIndentificacao" :props="props">
              {{ props.row.numDocumentoIndentificacao }}
              <q-popup-edit v-model="props.row.numDocumentoIndentificacao">
                <q-input v-model="props.row.numDocumentoIndentificacao" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="documentoValidade" :props="props">
              {{ props.row.documentoValidade }}
              <q-popup-edit v-model="props.row.documentoValidade">
                <q-input v-model="props.row.documentoValidade" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="morada" :props="props">
              {{ props.row.morada }}
              <q-popup-edit v-model="props.row.morada">
                <q-input v-model="props.row.morada" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="profissao" :props="props">
              {{ getProfissao(props.row.profissao.id).designacao }}
              <q-popup-edit v-model="props.row.profissao">
                <q-input v-model="props.row.profissao" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="situacaoPrisional" :props="props">
              {{ getSituacaoPrisional(props.row.situacaoPrisional.id).designacao }}
              <q-popup-edit v-model="props.row.situacaoPrisional" >
                <q-input v-model="props.row.situacaoPrisional" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <q-btn round glossy icon="edit" color="blue" @click="editaSuspeito(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeSuspeito(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
   <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
         <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Suspeito!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
            <q-separator />
            <q-card-section style="max-height: 70vh" class="scroll">
                    <q-form @submit.prevent="createSuspeito" class="q-gutter-md">
                      <individuo :nome.sync="suspeito.nome"
                                :sexo.sync="suspeito.sexo"
                                :telemovel2.sync="suspeito.telemovel2"
                                :apelido.sync="suspeito.apelido"
                                :telemovel1.sync="suspeito.telemovel1"
                                :fotografia.sync="suspeito.fotografia"
                                :idade.sync="suspeito.idade"
                                :estadoCivil.sync="suspeito.estadoCivil"
                                :naturalidade.sync="suspeito.naturalidade"
                                :dataNascimento.sync="suspeito.dataNascimento"
                                :localNascimento.sync="suspeito.localNascimento"
                                :morada.sync="suspeito.morada"
                                :documentoValidade.sync="suspeito.documentoValidade"
                                :numDocumentoIndentificacao.sync="suspeito.numDocumentoIndentificacao"
                                :pais.sync="pais"
                                :provincia.sync="provincia"
                                :tipoDocumento.sync="tipoDocumento"
                                :paises.sync="allPaises"
                                :tipoDocumentos.sync="allTipoDocumentos"
                                :provincias.sync="allProvinciaFromPais"/>
                        <create-edit-form :profissao.sync="profissao"
                                          :situacaoPrisional.sync="situacaoPrisional"
                                          :dataSituacaoPrisional.sync="suspeito.dataSituacaoPrisional"
                                          :situacaoPrisionalList.sync="allSituacaoPrisional"
                                          :profissaoList.sync="allProfissao"/>
                      </q-form>
            </q-card-section>
            <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createSuspeito" color="teal" label="Gravar" />
            <q-btn label="Cancelar" type="reset" @click="close" color="negative" v-close-popup />
        </q-card-actions>
        </q-card>
    </q-dialog>
   </div>
  </q-page>
</template>

<script>
import { mapActions } from 'vuex'
import { exportFile } from 'quasar'

function wrapCsvValue (val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Suspeito',
  data () {
    return {
      listErrors: [],
      suspeito_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      suspeito: {
        nome: '',
        apelido: '',
        sexo: '',
        dataNascimento: '',
        naturalidade: '',
        nacionalidade: '',
        localNascimento: '',
        telemovel1: '',
        telemovel2: '',
        estadoCivil: '',
        morada: '',
        numDocumentoIndentificacao: '',
        tipoDocumento: {},
        documentoValidade: '',
        descricaoOcorrencia: '',
        profissao: {},
        situacaoPrisional: {},
        dataSituacaoPrisional: '',
        pais: {},
        provincia: {},
        pecaProcesso: {}
      },
      pais: {
        codigo: '',
        designacao: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      tipoDocumento: {
        codigo: '',
        designacao: ''
      },
      profissao: {
        designacao: ''
      },
      situacaoPrisional: {
        designacao: ''
      },
      columns: [
        { name: 'nome', required: true, label: 'Nome', align: 'left', field: row => row.nome, format: val => `${val}`, sortable: true },
        { name: 'apelido', align: 'left', label: 'Apelido', field: row => row.apelido, format: val => `${val}`, sortable: true },
        { name: 'sexo', align: 'left', label: 'Gênero', field: row => row.sexo, format: val => `${val}`, sortable: true },
        { name: 'dataNascimento', align: 'left', label: 'Data de Nascimento', field: row => row.dataNascimento, format: val => `${val}`, sortable: true },
        { name: 'naturalidade', align: 'left', label: 'Naturalidade', field: row => row.naturalidade, format: val => `${val}`, sortable: true },
        { name: 'nacionalidade', align: 'left', label: 'Nacionalidade', field: row => row.nacionalidade, format: val => `${val}`, sortable: true },
        { name: 'provincia', align: 'left', label: 'Província', field: row => row.provincia, format: val => `${val}`, sortable: true },
        { name: 'localNascimento', align: 'left', label: 'Local de Nascimento', field: row => row.localNascimento, format: val => `${val}`, sortable: true },
        { name: 'estadoCivil', align: 'left', label: 'Estado Civil', field: row => row.estadoCivil, format: val => `${val}`, sortable: true },
        { name: 'tipoDocumento', align: 'left', label: 'Documento de Identificação', field: row => row.tipoDocumento, format: val => `${val}`, sortable: true },
        { name: 'numDocumentoIndentificacao', align: 'left', label: 'Número do Documento', field: row => row.numDocumentoIndentificacao, format: val => `${val}`, sortable: true },
        { name: 'documentoValidade', align: 'left', label: 'Validade do Documento', field: row => row.documentoValidade, format: val => `${val}`, sortable: true },
        { name: 'morada', align: 'left', label: 'Morada', field: row => row.morada, format: val => `${val}`, sortable: true },
        { name: 'profissao', align: 'left', label: 'Profissão', field: row => row.profissao, format: val => `${val}`, sortable: true },
        { name: 'situacaoPrisional', align: 'left', label: 'Situação Prisional', field: row => row.situacaoPrisional, format: val => `${val}`, sortable: true },
        { name: 'actions', label: 'Movimento', field: 'actions' }
      ],
      data: []
    }
  },
  preFetch ({ store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath }) {
  // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return store.dispatch('suspeito/getAllSuspeito')
  },
  props: [
    'autoEntrada'
  ],
  mounted () {
    this.$store.dispatch('profissao/getAllProfissao')
    this.$store.dispatch('situacaoPrisional/getAllSituacaoPrisional')
    this.$store.dispatch('suspeito/getAllSuspeito')
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('pais/getAllPais')
    this.$store.dispatch('tipoDocumentoIdentificacao/getAllTipoDocumentoIdentificacao')
  },
  components: {
    'create-edit-form': require('components/suspeito/createEditForm.vue').default,
    individuo: require('components/individuo/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allSuspeitos () {
      return this.$store.getters['suspeito/allSuspeito'].filter(suspeito => suspeito.pecaProcesso != null)
    },
    allSuspeitosFromPecaProcesso () {
      return this.allSuspeitos.filter(suspeito => suspeito.pecaProcesso.id === this.autoEntrada.id)
    },
    allPaises () {
      return this.$store.getters['pais/allPais']
    },
    allTipoDocumentos () {
      return this.$store.getters['tipoDocumentoIdentificacao/allTipoDocumentoIdentificacao']
    },
    allProvincias () {
      return this.$store.getters['provincia/allProvincia']
    },
    allProvinciaFromPais () {
      return this.allProvincias.filter(provincia => provincia.pais.id === this.pais.id)
    },
    allProfissao () {
      return this.$store.getters['profissao/allProfissao']
    },
    allSituacaoPrisional () {
      return this.$store.getters['situacaoPrisional/allSituacaoPrisional']
    }
  },
  methods: {
    ...mapActions('suspeito', ['getAllSuspeito', 'addNewSuspeito', 'updateSuspeito', 'deleteSuspeito']),
    createSuspeito () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.suspeito.nacionalidade = this.pais
      this.suspeito.provincia = this.provincia
      this.suspeito.tipoDocumento = this.tipoDocumento
      this.suspeito.profissao = this.profissao
      this.suspeito.situacaoPrisional = this.situacaoPrisional
      this.suspeito.pecaProcesso = this.autoEntrada
      console.log('Suspeito' + this.suspeito[0])
      if (this.editedIndex > -1) {
        this.updateSuspeito(this.suspeito).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.suspeito.nome + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log('erro update - ' + error)
          if (error.request.response != null) {
            const arrayErrors = JSON.parse(error.request.response)
            if (arrayErrors.total == null) {
              this.listErrors.push(arrayErrors.message)
            } else {
              arrayErrors._embedded.errors.forEach(element => {
                this.listErrors.push(element.message)
              })
            }
            console.log(this.listErrors)
          }
        })
      } else {
        this.addNewSuspeito(this.suspeito).then(resp => {
          console.log(resp)
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi inserida com successo! [ ' + this.suspeito.nome + ' ]'
          })
          this.close()
        }).catch(error => {
          console.log('Erro no code ' + error)
          if (error.request.status !== 0) {
            const arrayErrors = JSON.parse(error.request.response)
            if (arrayErrors.total == null) {
              this.listErrors.push(arrayErrors.message)
            } else {
              arrayErrors._embedded.errors.forEach(element => {
                this.listErrors.push(element.message)
              })
            }
            console.log(this.listErrors)
          }
        })
      }
    },
    close () {
      this.$store.dispatch('suspeito/getAllSuspeito')
      this.$store.dispatch('provincia/getAllProvincia')
      this.$store.dispatch('pais/getAllPais')
      this.$store.dispatch('tipoDocumentoIdentificacao/getAllTipoDocumentoIdentificacao')
      this.show_dialog = false
      this.suspeito = {}
      this.props = this.suspeito
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeSuspeito (suspeito) {
      this.$q.dialog({
        title: 'Confirmação',
        message: 'Tem certeza que pretende remover?',
        cancel: true,
        persistent: true
      }).onOk(() => {
        this.$q.notify({
          type: 'warning',
          color: 'primary',
          textColor: 'white',
          icon: 'cloud_done',
          timeout: 2000,
          position: 'bottom',
          classes: 'glossy',
          progress: true,
          message: 'A informação foi Removida com successo! [ ' + suspeito.nome + ' ]'
        })
        this.deleteSuspeito(suspeito)
      })
    },
    editaSuspeito (suspeito) {
      this.editedIndex = this.allSuspeitos.indexOf(suspeito)
      this.suspeito = Object.assign({}, suspeito)
      this.suspeito.nacionalidade = this.pais
      this.suspeito.provincia = this.provincia
      this.suspeito.tipoDocumento = this.tipoDocumento
      this.suspeito.profissao = this.profissao
      this.suspeito.situacaoPrisional = this.situacaoPrisional
      this.suspeito.pecaProcesso = this.autoEntrada
      this.show_dialog = true
    },
    getProvincia (id) {
      const localProvincias = this.allProvincias.filter(provincia => provincia.id === id)
      if (localProvincias.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localProvincias[0] }
    },
    getPais (id) {
      const localPais = this.allPaises.filter(pais => pais.id === id)
      if (localPais.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localPais[0] }
    },
    getTipoDocumento (id) {
      const localTipoDocumento = this.allTipoDocumentos.filter(tipoDocumento => tipoDocumento.id === id)
      if (localTipoDocumento.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localTipoDocumento[0] }
    },
    getProfissao (id) {
      const localProfissao = this.allProfissao.filter(profissao => profissao.id === id)
      if (localProfissao.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localProfissao[0] }
    },
    getSituacaoPrisional (id) {
      const localSituacaoPrisional = this.allSituacaoPrisional.filter(situacaoPrisional => situacaoPrisional.id === id)
      if (localSituacaoPrisional.length === 0) { return Object.assign({}, { designacao: 'Sem Info.' }) } else { return localSituacaoPrisional[0] }
    },
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.suspeito.suspeitos.map(row =>
            this.columns
              .map(col =>
                wrapCsvValue(
                  typeof col.field === 'function'
                    ? col.field(row)
                    : row[col.field === undefined ? col.name : col.field],
                  col.format
                )
              )
              .join(',')
          )
        )
        .join('\r\n')
      const status = exportFile('suspeito_list.csv', content, 'text/csv')
      if (status !== true) {
        this.$q.notify({
          message: 'O navegador recusou o download...',
          color: 'negative',
          icon: 'warning'
        })
      }
    }
  }
}
</script>
