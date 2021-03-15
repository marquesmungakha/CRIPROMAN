<template>
  <q-page class="q-pa-sm q-gutter-sm">
    <q-table :columns="columns" :data="allMagistrados" :filter="filter" binary-state-sort row-key="name"
             title="Magistrado">

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
              <router-link :to="`/magistrado/${props.row.id}`">
                <q-btn color="secondary" glossy icon="visibility" no-caps round size=sm />
              </router-link>
              <q-btn color="blue" glossy icon="edit" no-caps round size=sm @click="editaMagistrado(props.row)"/>
              <q-btn color="red" glossy icon="delete_forever" no-caps round size=sm
                     @click="removeMagistrado(props.row)"/>
            </div>
          </q-td>
        </q-tr>
      </template>
    </q-table>
    <div class="q-pa-sm q-gutter-sm">
      <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
          <q-card-section>
            <div class="text-h6">Adicionar/Actualizar Magistrado!</div>
          </q-card-section>
          <q-card-section>
            <li v-for="item in listErrors" :key="item">
              {{ item }}
            </li>
          </q-card-section>
          <q-separator/>
          <q-card-section class="scroll" style="max-height: 70vh">
            <q-form class="q-gutter-md" @submit.prevent="createMagistrado">
              <quadro :apelido.sync="magistrado.apelido"
                      :fotografia.sync="magistrado.fotografia"
                      :idade.sync="magistrado.idade"
                      :nome.sync="magistrado.nome"
                      :numero.sync="magistrado.numero"
                      :sexo.sync="magistrado.sexo"
                      :telemovel1.sync="magistrado.telemovel1"
                      :telemovel2.sync="magistrado.telemovel2"
                      :onFileChange.sync="onFileChange"
                      :image.sync="image"/>
              <create-edit-form :cargoOcupado.sync="magistrado.cargoOcupado"
                                :descricao.sync="magistrado.descricao"
                                :show_dialog="show_dialog"/>
            </q-form>
          </q-card-section>
          <q-separator/>
          <q-card-actions align="right">
            <q-btn :loading="submitting" color="teal" label="Gravar" type="submit" @click.stop="createMagistrado"/>
            <q-btn v-close-popup color="negative" label="Cancelar" type="reset" @click="close"/>
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </q-page>
</template>

<script>
import {exportFile, QSpinnerBall} from 'quasar'
import Magistrado from 'src/store/models/magistrado/magistrado'

function wrapCsvValue(val, formatFn) {
  let formatted = formatFn !== undefined ? formatFn(val) : val
  formatted = formatted === undefined || formatted === null ? '' : String(formatted)
  formatted = formatted.split('"').join('""')
  return `"${formatted}"`
}

export default {
  name: 'Magistrado',
  data() {
    return {
      listErrors: [],
      magistrado_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      image: '',
      magistrado: {
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
          label: 'Número do Magistrado',
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
          field: row => row.tipo_magistrado.id,
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
    return this.getAllMagistrado()
  },
  mounted() {
    let offset = 0
    this.getAllMagistrado(offset)
  },
  components: {
    'create-edit-form': require('components/magistrado/createEditForm.vue').default,
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
    allMagistrados() {
      return Magistrado.query().all()
    }
  },
  methods: {
    createMagistrado() {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      let formData = new FormData();
      formData.append("nome", this.magistrado.nome);
      formData.append("apelido", this.magistrado.apelido);
      formData.append("sexo", this.magistrado.sexo);
      formData.append("idade", this.magistrado.idade);
      formData.append("telemovel2", this.magistrado.telemovel2);
      formData.append("telemovel1", this.magistrado.telemovel1);
      formData.append("fotografia", this.magistrado.fotografia);
      formData.append("descricao", this.magistrado.descricao);
      formData.append("numero", this.magistrado.numero);
      formData.append("cargoOcupado", this.magistrado.cargoOcupado);


      if (this.editedIndex > -1) {
        Magistrado.api().patch("/magistrado/" + this.magistrado.id, formData).then(resp => {
          this.$q.notify({
            type: 'positive',
            color: 'green-4',
            textColor: 'white',
            icon: 'cloud_done',
            timeout: 2000,
            position: 'bottom',
            classes: 'glossy',
            progress: true,
            message: 'A informação foi actualizada com successo!! [ ' + this.magistrado.designacao + ' ]'
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
        Magistrado.api().post("/magistrado/", formData).then(resp => {
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
            message: 'A informação foi inserida com successo! [ ' + this.magistrado.designacao + ' ]'
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
      this.getAllMagistrado()
      this.image = ''
      this.show_dialog = false
      this.magistrado = {}
      this.props = this.magistrado
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeMagistrado(magistrado) {
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
          message: 'A informação foi Removida com successo! [ ' + magistrado.designacao + ' ]'
        })
        Magistrado.api().delete("/magistrado/" + magistrado.id)
      })
    },
    editaMagistrado(magistrado) {
      this.editedIndex = 0
      this.magistrado = Object.assign({}, magistrado)
      this.image ='data:image/jpeg;base64,' + btoa(new Uint8Array(magistrado.fotografia).reduce((data, byte) => data + String.fromCharCode(byte), ''))
      this.show_dialog = true
    },
    getAllMagistrado(offset) {
      Magistrado.api().get("/magistrado?offset="+offset+"&max=1000").then(resp => {
          console.log(resp)
          offset = offset + 1
          if(resp.response.data.length() > 0) 
              setTimeout(this.getAllMagistrado, 2)

          }).catch(error => {
          console.log('Erro no code ' + error)
        })
    },
    onFileChange(event){
      this.magistrado.fotografia = event.target.files[0];
      this.image = URL.createObjectURL(event.target.files[0]);
    },
    exportTable() {
      // naive encoding to csv format
      const content = [this.columns.map(col => wrapCsvValue(col.label))]
        .concat(
          this.$store.state.magistrado.magistrados.map(row =>
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
      const status = exportFile('magistrado_list.csv', content, 'text/csv')
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
