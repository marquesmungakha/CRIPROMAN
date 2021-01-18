<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Magistrado" :data="allMagistrados" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
            <q-td key="numero" :props="props">
              {{ props.row.numero }}
              <q-popup-edit v-model="props.row.numero">
                <q-input v-model="props.row.numero" dense autofocus counter ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="nome" :props="props">
              {{ props.row.nome }}
              <q-popup-edit v-model="props.row.nome" title="Update nome">
                <q-input v-model="props.row.nome" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
                <q-td key="apelido" :props="props">
              {{ props.row.apelido }}
              <q-popup-edit v-model="props.row.apelido" title="Update apelido">
                <q-input v-model="props.row.apelido" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="sexo" :props="props">
              {{ props.row.sexo }}
              <q-popup-edit v-model="props.row.sexo" title="Update sexo">
                <q-input v-model="props.row.sexo" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
               <q-td key="idade" :props="props">
              {{ props.row.idade }}
              <q-popup-edit v-model="props.row.idade" title="Update Idade">
                <q-input v-model="props.row.idade" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
               <q-td key="cargoOcupado" :props="props">
              {{ props.row.cargoOcupado }}
              <q-popup-edit v-model="props.row.cargoOcupado" title="Update cargoOcupado">
                <q-input v-model="props.row.cargoOcupado" dense autofocus ></q-input>
              </q-popup-edit>
            </q-td>
            <q-td key="actions" :props="props">
             <div class="q-gutter-sm">
              <router-link :to="`/magistrado/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaMagistrado(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeMagistrado(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
   <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Magistrado!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
            <q-separator />
            <q-card-section style="max-height: 70vh" class="scroll">
                    <q-form @submit.prevent="createMagistrado" class="q-gutter-md">
                     <quadro  :nome.sync="magistrado.nome"
                              :sexo.sync="magistrado.sexo"
                              :telemovel2.sync="magistrado.telemovel2"
                              :apelido.sync="magistrado.apelido"
                              :telemovel1.sync="magistrado.telemovel1"
                              :fotografia.sync="magistrado.fotografia"
                              :idade.sync="magistrado.idade"
                              :numero.sync="magistrado.numero"/>
                        <create-edit-form :show_dialog="show_dialog"
                                          :descricao.sync="magistrado.descricao"
                                          :cargoOcupado.sync="magistrado.cargoOcupado"/>
                      </q-form>
            </q-card-section>
            <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createMagistrado" color="teal" label="Gravar" />
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
  name: 'Magistrado',
  data () {
    return {
      listErrors: [],
      magistrado_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      magistrado: {
        nome: '',
        sexo: '',
        idade: '',
        telemovel2: '',
        apelido: '',
        telemovel1: '',
        fotografia: '',
        class: 'org.devinthesky.quadro.Magistrado',
        descricao: '',
        numero: '',
        cargoOcupado: ''
      },
      columns: [
        { name: 'numero', required: true, label: 'Número do Magistrado', align: 'left', field: row => row.sigla, format: val => `${val}`, sortable: true },
        { name: 'nome', align: 'left', label: 'Nome', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'apelido', align: 'left', label: 'Apelido', field: row => row.tipo_magistrado.id, format: val => `${val}`, sortable: true },
        { name: 'sexo', align: 'left', label: 'Sexo', field: row => row.nivel, format: val => `${val}`, sortable: true },
        { name: 'idade', align: 'left', label: 'Idade', field: row => row.idade, format: val => `${val}`, sortable: true },
        { name: 'cargoOcupado', align: 'left', label: 'Cargo', field: row => row.cargoOcupado, format: val => `${val}`, sortable: true },
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
    return store.dispatch('magistrado/getAllMagistrado')
  },
  mounted () {
    this.magistrado.class = 'org.devinthesky.individuo.Magistrado'
  },
  components: {
    'create-edit-form': require('components/magistrado/createEditForm.vue').default,
    quadro: require('components/quadro/createEditFields.vue').default
  },
  metaInfo: {
  },
  computed: {
    allMagistrados () {
      return this.$store.getters['magistrado/allMagistrado']
    }
  },
  methods: {
    ...mapActions('magistrado', ['getAllMagistrado', 'addNewMagistrado', 'updateMagistrado', 'deleteMagistrado']),
    createMagistrado () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.magistrado.class = 'org.devinthesky.quadro.Magistrado'
      if (this.editedIndex > -1) {
        this.updateMagistrado(this.magistrado).then(resp => {
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
        this.addNewMagistrado(this.magistrado).then(resp => {
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
    close () {
      this.$store.dispatch('magistrado/getAllMagistrado')
      this.show_dialog = false
      this.magistrado = {}
      this.props = this.magistrado
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeMagistrado (magistrado) {
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
        this.deleteMagistrado(magistrado)
      })
    },
    editaMagistrado (magistrado) {
      this.editedIndex = this.allMagistrados.indexOf(magistrado)
      this.magistrado = Object.assign({}, magistrado)
      this.show_dialog = true
    },
    exportTable () {
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
