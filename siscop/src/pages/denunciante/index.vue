<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Denunciante" :data="allDenunciantesFromPecaProcesso" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="descricaoOcorrencia" :props="props">
              {{ props.row.descricaoOcorrencia }}
              <q-popup-edit v-model="props.row.descricaoOcorrencia">
                <q-input v-model="props.row.descricaoOcorrencia" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="qualidadeDe" :props="props">
              {{ props.row.qualidadeDe }}
              <q-popup-edit v-model="props.row.qualidadeDe" >
                <q-input v-model="props.row.qualidadeDe" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <q-btn round glossy icon="edit" color="blue" @click="editaDenunciante(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeDenunciante(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
   <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
         <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Denunciante!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
            <q-separator />
            <q-card-section style="max-height: 70vh" class="scroll">
                    <q-form @submit.prevent="createDenunciante" class="q-gutter-md">
                      <individuo :nome.sync="denunciante.nome"
                                :sexo.sync="denunciante.sexo"
                                :telemovel2.sync="denunciante.telemovel2"
                                :apelido.sync="denunciante.apelido"
                                :telemovel1.sync="denunciante.telemovel1"
                                :fotografia.sync="denunciante.fotografia"
                                :idade.sync="denunciante.idade"
                                :estadoCivil.sync="denunciante.estadoCivil"
                                :naturalidade.sync="denunciante.naturalidade"
                                :dataNascimento.sync="denunciante.dataNascimento"
                                :localNascimento.sync="denunciante.localNascimento"
                                :morada.sync="denunciante.morada"
                                :documentoValidade.sync="denunciante.documentoValidade"
                                :numDocumentoIndentificacao.sync="denunciante.numDocumentoIndentificacao"
                                :pais.sync="pais"
                                :provincia.sync="provincia"
                                :tipoDocumento.sync="tipoDocumento"
                                :paises.sync="allPaises"
                                :tipoDocumentos.sync="allTipoDocumentos"
                                :provincias.sync="allProvinciaFromPais"/>
                        <create-edit-form :descricaoOcorrencia.sync="denunciante.descricaoOcorrencia"
                                          :localTrabalho.sync="denunciante.localTrabalho"
                                          :ocupacao.sync="denunciante.ocupacao"
                                          :qualidadeDe.sync="denunciante.qualidadeDe"/>
                      </q-form>
            </q-card-section>
            <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createDenunciante" color="teal" label="Gravar" />
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
  name: 'Denunciante',
  data () {
    return {
      listErrors: [],
      denunciante_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      denunciante: {
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
        localTrabalho: '',
        ocupacao: '',
        qualidadeDe: '',
        pais: {},
        provincia: {},
        pecaProcesso: {}
      },
      pais: {
        codigo: '',
        designacao: '',
        nacionalidade: ''
      },
      provincia: {
        codigo: '',
        designacao: ''
      },
      tipoDocumento: {
        codigo: '',
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
        { name: 'descricaoOcorrencia', align: 'left', label: 'Descrição da Ocorrencia', field: row => row.descricao, format: val => `${val}`, sortable: true },
        { name: 'qualidadeDe', align: 'left', label: 'Na qualidade De', field: row => row.qualidadeDe, format: val => `${val}`, sortable: true },
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
    return store.dispatch('denunciante/getAllDenunciante')
  },
  props: [
    'autoEntrada'
  ],
  mounted () {
    this.$store.dispatch('denunciante/getAllDenunciante')
    this.$store.dispatch('provincia/getAllProvincia')
    this.$store.dispatch('pais/getAllPais')
    this.$store.dispatch('tipoDocumentoIdentificacao/getAllTipoDocumentoIdentificacao')
  },
  components: {
    'create-edit-form': require('components/denunciante/createEditForm.vue').default,
    individuo: require('components/individuo/createEditForm.vue').default
  },
  metaInfo: {
  },
  computed: {
    allDenunciantes () {
      return this.$store.getters['denunciante/allDenunciante'].filter(denunciante => denunciante.pecaProcesso != null)
    },
    allDenunciantesFromPecaProcesso () {
      return this.allDenunciantes.filter(denunciante => denunciante.pecaProcesso.id === this.autoEntrada.id)
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
    }
  },
  methods: {
    ...mapActions('denunciante', ['getAllDenunciante', 'addNewDenunciante', 'updateDenunciante', 'deleteDenunciante']),
    createDenunciante () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.denunciante.nacionalidade = this.pais
      this.denunciante.provincia = this.provincia
      this.denunciante.tipoDocumento = this.tipoDocumento
      this.denunciante.pecaProcesso = this.autoEntrada
      console.log('Denunciante' + this.denunciante[0])
      if (this.editedIndex > -1) {
        this.updateDenunciante(this.denunciante).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.denunciante.nome + ' ]'
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
        this.addNewDenunciante(this.denunciante).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.denunciante.nome + ' ]'
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
      this.$store.dispatch('denunciante/getAllDenunciante')
      this.$store.dispatch('provincia/getAllProvincia')
      this.$store.dispatch('pais/getAllPais')
      this.$store.dispatch('tipoDocumentoIdentificacao/getAllTipoDocumentoIdentificacao')
      this.show_dialog = false
      this.denunciante = {}
      this.props = this.denunciante
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeDenunciante (denunciante) {
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
          message: 'A informação foi Removida com successo! [ ' + denunciante.nome + ' ]'
        })
        this.deleteDenunciante(denunciante)
      })
    },
    editaDenunciante (denunciante) {
      this.editedIndex = this.allDenunciantes.indexOf(denunciante)
      this.denunciante = Object.assign({}, denunciante)
      this.denunciante.nacionalidade = this.pais
      this.denunciante.provincia = this.provincia
      this.denunciante.tipoDocumento = this.tipoDocumento
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
    exportTable () {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.denunciante.denunciantes.map(row =>
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
      const status = exportFile('denunciante_list.csv', content, 'text/csv')
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
