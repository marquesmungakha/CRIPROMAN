<template>
  <q-page class="q-pa-sm q-gutter-sm">
  <q-table title="Agente" :data="allAgentes" :columns="columns" row-key="name" binary-state-sort :filter="filter">

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
              <router-link :to="`/agente/${props.row.id}`" >
              <q-btn round glossy icon="visibility" color="secondary" size=sm no-caps />
               </router-link>
              <q-btn round glossy icon="edit" color="blue" @click="editaAgente(props.row)" size=sm no-caps />
              <q-btn round glossy icon="delete_forever" color="red" @click="removeAgente(props.row)" size=sm no-caps/>
             </div>
            </q-td>
          </q-tr>
        </template>
  </q-table>
   <div class="q-pa-sm q-gutter-sm">
       <q-dialog v-model="show_dialog" persistent>
        <q-card style="width: 1100px; max-width: 90vw;">
        <q-card-section>
            <div class="text-h6">Adicionar  Agente!</div>
        </q-card-section>
            <q-card-section>
            <li v-for="item in listErrors" :key="item">
            {{ item }}
            </li>
            </q-card-section>
            <q-separator />
            <q-card-section style="max-height: 70vh" class="scroll">
                    <q-form @submit.prevent="createAgente" class="q-gutter-md">
                     <quadro  :nome.sync="agente.nome"
                              :sexo.sync="agente.sexo"
                              :telemovel2.sync="agente.telemovel2"
                              :apelido.sync="agente.apelido"
                              :telemovel1.sync="agente.telemovel1"
                              :fotografia.sync="agente.fotografia"
                              :idade.sync="agente.idade"
                              :numero.sync="agente.numero"/>
                        <create-edit-form :show_dialog="show_dialog"
                                          :descricao.sync="agente.descricao"
                                          :cargoOcupado.sync="agente.cargoOcupado"/>
                      </q-form>
            </q-card-section>
            <q-separator />
        <q-card-actions align="right">
            <q-btn type="submit" :loading="submitting" @click.stop="createAgente" color="teal" label="Gravar" />
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
  name: 'Agente',
  data () {
    return {
      listErrors: [],
      agente_details_dialog: false,
      editedIndex: -1,
      submitting: false,
      filter: '',
      show_dialog: false,
      show_filter: false,
      agente: {
        nome: '',
        sexo: '',
        idade: '',
        telemovel2: '',
        apelido: '',
        telemovel1: '',
        fotografia: '',
        class: 'org.devinthesky.quadro.Agente',
        descricao: '',
        numero: '',
        cargoOcupado: ''
      },
      columns: [
        { name: 'numero', required: true, label: 'Número do Agente', align: 'left', field: row => row.sigla, format: val => `${val}`, sortable: true },
        { name: 'nome', align: 'left', label: 'Nome', field: row => row.designacao, format: val => `${val}`, sortable: true },
        { name: 'apelido', align: 'left', label: 'Apelido', field: row => row.tipo_agente.id, format: val => `${val}`, sortable: true },
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
    return store.dispatch('agente/getAllAgente')
  },
  mounted () {
    this.agente.class = 'org.devinthesky.individuo.Agente'
  },
  components: {
    'create-edit-form': require('components/agente/createEditForm.vue').default,
    quadro: require('components/quadro/createEditFields.vue').default
  },
  metaInfo: {
  },
  computed: {
    allAgentes () {
      return this.$store.getters['agente/allAgente']
    }
  },
  methods: {
    ...mapActions('agente', ['getAllAgente', 'addNewAgente', 'updateAgente', 'deleteAgente']),
    createAgente () {
      this.listErrors = []
      this.submitting = true
      setTimeout(() => {
        this.submitting = false
      }, 300)
      this.agente.class = 'org.devinthesky.quadro.Agente'
      if (this.editedIndex > -1) {
        this.updateAgente(this.agente).then(resp => {
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
        this.addNewAgente(this.agente).then(resp => {
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
    close () {
      this.$store.dispatch('agente/getAllAgente')
      this.show_dialog = false
      this.agente = {}
      this.props = this.agente
      setTimeout(() => {
        this.editedIndex = -1
      }, 300)
    },
    removeAgente (agente) {
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
        this.deleteAgente(agente)
      })
    },
    editaAgente (agente) {
      this.editedIndex = this.allAgentes.indexOf(agente)
      this.agente = Object.assign({}, agente)
      this.show_dialog = true
    },
    exportTable () {
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
