<template>
  <q-layout view="lHh Lpr lFf">
    <q-card class="q-mt-md qmr-sr">
      <q-header elevated class="bg-grey-1 text-grey-8 text-center" style="height: 150px">

        <q-toolbar>
          <q-btn flat dense round icon="menu" aria-label="Menu" @click="leftDrawerOpen = !leftDrawerOpen" />
          <q-toolbar-title>
            <q-space/>
            <div class="text-h6 text-weight- q-mt-md qmr-sr"> SERVIÇO NACIONAL DE INVESTIGAÇÃO CRIMINAL</div>
            <q-space/>
            <div class="text-weight-thin"> SISTEMA DE GESTÃO PROCESSUAL </div>
            <q-space/>
            <!--div class="text-weight-thin"> DEPARTAMENTO DE PLANIFICACAO E AVALIACAO ESTATISTICA </div-->
          </q-toolbar-title>
          <div class="q-gutter-sm row items-center no-wrap">
            <q-btn round dense flat color="grey-8" icon="notifications">
              <q-badge color="red" text-color="white" floating>
                0
              </q-badge>
              <q-menu>
                <q-list style="min-width: 100px">
                  <messages></messages>
                  <q-card class="text-center no-shadow no-border">
                    <q-btn label="Ver tudo" style="max-width: 120px !important;" flat dense
                           class="text-indigo-8"></q-btn>
                  </q-card>
                </q-list>
              </q-menu>
              <!--            <q-tooltip>Notifications</q-tooltip>-->
            </q-btn>

            <q-btn round flat></q-btn>
            <q-btn-dropdown unelevated v-model="userInfoOpen" no-caps @click="onMainClick">
              <template v-slot:label>
                <div class="row items-center no-wrap">
                  <q-avatar size="lg">
                    <img src="~assets/SernicLogo.png" />
                      <!-- <img src="~assets/userLogedIn.jpg" /> -->
                  </q-avatar>
                  <div class="text-center q-pa-sm">
                    Admistrador
                  </div>
                </div>
              </template>
              <user-info>
                <q-list  style="width: 260px">
                  <q-item>
                    <q-item-section avatar>
                      <q-avatar>
                        <img src="~assets/userLogedIn.jpg" />
                      </q-avatar>
                    </q-item-section>
                    <q-item-section>
                      <q-item-label lines="1">
                        <div class="text-h6">Admin Admin</div>
                      </q-item-label>
                    </q-item-section>
                  </q-item>
                  <q-separator spaced />
                  <q-item clickable v-close-popup @click="onItemClick">
                    <q-item-section avatar>
                      <q-avatar icon="mdi-account"  />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label lines="1">Perfil</q-item-label>
                      <q-item-label caption>Vizualizar o Perfil</q-item-label>
                    </q-item-section>
                  </q-item>

                  <q-item clickable v-close-popup @click="onItemClick" to="/Logout">
                    <q-item-section avatar>
                      <q-avatar icon="mdi-logout"  />
                    </q-item-section>
                    <q-item-section>
                      <q-item-label>Sair</q-item-label>
                      <q-item-label caption>Sair do Sistema</q-item-label>
                    </q-item-section>
                  </q-item>
                </q-list>
              </user-info>
            </q-btn-dropdown>
          </div>
        </q-toolbar>
      </q-header>
    </q-card>

    <q-page-container>
     <q-page class="q-pa-md">
        <q-card class="absolute-top " style="height: 150px">
          
              <q-table
                @row-click="goTo"
                title="Lista de Orgãos"
                :data="allOrgaos"
                :columns="columns"
                row-key="designacao">

                <template v-slot:top>
                  <div class="column" >
                    <div class="col-5 flex flex-center row justify-center q-pa-md" style="align-self: center">
                      <img src="~assets/SernicLogo.png" style="height: 140px; max-width: 150px">  
                      <div class="absolute-top vertical-middle bg-transparent">
                      </div>
                    </div>
                    <div class="col-5">
                       <div class="text-weight-bold text-center">SERVIÇO NACIONAL DE INVESTIGAÇÃO CRIMINAL</div>
                      <div class="absolute-bottom bg-transparent">
                        <!-- <div class="text-weight-bold text-center">Ministério Público</div> -->
                      </div>
                    </div>
                  </div>
                  <q-space />
                </template>

                <template v-slot:header="props">
                  <q-tr :props="props">
                    <q-th auto-width />
                    <q-th
                      v-for="col in props.cols"
                      :key="col.name"
                      :props="props">
                      {{ col.label }}
                    </q-th>
                  </q-tr>
                </template>

                <template v-slot:body="props">
                  <q-tr :props="props">
                    <q-td auto-width>
                      <q-btn size="sm" color="accent" round dense @click="props.expand = !props.expand" :icon="props.expand ? 'remove' : 'add'" />
                    </q-td>
                    <q-td v-for="col in props.cols" :key="col.name" :props="props">
                      <q-item to="/mainPage" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                        <q-item-section>
                          <q-item-label>  {{ col.value }} </q-item-label>
                        </q-item-section>
                      </q-item>                    
                    </q-td>
                  </q-tr>
                  <q-tr v-show="props.expand" :props="props">
                    <q-td colspan="100%">
                      <q-tr v-for="dep in props.row.dependenciasOrgao" :key="dep.id">
                        <q-td>{{ dep.designacao }}</q-td>
                      </q-tr>
                    </q-td>
                  </q-tr>
                </template>
              </q-table>
          </q-card>
     </q-page>
    </q-page-container>

 <q-footer elevated>
      <q-toolbar class="glossy bg-grey-2 text-grey-8 text-center">
        <q-toolbar-title>Copyright ©  InformService, Lda 2018</q-toolbar-title>
      </q-toolbar>
    </q-footer>

  </q-layout>
