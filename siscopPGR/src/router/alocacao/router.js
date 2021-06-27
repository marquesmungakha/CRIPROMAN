// Alocacao Pages
import AlocacaoList from 'pages/alocacao/index'
import AlocacaoDetail from 'pages/alocacao/details'

const AlocacaoListRoute =
  {
    path: '/alocacao',
    name: 'alocacao',
    component: AlocacaoList
  }

const AlocacaoShowRoute = {
  path: '/:id',
  name: 'alocacaoDetails',
  component: AlocacaoDetail,
  props: true
}

export default [
  AlocacaoListRoute, AlocacaoShowRoute
]
