
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') },
      { path: '/dashboard', component: () => import('pages/dashboard/dashboard') },
      { path: '/pais', component: () => import('pages/pais/index') },
      { path: '/pais/:id', component: () => import('pages/pais/details') },
      { path: '/provincia', component: () => import('pages/provincia/index') },
      { path: '/provincia/:id', component: () => import('pages/provincia/details') },
      { path: '/distrito', component: () => import('pages/distrito/index') },
      { path: '/distrito/:id', component: () => import('pages/distrito/details') },
      { path: '/cidade', component: () => import('pages/cidade/index') },
      { path: '/cidade/:id', component: () => import('pages/cidade/details') },
      { path: '/postoAdministrativo', component: () => import('pages/postoAdministrativo/index') },
      { path: '/postoAdministrativo/:id', component: () => import('pages/postoAdministrativo/details') },
      { path: '/localidade', component: () => import('pages/localidade/index') },
      { path: '/localidade/:id', component: () => import('pages/localidade/details') },
      { path: '/bairro', component: () => import('pages/bairro/index') },
      { path: '/bairro/:id', component: () => import('pages/bairro/details') },
      { path: '/tipoAuto', component: () => import('pages/tipoAuto/index') },
      { path: '/tipoAuto/:id', component: () => import('pages/tipoAuto/details') },
      { path: '/modoAtuacao', component: () => import('pages/modoAtuacao/index') },
      { path: '/modoAtuacao/:id', component: () => import('pages/modoAtuacao/details') },
      { path: '/jurisdicao', component: () => import('pages/jurisdicao/index') },
      { path: '/jurisdicao/:id', component: () => import('pages/jurisdicao/details') },
      { path: '/crime', component: () => import('pages/crime/index') },
      { path: '/crime/:id', component: () => import('pages/crime/details') },
      { path: '/situacaoPrisional', component: () => import('pages/situacaoPrisional/index') },
      { path: '/situacaoPrisional/:id', component: () => import('pages/situacaoPrisional/details') },
      { path: '/formaProcesso', component: () => import('pages/formaProcesso/index') },
      { path: '/formaProcesso/:id', component: () => import('pages/formaProcesso/details') },
      { path: '/tipoParecer', component: () => import('pages/tipoParecer/index') },
      { path: '/tipoParecer/:id', component: () => import('pages/tipoParecer/details') },
      { path: '/tipoDocumentoIdentificacao', component: () => import('pages/tipoDocumentoIdentificacao/index') },
      { path: '/tipoDocumentoIdentificacao/:id', component: () => import('pages/tipoDocumentoIdentificacao/details') },
      { path: '/tipoDedo', component: () => import('pages/tipoDedo/index') },
      { path: '/tipoDedo/:id', component: () => import('pages/tipoDedo/details') },
      { path: '/profissao', component: () => import('pages/profissao/index') },
      { path: '/profissao/:id', component: () => import('pages/profissao/details') },
      { path: '/cargo', component: () => import('pages/cargo/index') },
      { path: '/cargo/:id', component: () => import('pages/cargo/details') },
      { path: '/tipoOrgao', component: () => import('pages/tipoOrgao/index') },
      { path: '/tipoOrgao/:id', component: () => import('pages/tipoOrgao/details') },
      { path: '/tipoMeio', component: () => import('pages/tipoMeio/index') },
      { path: '/tipoMeio/:id', component: () => import('pages/tipoMeio/details') },
      { path: '/marca', component: () => import('pages/marca/index') },
      { path: '/marca/:id', component: () => import('pages/marca/details') },
      { path: '/modelo', component: () => import('pages/modelo/index') },
      { path: '/modelo/:id', component: () => import('pages/modelo/details') },
      { path: '/funcao', component: () => import('pages/funcao/index') },
      { path: '/funcao/:id', component: () => import('pages/funcao/details') },
      { path: '/orgao', component: () => import('pages/orgao/index') },
      { path: '/orgao/:id', component: () => import('pages/orgao/details') },
      { path: '/inspector', component: () => import('pages/inspector/index') },
      { path: '/inspector/:id', component: () => import('pages/inspector/details') },
      { path: '/agente', component: () => import('pages/agente/index') },
      { path: '/agente/:id', component: () => import('pages/agente/details') },
      { path: '/magistrado', component: () => import('pages/magistrado/index') },
      { path: '/magistrado/:id', component: () => import('pages/magistrado/details') },
      { path: '/autoEntrada', component: () => import('pages/autoEntrada/index') },
      { path: '/autoEntrada/:id', component: () => import('pages/autoEntrada/details') },
      { path: '/processoInvestigacao', component: () => import('pages/processoInvestigacao/index') },
      { path: '/processoInvestigacao/:id', component: () => import('pages/processoInvestigacao/details') },
      { path: '/processoInstrucaoPreparatoria', component: () => import('pages/processoInstrucaoPreparatoria/index') },
      { path: '/processoInstrucaoPreparatoria/:id', component: () => import('pages/processoInstrucaoPreparatoria/details') },
      { path: '/motivoDetencao', component: () => import('pages/motivoDetencao/index') },
      { path: '/motivoDetencao/:id', component: () => import('pages/motivoDetencao/details') },
      { path: '/acarreacao/:id', component: () => import('pages/acarreacao/details') },
      { path: '/apreensao/:id', component: () => import('pages/apreensao/details') },
      { path: '/corpoDelito/:id', component: () => import('pages/corpoDelito/details') },
      { path: '/declaracao/:id', component: () => import('pages/declaracao/details') },
      { path: '/denuncia/:id', component: () => import('pages/denuncia/details') },
      { path: '/detencao/:id', component: () => import('pages/detencao/details') },
      { path: '/pergunta/:id', component: () => import('pages/pergunta/details') }
    ]
  },
  {
    path: '/Login',
    component: () => import('pages/login/Login.vue'),
    meta: { requiresAuth:false}
  },
  {
    path: '/Logout',
    component: () => import('pages/logout/Logout'),
    meta: { requiresAuth:false}
  },
  // Always leave this as last one,
  // but you can also remove it
  {
    path: '*',
    component: () => import('pages/Error404.vue')
  }
]

//
export default routes