</template>

<script>
import  QSpinnerBall from 'quasar'
import EssentialLink from 'components/EssentialLink.vue'
import Orgao from 'src/store/models/orgao/orgao'
import DependenciaOrgao from 'src/store/models/dependenciaOrgao/dependenciaOrgao'

export default {
  name: 'MainLayout',
  components: {
    // eslint-disable-next-line vue/no-unused-components
    EssentialLink
  },
  data () {
    return {
      leftDrawerOpen: false,
      userInfoOpen: false,
      onMainClick: '',
      onItemClick: '',
      columns: [
                // {
                //   name: 'sigla',
                //   required: true,
                //   label: 'Sigla',
                //   align: 'left',
                //   field: row => row.sigla,
                //   format: val => `${val}`,
                //   sortable: true
                // },
                {
                  name: 'designacao',
                  align: 'left',
                  label: 'Designação',
                  field: row => row.designacao,
                  format: val => `${val}`,
                  sortable: true
                }
              ],
      data: [],
      orgaoList: {
        label: '',
        children: []
      },
      dependennciaList: {
        label: '',
        icon: ''
      }
    }
  },
  meta: {
    title: 'GEPROC-SERNIC'
  },
   mounted() {
    this.getAllOrgao()
    this.getAllDependenciaOrgao()
  },
  components: {
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
    allOrgaos() {
      return Orgao.query().with('dependenciasOrgao').where('nivel',0).all()
    }
  },
    methods: {
      goTo(event, row){
      // Here you can navigate to where ever you have to
      // this.$router.push('home')
      console.log(row);
      },
      getOrgaoTree() {
        this.orgaoList.label = allOrgaos.designacao
      },
    getAllOrgao() {
      Orgao.api().get('/orgao?offset=0&max=1000000')
    },
    getAllDependenciaOrgao() {
      DependenciaOrgao.api().get('/dependenciaOrgao?offset=0&max=1000000')
    }
  }
}
</script>
