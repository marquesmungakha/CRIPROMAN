<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allDenunciantesFromPecaProcesso" :filter="filter" binary-state-sort
             row-key="name" title="Denunciante">

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
            {{ props.row.denunciante.nome}}
            <q-popup-edit v-model="props.row.denunciante.nome">
              <q-input v-model="props.row.denunciante.nome" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="apelido" :props="props">
            {{ props.row.denunciante.apelido }}
            <q-popup-edit v-model="props.row.denunciante.apelido">
              <q-input v-model="props.row.denunciante.apelido" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="sexo" :props="props">
            {{ props.row.denunciante.sexo }}
            <q-popup-edit v-model="props.row.denunciante.sexo">
              <q-input v-model="props.row.denunciante.sexo" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="dataNascimento" :props="props">
            {{ props.row.denunciante.dataNascimento }}
            <q-popup-edit v-model="props.row.denunciante.dataNascimento">
              <q-input v-model="props.row.denunciante.dataNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="naturalidade" :props="props">
            {{ props.row.denunciante.naturalidade }}
            <q-popup-edit v-model="props.row.denunciante.naturalidade">
              <q-input v-model="props.row.denunciante.naturalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nacionalidade" :props="props">
            {{ props.row.denunciante.nacionalidade.nacionalidade }}
            <q-popup-edit v-model="props.row.denunciante.nacionalidade.nacionalidade">
              <q-input v-model="props.row.denunciante.nacionalidade.nacionalidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="provincia" :props="props">
            {{ props.row.denunciante.provincia.designacao }}
            <q-popup-edit v-model="props.row.denunciante.provincia.designacao">
              <q-input v-model="props.row.denunciante.provincia.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="localNascimento" :props="props">
            {{ props.row.denunciante.localNascimento }}
            <q-popup-edit v-model="props.row.denunciante.localNascimento">
              <q-input v-model="props.row.denunciante.localNascimento" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="estadoCivil" :props="props">
            {{ props.row.denunciante.estadoCivil }}
            <q-popup-edit v-model="props.row.denunciante.estadoCivil">
              <q-input v-model="props.row.denunciante.estadoCivil" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="tipoDocumento" :props="props">
            {{ props.row.denunciante.tipoDocumento.designacao }}
            <q-popup-edit v-model="props.row.denunciante.tipoDocumento.designacao">
              <q-input v-model="props.row.denunciante.tipoDocumento.designacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="numDocumentoIndentificacao" :props="props">
            {{ props.row.denunciante.numDocumentoIndentificacao }}
            <q-popup-edit v-model="props.row.denunciante.numDocumentoIndentificacao">
              <q-input v-model="props.row.denunciante.numDocumentoIndentificacao" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="documentoValidade" :props="props">
            {{ props.row.denunciante.documentoValidade }}
            <q-popup-edit v-model="props.row.denunciante.documentoValidade">
              <q-input v-model="props.row.denunciante.documentoValidade" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="morada" :props="props">
            {{ props.row.denunciante.morada }}
            <q-popup-edit v-model="props.row.denunciante.morada">
              <q-input v-model="props.row.denunciante.morada" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="descricaoOcorrencia" :props="props">
            {{ props.row.descricaoOcorrencia }}
            <q-popup-edit v-model="props.row.descricaoOcorrencia">
              <q-input v-model="props.row.descricaoOcorrencia" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="qualidadeDe" :props="props">
            {{ props.row.qualidadeDe }}
            <q-popup-edit v-model="props.row.qualidadeDe">
              <q-input v-model="props.row.qualidadeDe" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaDenunciante(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click="removeDenunciante(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar Denunciante!</div>
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
            <q-form class="q-gutter-md" @submit.prevent="createDenunciante">
              <individuo :apelido.sync="denunciante.apelido"
                         :dataNascimento.sync="denunciante.dataNascimento"
                         :documentoValidade.sync="denunciante.documentoValidade"
                         :estadoCivil.sync="denunciante.estadoCivil"
                         :fotografia.sync="denunciante.fotografia"
                         :idade.sync="denunciante.idade"
                         :localNascimento.sync="denunciante.localNascimento"
                         :morada.sync="denunciante.morada"
                         :naturalidade.sync="denunciante.naturalidade"
                         :nome.sync="denunciante.nome"
                         :numDocumentoIndentificacao.sync="denunciante.numDocumentoIndentificacao"
                         :pais.sync="pais"
                         :paises.sync="allPaises"
                         :provincia.sync="provincia"
                         :provincias.sync="allProvinciaFromPais"
                         :sexo.sync="denunciante.sexo"
                         :telemovel1.sync="denunciante.telemovel1"
                         :telemovel2.sync="denunciante.telemovel2"
                         :tipoDocumento.sync="tipoDocumento"
                         :tipoDocumentos.sync="allTipoDocumentos"
                         :onFileChange.sync="onFileChange"
                         :image.sync="image"/>
      <create-edit-form :descricaoOcorrencia.sync="pecaProcessoDenunciante.descricaoOcorrencia"
                        :localTrabalho.sync="pecaProcessoDenunciante.localTrabalho"
                        :ocupacao.sync="pecaProcessoDenunciante.ocupacao"
                        :qualidadeDe.sync="pecaProcessoDenunciante.qualidadeDe"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createDenunciante"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import {exportFile} from 'quasar'
import Denunciante from 'src/store/models/denunciante/denunciante'
import Pais from 'src/store/models/pais/pais'
import TipoDocumentoIdentificacao from 'src/store/models/tipoDocumentoIdentificacao/tipoDocumentoIdentificacao'
import Provincia from 'src/store/models/provincia/provincia'
import PecaProcessoDenunciante from 'src/store/models/pecaProcesso/pecaProcessoDenunciante'
import PecaProcesso from 'src/store/models/pecaProcesso/pecaProcesso'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Denunciante',
  data() {
    return {
      listErrors: [],
      denunciante_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      image: '',
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
      pecaProcessoDenunciante: {
        descricaoOcorrencia: '',
        localTrabalho: '',
        ocupacao: '',
        qualidadeDe: '',
        denunciante: {},
        pecaProcesso: {}
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
        {
          name: 'descricaoOcorrencia',
          align: 'left',
          label: 'Descrição da Ocorrencia',
          field: row => row.descricao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'qualidadeDe',
          align: 'left',
          label: 'Na qualidade De',
          field: row => row.qualidadeDe,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'actions', label: 'Movimento', field: 'actions'}
      ],
      data: []
    }
  },
  preFetch({store, currentRoute, previousRoute, redirect, ssrContext, urlPath, publicPath}) {
    // urlPath and publicPath requires @quasar/app v2+

    // fetch data, validate route and optionally redirect to some other route...

    // ssrContext is available only server-side in SSR mode

    // No access to "this" here as preFetch() is called before
    // the component gets instantiated.

    // Return a Promise if you are running an async job
    // Example:
    return this.getAllDenunciante()
  },
  props: [
    'pecaProcesso'
  ],
  mounted() {
    this.getAllDenunciante()
    this.getAllDenuncianteProcesso()
    this.getAllProvincia()
    this.getAllPais()
    this.getAllTipoDocumentoIdentificacao()
  },
  components: {
    'create-edit-form': require('components/denunciante/createEditForm.vue').default,
    individuo: require('components/individuo/createEditForm.vue').default
  },
  created() {
  },
  metaInfo: {},
  computed: {
    allDenunciantesFromPecaProcesso() {
      return PecaProcesso.query().with('denunciantes')
                                 .with('denunciantes.denunciante')
                                 .with('denunciantes.denunciante.nacionalidade')
                                 .with('denunciantes.denunciante.provincia')
                                 .with('denunciantes.denunciante.tipoDocumento')
                                 .with('denunciantes.denunciante.profissao')
                                 .find(this.pecaProcesso.id).denunciantes
    },
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
    createDenunciante() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.provincia.pais = this.pais
      this.denunciante.nacionalidade_id = this.pais.id
      this.denunciante.provincia_id = this.provincia.id
      this.denunciante.tipoDocumento_id = this.tipoDocumento.id
      this.denunciante.pecaProcesso_id = this.pecaProcesso.id
      this.denunciante.nacionalidade = this.pais
      this.denunciante.provincia = this.provincia
      this.denunciante.tipoDocumento = this.tipoDocumento
      this.denunciante.pecaProcesso = this.pecaProcesso

      this.pecaProcessoDenunciante.denunciante =  this.denunciante
      this.pecaProcessoDenunciante.pecaProcesso = this.pecaProcesso
     
     console.log(this.pecaProcessoDenunciante)

      if (this.editedIndex > -1) {
         PecaProcessoDenunciante.api().patch("/pecaProcessoDenunciante/" + this.pecaProcessoDenunciante.id, this.pecaProcessoDenunciante).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [' + this.pecaProcessoDenunciante.denunciante.nome + ' ]'
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
        PecaProcessoDenunciante.api().post("/pecaProcessoDenunciante/", this.pecaProcessoDenunciante).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.pecaProcessoDenunciante.denunciante.nome + ' ]'
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
      this.getAllDenuncianteProcesso()
      this.getAllDenunciante()
      this.getAllProvincia()
      this.getAllPais()
      this.getAllTipoDocumentoIdentificacao()
      this.listErrors = {}
      this.show_dialog = false
      this.denunciante = {}
      this.props = this.denunciante
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeDenunciante(denunciante) {
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
         Denunciante.api().delete("/pecaProcessoDenunciante/" + this.denunciante.id)
      })
    },
    editaDenunciante(denunciante) {
      this.editedIndex = 0
      this.pecaProcessoDenunciante = Object.assign({}, denunciante)
      this.denunciante =  this.pecaProcessoDenunciante.denunciante
      this.pais = Pais.query().find(this.denunciante.nacionalidade_id)
      this.provincia = Provincia.query().find(this.denunciante.provincia_id)
      this.tipoDocumento = TipoDocumentoIdentificacao.query().find(this.denunciante.tipoDocumento_id)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(this.denunciante.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    getAllDenunciante() {
      Denunciante.api().get('/denunciante?offset=0&max=1000000')
    },
     getAllDenuncianteProcesso() {
      PecaProcessoDenunciante.api().get('/pecaProcessoDenunciante?offset=0&max=1000000')
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
    onFileChange(event){
      this.denunciante.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
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
