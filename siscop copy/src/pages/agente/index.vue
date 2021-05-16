<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allAgentes" :filter="filter" binary-state-sort row-key="name" title="Agente">

      <template v-slot:top-right>
        <q-input v-if="show_filter" v-model="filter" borderless debounce="300" dense filled placeholder="Pesquisa">
          <template v-slot:append>
            <q-icon name="search"/>
          </template>
        </q-input>

        <div class="q-pa-md q-gutter-sm">
          <q-btn class="q-ml-sm" flat icon="filter_list" @click="show_filter=!show_filter"/>
          <q-btn color="primary" label="Adicionar Novo" no-caps outline rounded @click="show_dialog = true"/>
          <q-btn color="primary" icon-right="archive" label="Imprimir em Excel" no-caps rounded @click="exportTable"/>
        </div>
      </template>
      <template v-slot:body="props">
        <q-tr :props="props">
          <q-td key="numero" :props="props">
            {{ props.row.numero }}
            <q-popup-edit v-model="props.row.numero">
              <q-input v-model="props.row.numero" autofocus counter dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="nome" :props="props">
            {{ props.row.nome }}
            <q-popup-edit v-model="props.row.nome" title="Update nome">
              <q-input v-model="props.row.nome" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="apelido" :props="props">
            {{ props.row.apelido }}
            <q-popup-edit v-model="props.row.apelido" title="Update apelido">
              <q-input v-model="props.row.apelido" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="sexo" :props="props">
            {{ props.row.sexo }}
            <q-popup-edit v-model="props.row.sexo" title="Update sexo">
              <q-input v-model="props.row.sexo" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="idade" :props="props">
            {{ props.row.idade }}
            <q-popup-edit v-model="props.row.idade" title="Update Idade">
              <q-input v-model="props.row.idade" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="cargoOcupado" :props="props">
            {{ props.row.cargoOcupado }}
            <q-popup-edit v-model="props.row.cargoOcupado" title="Update cargoOcupado">
              <q-input v-model="props.row.cargoOcupado" autofocus dense></q-input>
            </q-popup-edit>
          </q-td>
          <q-td key="actions" :props="props">
            <div class="q-gutter-sm">
              <router-link :to="`/agente/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm />
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaAgente(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm @click="removeAgente(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar/Actualizar Agente!</div>
          </q-card-section>
          <q-card-section>
            <div v-if="listErrors.length > 0" class="q-pa-sm q-gutter-sm"
                 style="max-width: 550px; max-height: 150px;border-radius: 10px; border: 1px solid #cb4646; margin: 5px; background-color: #ead8da">
              <ul class="list-group alert alert-danger">
                <li v-for="item in listErrors"
                    :key="item" class="list-group-item text-negative q-pl-xs text-weight-regular text-caption">
                  {{ item }}
                </li>
              </ul>
            </div>
          </q-card-section>
          <q-separator/>
          <q-card-section class="scroll" style="max-height: 70vh">
            <q-form class="q-gutter-md" @submit.prevent="createAgente">
              <quadro :apelido.sync="agente.apelido"
                      :fotografia.sync="agente.fotografia"
                      :idade.sync="agente.idade"
                      :nome.sync="agente.nome"
                      :numero.sync="agente.numero"
                      :sexo.sync="agente.sexo"
                      :telemovel1.sync="agente.telemovel1"
                      :telemovel2.sync="agente.telemovel2"
                      :onFileChange.sync="onFileChange"
                      :image.sync="image"/>
              <create-edit-form :cargoOcupado.sync="agente.cargoOcupado"
                                :descricao.sync="agente.descricao"
                                :show_dialog="show_dialog"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createAgente"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Agente from 'src/store/models/agente/agente'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Agente',
  data() {
    return {
      listErrors: [],
      agente_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      image: '',
      agente: {
        nome: '',
        sexo: '',
        idade: '',
        telemovel2: '',
        apelido: '',
        telemovel1: '',
        fotografia: '',
        descricao: '',
        numero: '',
        cargoOcupado: ''
      },
      columns: [
        {
          name: 'numero',
          required: true,
          label: 'Número do Agente',
          align: 'left',
          field: row => row.sigla,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'nome',
          align: 'left',
          label: 'Nome',
          field: row => row.designacao,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'apelido',
          align: 'left',
          label: 'Apelido',
          field: row => row.tipo_agente.id,
          format: val => `${val}`,
          sortable: true
        },
        {name: 'sexo', align: 'left', label: 'Sexo', field: row => row.nivel, format: val => `${val}`, sortable: true},
        {
          name: 'idade',
          align: 'left',
          label: 'Idade',
          field: row => row.idade,
          format: val => `${val}`,
          sortable: true
        },
        {
          name: 'cargoOcupado',
          align: 'left',
          label: 'Cargo',
          field: row => row.cargoOcupado,
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
    return this.getAllAgente()
  },
  mounted() {
    this.getAllAgente()
  },
  components: {
    'create-edit-form': require('components/agente/createEditForm.vue').default,
    quadro: require('components/quadro/createEditFields.vue').default
  },
  created() {
    this.$q.loading.show({
      message: "Carregando ...",
      spinnerColor: "grey-4",
      spinner: QSpinnerBall
      // delay: 400 // ms
    })

    setTimeout(() => {
      this.$q.loading.hide()
    }, 600)

  },
  metaInfo: {},
  computed: {
    allAgentes() {
      return Agente.query().all()
    },
    dataUrl(){
      return 'data:image/jpeg;base64,' + btoa(
            new Uint8Array(agente.fotografia)
            .reduce((data, byte) => data + String.fromCharCode(byte), '')
        );
    }
  },
  methods: {
    createAgente() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)

      let formData = new FormData();
      formData.append("nome", this.agente.nome);
      formData.append("apelido", this.agente.apelido);
      formData.append("sexo", this.agente.sexo);
      formData.append("idade", this.agente.idade);
      formData.append("telemovel2", this.agente.telemovel2);
      formData.append("telemovel1", this.agente.telemovel1);
      formData.append("fotografia", this.agente.fotografia);
      formData.append("descricao", this.agente.descricao);
      formData.append("numero", this.agente.numero);
      formData.append("cargoOcupado", this.agente.cargoOcupado);

      if (this.editedIndex > -1) {
        Agente.api().patch("/agente/" + this.agente.id, formData).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.agente.designacao + ' ]'
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
        Agente.api().post("/agente/", formData).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.agente.designacao + ' ]'
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
      this.getAllAgente()
      this.show_dialog = false
      this.image = ''
      this.agente = {}
      this.props = this.agente
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeAgente(agente) {
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
          message: 'A informação foi Removida com successo! [ ' + agente.designacao + ' ]'
        })
        Agente.api().delete("/agente/" + agente.id)
      })
    },
    editaAgente(agente) {
      this.editedIndex = 0
      this.agente = Object.assign({}, agente)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(agente.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    getAllAgente() {
      Agente.api().get('/agente?offset=0&max=1000000')
    },
    onFileChange(event){
      this.agente.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.agente.agentes.map(row =>
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
      const status = exportFile('agente_list.csv', content, 'text/csv')
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
