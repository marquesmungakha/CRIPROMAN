<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allInspectors" :filter="filter" binary-state-sort row-key="name"
             title="Inspector">

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
              <router-link :to="`/inspector/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm >
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Ver Detalhes
                </q-tooltip>
                </q-btn>
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaInspector(props.row)">
                <q-tooltip content-class="bg-white text-primary shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Editar
                </q-tooltip>
                </q-btn>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click="removeInspector(props.row)">
                <q-tooltip content-class="bg-red text-white shadow-4" 
                          :offset="[10, 10]"
                          transition-show="rotate"
                          transition-hide="rotate">
                  Remover
                </q-tooltip>
                </q-btn>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar/Actualizar Inspector!</div>
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
          <q-card-section class="scroll" style="max-height: 80vh">
            <q-form class="q-gutter-md" @submit.prevent="createInspector">
              <quadro :apelido.sync="inspector.apelido"
                      :fotografia.sync="inspector.fotografia"
                      :idade.sync="inspector.idade"
                      :nome.sync="inspector.nome"
                      :numero.sync="inspector.numero"
                      :sexo.sync="inspector.sexo"
                      :telemovel1.sync="inspector.telemovel1"
                      :telemovel2.sync="inspector.telemovel2"
                      :onFileChange.sync="onFileChange"
                      :image.sync="image"/>
              <create-edit-form :cargoOcupado.sync="inspector.cargoOcupado"
                                :descricao.sync="inspector.descricao"
                                :show_dialog="show_dialog"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createInspector"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Inspector from 'src/store/models/inspector/inspector'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Inspector',
  data() {
    return {
      listErrors: [],
      inspector_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      image: '',
      inspector: {
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
          label: 'Número do Inspector',
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
          field: row => row.tipo_inspector.id,
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
    return this.getAllInspector()
  },
  mounted() {
    let offset = 0
    this.getAllInspector(offset)
  },
  components: {
    'create-edit-form': require('components/inspector/createEditForm.vue').default,
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
    allInspectors() {
      return Inspector.query().all()
    }
  },
  methods: {
    createInspector() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      let formData = new FormData()
      formData.append('fotografia', this.inspector.fotografia)
      formData.append('nome', this.inspector.nome)
      formData.append('sexo', this.inspector.sexo)
      formData.append('idade', this.inspector.idade)
      formData.append('telemovel2', this.inspector.telemovel2)
      formData.append('apelido', this.inspector.apelido)
      formData.append('telemovel1', this.inspector.telemovel1)
      formData.append('descricao', this.inspector.descricao)
      formData.append('numero', this.inspector.numero)
      formData.append('cargoOcupado', this.inspector.cargoOcupado)
     
      if (this.editedIndex > -1) {
        Inspector.api().patch("/inspector/" + this.inspector.id, formData).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.inspector.designacao + ' ]'
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
        Inspector.api().post("/inspector/", formData).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.inspector.designacao + ' ]'
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
    let offset = 0
    this.getAllInspector(offset)
      this.image = ''
      this.show_dialog = false
      this.inspector = {}
      this.props = this.inspector
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeInspector(inspector) {
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
          message: 'A informação foi Removida com successo! [ ' + inspector.designacao + ' ]'
        })
        Inspector.api().delete("/inspector/" + inspector.id)
      })
    },
    editaInspector(inspector) {
      this.editedIndex = 0
      this.inspector = Object.assign({}, inspector)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(inspector.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
     getAllInspector(offset) {
       if(offset >= 0){
          Inspector.api().get("/inspector?offset="+offset+"&max=100").then(resp => {
          offset = offset + 100
          if(resp.response.data.length > 0) 
              setTimeout(this.getAllInspector(offset), 2)
          }).catch(error => {
          console.log('Erro no code ' + error)
        })
       }
    },
     onFileChange(event){
      this.inspector.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.inspector.inspectors.map(row =>
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
      const status = exportFile('inspector_list.csv', content, 'text/csv')
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
