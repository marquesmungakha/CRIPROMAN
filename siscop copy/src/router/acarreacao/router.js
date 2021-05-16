// Provincia Pages
import AcarreacaoList from 'pages/acarreacao/index'
import AcarreacaoDetail from 'pages/acarreacao/details'

const AcarreacaoListRoute =
  {
    path: '/acarreacao',
    name: 'acarreacao',
    component: AcarreacaoList
  }

const AcarreacaoShowRoute = {
  path: '/:id',
  name: 'acarreacaoDetails',
  component: AcarreacaoDetail,
  props: true
}

export default [
  AcarreacaoListRoute, AcarreacaoShowRoute
]
