<template>
  <q-layout view="lHh Lpr lFf">
    <q-card class="q-mt-md qmr-sr">
      <q-header elevated class="bg-grey-1 text-grey-8 text-center" style="height: 150px">

        <q-toolbar>
          <q-btn flat dense round icon="menu" aria-label="Menu" @click="leftDrawerOpen = !leftDrawerOpen" />
          <q-toolbar-title>
            <q-space/>
            <div class="text-h6 text-weight- q-mt-md qmr-sr">SISTEMA DE GESTÃO PROCESSUAL </div>
            <q-space/>
            <div class="text-weight-thin"> Procuradoria Geral da República - PGR </div>
            <q-space/>
            <div class="text-weight-thin"> Serviço Nacional de Informação e Estatística </div>
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
                    <img src="~assets/userLogedIn.jpg" />
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

    <q-drawer v-model="leftDrawerOpen" show-if-above bordered content-class="bg-brown-1 text-gray-8" >
      <q-card class="absolute-top bg-brown-1 " style="height: 150px">
        <q-card-section class="col-5 flex flex-center">
          <q-avatar size="95px">
            <img src="~assets/simb_moz.jpg">
          </q-avatar>
        </q-card-section>
        <div class="absolute-top vertical-middle bg-transparent">
        </div>
        <div class="absolute-bottom bg-transparent">
          <div class="text-weight-bold text-center">República de Moçambique</div>
          <div class="text-weight-bold text-center">Ministério Público</div>
        </div>
      </q-card>
      <q-scroll-area style="height: calc(100% - 150px); margin-top: 150px; border-right: 1px solid #e3e3e3">
        <q-list padding>
                <q-item>
        <q-item-section>
          <q-item-label class="text-center text-bold">MENU</q-item-label>
        </q-item-section>
      </q-item>
        <q-item to="/" active-class="q-item-no-link-highlighting">
        <q-item-section avatar>
          <q-icon name="home" class="round"/>
        </q-item-section>
        <q-item-section>
          <q-item-label>Pagina Inicial</q-item-label>
        </q-item-section>
      </q-item>
          <q-expansion-item icon="miscellaneous_services" label="Parametrização" >
             <q-expansion-item icon="location_on" :header-inset-level="0.85" label="Localização" >
                <q-item to="/pais" exact style="margin-left: 105px  !important;" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>País</q-item-label>
                  </q-item-section>
                </q-item>
                <q-item  to="/provincia" exact style="margin-left: 105px  !important;" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Província</q-item-label>
                  </q-item-section>
                </q-item>
                <q-item to="/distrito" exact style="margin-left: 105px  !important;" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Distrito</q-item-label>
                  </q-item-section>
                </q-item>
                <q-item to="/cidade" exact style="margin-left: 105px  !important;" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Cidade</q-item-label>
                  </q-item-section>
                </q-item>
                <q-item to="/postoAdministrativo" exact style="margin-left: 105px  !important;" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Posto Administrativo</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-item to="/Localidade" exact style="margin-left: 105px  !important;" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Localidade</q-item-label>
                  </q-item-section>
                </q-item>
                 <q-item to="/Bairro" exact style="margin-left: 105px  !important;" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Bairro</q-item-label>
                  </q-item-section>
                </q-item>
                  </q-expansion-item>
              <q-expansion-item icon="assignment" :header-inset-level="0.85" label="Auto" >
              <q-item to="/tipoAuto" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Tipo Auto </q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/motivoDetencao" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Motivo Detenção</q-item-label>
                  </q-item-section>
            </q-item>
             <q-item to="/jurisdicao" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Famílias Delitivas </q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/crime" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Tipo Legal de Crime</q-item-label>
                  </q-item-section>
            </q-item>
              </q-expansion-item>
                <q-expansion-item icon="import_contacts" :header-inset-level="0.85" label="Processo" >
            <q-item to="/situacaoPrisional" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Situacao Prisional</q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/formaProcesso" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Forma Processo</q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/jurisdicao" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Jurisdição/Famílias Delitivas </q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/crime" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Acções/Tipo Legal de Crime</q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/tipoParecer" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label> Estágio do Processo </q-item-label>
                  </q-item-section>
            </q-item>
                </q-expansion-item>
                  <q-expansion-item icon="account_box" :header-inset-level="0.85" label="Dados Pessoais" >
            <q-item to="/tipoDocumentoIdentificacao" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Doc. de Identificação </q-item-label>
                  </q-item-section>
            </q-item>
             <q-item to="/tipoDedo" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label> Dedo </q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/funcao" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Função</q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/profissao" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Profissão</q-item-label>
                  </q-item-section>
            </q-item>
               <q-item to="/cargo" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Cargo</q-item-label>
                  </q-item-section>
            </q-item>
          </q-expansion-item>
            <q-expansion-item icon="apartment" :header-inset-level="0.85" label="Orgao" >
             <q-item to="/tipoOrgao" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Tipo Orgão </q-item-label>
                  </q-item-section>
            </q-item>
                   </q-expansion-item>
            <q-expansion-item icon="plagiarism" :header-inset-level="0.85" label="Meios Utilizados" >
            <q-item to="/tipoMeio" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Tipo de Meio de Crime</q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/marca" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Marca</q-item-label>
                  </q-item-section>
            </q-item>
            <q-item to="/modelo" exact style="margin-left: 105px  !important;" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                  <q-item-section>
                    <q-item-label>Modelo</q-item-label>
                  </q-item-section>
            </q-item>
        </q-expansion-item>
        </q-expansion-item>
        <q-expansion-item icon="menu_open" label="Cadastro" >
         <q-item to="/orgao" exact class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar><q-icon name="apartment"/></q-item-section>
              <q-item-section>
                <q-item-label>Orgão</q-item-label>
              </q-item-section>
            </q-item>
            <q-item  to="/inspector" class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar><q-icon name="how_to_reg"/></q-item-section>
              <q-item-section>
                <q-item-label>Inspector</q-item-label>
              </q-item-section>
            </q-item>
             <q-item  to="/agente" class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar><q-icon name="how_to_reg"/></q-item-section>
              <q-item-section>
                <q-item-label>Agente</q-item-label>
              </q-item-section>
            </q-item>
              <q-item to="/magistrado" class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar><q-icon name="how_to_reg"/></q-item-section>
              <q-item-section>
                <q-item-label>Magistrado</q-item-label>
              </q-item-section>
            </q-item>
              <q-item to="/autoEntrada" class="q-ml-xl" active-class="q-item-no-link-highlighting">
                <q-item-section avatar><q-icon name="policy"/></q-item-section>
                <q-item-section>
                  <q-item-label>Entrada de Auto</q-item-label>
                </q-item-section>
            </q-item>
            <q-item to="/processoInvestigacao" class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar><q-icon name="gavel"/></q-item-section>
              <q-item-section>
                <q-item-label>Processo Investigação</q-item-label>
              </q-item-section>
            </q-item>
            <q-item to="/processoInstrucaoPreparatoria" class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar><q-icon name="gavel"/></q-item-section>
              <q-item-section>
                <q-item-label>Processo</q-item-label>
              </q-item-section>
            </q-item>
             </q-expansion-item>
          <q-expansion-item icon="folder_open" label="Gestão de Relatorios" >
            <q-item to="/dashboard" class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar>
                <q-icon name="dashboard"/>
              </q-item-section>
              <q-item-section>
                <q-item-label>Dashboard</q-item-label>
              </q-item-section>
            </q-item>
            <q-item to="/modelo1" class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar>
                <q-icon name="receipt_long"/>
              </q-item-section>
              <q-item-section>
                <q-item-label>Modelo 1, 2 e 8</q-item-label>
              </q-item-section>
            </q-item>
            <q-item class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar>
                <q-icon name="receipt_long"/>
              </q-item-section>
              <q-item-section>
                <q-item-label>Modelo 2</q-item-label>
              </q-item-section>
            </q-item>
            <q-item class="q-ml-xl" active-class="q-item-no-link-highlighting">
              <q-item-section avatar>
                <q-icon name="receipt_long"/>
              </q-item-section>
              <q-item-section>
                <q-item-label>Modelo 8</q-item-label>
              </q-item-section>
            </q-item>
        </q-expansion-item>
        <q-expansion-item icon="people_alt" label="Gestao de Utilizadores" >
            <q-item class="q-ml-xl" active-class="q-item-no-link-highlighting">
            <q-item-section avatar>
                <q-icon name="person"/>
              </q-item-section>
              <q-item-section>
                <q-item-label>Utilizadores</q-item-label>
              </q-item-section>
            </q-item>
            <q-item class="q-ml-xl" active-class="q-item-no-link-highlighting">
            <q-item-section avatar>
                <q-icon name="group"/>
              </q-item-section>
              <q-item-section>
                <q-item-label>Grupo de Utilizadores</q-item-label>
              </q-item-section>
            </q-item>
            <q-item class="q-ml-xl" active-class="q-item-no-link-highlighting">
            <q-item-section avatar>
                <q-icon name="supervised_user_circle"/>
              </q-item-section>
              <q-item-section>
                <q-item-label>Permissoes</q-item-label>
              </q-item-section>
            </q-item>
        </q-expansion-item>
      </q-list>
    </q-scroll-area>
  </q-drawer>

    <q-footer elevated>
      <q-toolbar class="glossy bg-grey-2 text-grey-8 text-center">
        <q-toolbar-title>Copyright ©  InformService, Lda 2018</q-toolbar-title>
      </q-toolbar>
    </q-footer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
import EssentialLink from 'components/EssentialLink.vue'

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
      onItemClick: ''
    }
  },

  meta: {
    title: 'SISCOP-PGR'
  }
}
</script>
