<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allOfendidosFromPecaProcesso" :filter="filter" binary-state-sort row-key="name"
             title="Ofendido/Vítima">

      <template v-slot:top-right>
        <q-input v-if="show_filter" v-model="filter" borderless debounce="300" dense filled placeholder="Pesquisa">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>

        <div class="q-pa-md q-gutter-sm">
          <q-btn class="q-ml-sm" flat icon="filter_list" @click="show_filter=!show_filter"/>
          <q-btn color="primary" label="Adicionar Novo" no-caps outline rounded @click="show_dialog = true"/>
        </div>
      </template>
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td key="nome" :props="props">
            {{ props.row.ofendido.nome }}
            <q-popup-edit v-model="props.row.ofendido.nome">
              <q-input v-model="props.row.ofendido.nome" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="apelido" :props="props">
            {{ props.row.ofendido.apelido }}
            <q-popup-edit v-model="props.row.ofendido.apelido">
              <q-input v-model="props.row.ofendido.apelido" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="sexo" :props="props">
            {{ props.row.ofendido.sexo }}
            <q-popup-edit v-model="props.row.ofendido.sexo">
              <q-input v-model="props.row.ofendido.sexo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataNascimento" :props="props">
            {{ props.row.ofendido.dataNascimento }}
            <q-popup-edit v-model="props.row.ofendido.dataNascimento">
              <q-input v-model="props.row.ofendido.dataNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="naturalidade" :props="props">
            {{ props.row.ofendido.naturalidade }}
            <q-popup-edit v-model="props.row.ofendido.naturalidade">
              <q-input v-model="props.row.ofendido.naturalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nacionalidade" :props="props">
            {{ props.row.ofendido.nacionalidade.nacionalidade }}
            <q-popup-edit v-model="props.row.ofendido.nacionalidade.nacionalidade">
              <q-input v-model="props.row.ofendido.nacionalidade.nacionalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            {{ props.row.ofendido.provincia.designacao }}
            <q-popup-edit v-model="props.row.ofendido.provincia.designacao">
              <q-input v-model="props.row.ofendido.provincia.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="localNascimento" :props="props">
            {{ props.row.ofendido.localNascimento }}
            <q-popup-edit v-model="props.row.ofendido.localNascimento">
              <q-input v-model="props.row.ofendido.localNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="estadoCivil" :props="props">
            {{ props.row.ofendido.estadoCivil }}
            <q-popup-edit v-model="props.row.ofendido.estadoCivil">
              <q-input v-model="props.row.ofendido.estadoCivil" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoDocumento" :props="props">
            {{ props.row.ofendido.tipoDocumento.designacao }}
            <q-popup-edit v-model="props.row.ofendido.tipoDocumento.designacao">
              <q-input v-model="props.row.ofendido.tipoDocumento.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="numDocumentoIndentificacao" :props="props">
            {{ props.row.ofendido.numDocumentoIndentificacao }}
            <q-popup-edit v-model="props.row.ofendido.numDocumentoIndentificacao">
              <q-input v-model="props.row.ofendido.numDocumentoIndentificacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="documentoValidade" :props="props">
            {{ props.row.ofendido.documentoValidade }}
            <q-popup-edit v-model="props.row.ofendido.documentoValidade">
              <q-input v-model="props.row.ofendido.documentoValidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="morada" :props="props">
            {{ props.row.ofendido.morada }}
            <q-popup-edit v-model="props.row.ofendido.morada">
              <q-input v-model="props.row.ofendido.morada" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaOfendido(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeOfendido(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar Ofendido/Vítima!</div>
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
          <q-separator/>
          <q-card-section class="scroll" style="max-height: 70vh">
            <q-form class="q-gutter-md" @submit.prevent="createOfendido">
              <individuo :apelido.sync="ofendido.apelido"
                         :dataNascimento.sync="ofendido.dataNascimento"
                         :documentoValidade.sync="ofendido.documentoValidade"
                         :estadoCivil.sync="ofendido.estadoCivil"
                         :fotografia.sync="ofendido.fotografia"
                         :idade.sync="ofendido.idade"
                         :localNascimento.sync="ofendido.localNascimento"
                         :morada.sync="ofendido.morada"
                         :naturalidade.sync="ofendido.naturalidade"
                         :nome.sync="ofendido.nome"
                         :numDocumentoIndentificacao.sync="ofendido.numDocumentoIndentificacao"
                         :pais.sync="pais"
                         :paises.sync="allPaises"
                         :provincia.sync="provincia"
                         :provincias.sync="allProvinciaFromPais"
                         :sexo.sync="ofendido.sexo"
                         :telemovel1.sync="ofendido.telemovel1"
                         :telemovel2.sync="ofendido.telemovel2"
                         :tipoDocumento.sync="tipoDocumento"
                         :tipoDocumentos.sync="allTipoDocumentos"
                         :onFileChange.sync="onFileChange"
                         :image.sync="image"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createOfendido"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Pais from 'src/store/models/pais/pais'
import TipoDocumentoIdentificacao from 'src/store/models/tipoDocumentoIdentificacao/tipoDocumentoIdentificacao'
import Provincia from 'src/store/models/provincia/provincia'
import Ofendido from 'src/store/models/ofendido/ofendido'
import PecaProcessoOfendido from 'src/store/models/pecaProcesso/pecaProcessoOfendido'
import PecaProcesso from 'src/store/models/pecaProcesso/pecaProcesso'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Ofendido',
  data() {
    return {
      listErrors: [],
      ofendido_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      image: '',
      ofendido: {
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
        provincia: {}
      },
       pecaProcessoOfendido: {
        ofendido: {},
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
        {
          name: 'nome',
          required: true,
          label: 'Nome',
          align: 'left',
          field: row => row.nome,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'apelido',
          align: 'left',
          label: 'Apelido',
          field: row => row.apelido,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'sexo', align: 'left', label: 'Gênero', field: row => row.sexo, format: val => `${val}`, sortable: true},
        {
          name: 'dataNascimento',
          align: 'left',
          label: 'Data de Nascimento',
          field: row => row.dataNascimento,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'naturalidade',
          align: 'left',
          label: 'Naturalidade',
          field: row => row.naturalidade,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'nacionalidade',
          align: 'left',
          label: 'Nacionalidade',
          field: row => row.nacionalidade,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'provincia',
          align: 'left',
          label: 'Província',
          field: row => row.provincia,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'localNascimento',
          align: 'left',
          label: 'Local de Nascimento',
          field: row => row.localNascimento,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'estadoCivil',
          align: 'left',
          label: 'Estado Civil',
          field: row => row.estadoCivil,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'tipoDocumento',
          align: 'left',
          label: 'Documento de Identificação',
          field: row => row.tipoDocumento,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'numDocumentoIndentificacao',
          align: 'left',
          label: 'Número do Documento',
          field: row => row.numDocumentoIndentificacao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'documentoValidade',
          align: 'left',
          label: 'Validade do Documento',
          field: row => row.documentoValidade,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'morada',
          align: 'left',
          label: 'Morada',
          field: row => row.morada,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'actions', label: 'Movimento', field: 'actions'}
      ],
      data: []
    }
  },
  preFetch({}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return  this.getAllOfendido()
  },
  props: [
    'pecaProcesso'
  ],
  mounted() {
    this.getAllOfendido()
    this.getAllOfendidoPecaProcesso()
    this.getAllProvincia()
    this.getAllPais()
    this.getAllTipoDocumentoIdentificacao()
  },
  components: {
    individuo: require('components/individuo/createEditForm.vue').default
  },
    created() {
  },
  metaInfo: {},
  computed: {
   allOfendidosFromPecaProcesso() {
      return PecaProcesso.query().with('ofendidos')
                                 .with('ofendidos.ofendido')
                                 .with('ofendidos.ofendido.nacionalidade')
                                 .with('ofendidos.ofendido.provincia')
                                 .with('ofendidos.ofendido.tipoDocumento')
                                 .with('ofendidos.ofendido.profissao')
                                 .find(this.pecaProcesso.id).ofendidos
    },
     // return .query().with('provincia').with('nacionalidade').with('tipoDocumento').where('pecaProcesso_id', this.pecaProcesso.id).get()
    
    allPaises() {
      return Pais.query().all()
    },
    allTipoDocumentos() {
      return TipoDocumentoIdentificacao.query().all()
    },
    allProvincias() {
      return Provincia.query().all()
    },
    allProvinciaFromPais() {
      return Provincia.query().where('pais_id',this.pais.id).get()
    }
  },
  methods: {
    createOfendido() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.provincia.pais = this.pais
      this.ofendido.nacionalidade_id = this.pais.id
      this.ofendido.provincia_id = this.provincia.id
      this.ofendido.tipoDocumento_id = this.tipoDocumento.id
      this.ofendido.pecaProcesso_id = this.pecaProcesso.id
      this.ofendido.nacionalidade = this.pais
      this.ofendido.provincia = this.provincia
      this.ofendido.tipoDocumento = this.tipoDocumento

      this.pecaProcessoOfendido.ofendido = this.ofendido
      this.pecaProcessoOfendido.pecaProcesso = this.pecaProcesso

      if (this.editedIndex > -1) {
        PecaProcessoOfendido.api().patch("/pecaProcessoOfendido/" + this.pecaProcessoOfendido.id, this.pecaProcessoOfendido).then(() => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.ofendido.nome + ' ]'
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
       PecaProcessoOfendido.api().post("/pecaProcessoOfendido/", this.pecaProcessoOfendido).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.ofendido.nome + ' ]'
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
    close() {
      this.getAllOfendidoPecaProcesso()
      this.getAllOfendido()
      this.getAllProvincia()
      this.getAllPais()
      this.getAllTipoDocumentoIdentificacao()
      this.listErrors = {}
      this.show_dialog = false
      this.ofendido = {}
      this.props = this.ofendido
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeOfendido(ofendido) {
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
          message: 'A informação foi Removida com successo! [ ' + ofendido.nome + ' ]'
        })
        PecaProcessoOfendido.api().delete("/pecaProcessoOfendido/" + this.pecaProcessoOfendido.id)
      })
    },
    editaOfendido(ofendido) {
      this.editedIndex = 0
      this.pecaProcessoOfendido = Object.assign({}, ofendido)
      this.ofendido =  this.pecaProcessoOfendido.ofendido
      this.pais = Pais.query().find(this.ofendido.nacionalidade_id)
      this.provincia = Provincia.query().find(this.ofendido.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.ofendido.tipoDocumento_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.ofendido.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    getAllOfendido() {
      Ofendido.api().get('/ofendido?offset=0&max=1000000')
    }, 
     getAllOfendidoPecaProcesso() {
      PecaProcessoOfendido.api().get('/pecaProcessoOfendido?offset=0&max=1000000')
    },
    getAllTipoDocumentoIdentificacao() {
      TipoDocumentoIdentificacao.api().get('/tipoDocumentoIdentificacao?offset=0&max=1000000')
    },
    getAllProvincia() {
      Provincia.api().get('/provincia?offset=0&max=1000000')
    },
    getAllPais() {
      Pais.api().get('/pais?offset=0&max=1000000')
    },
    onFileChange(event) {
      this.ofendido.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.ofendido.ofendidos.map(row =>
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
      const status = exportFile('ofendido_list.csv', content, 'text/csv')
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
