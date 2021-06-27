// Provincia Pages
import DeclaracaoList from 'pages/declaracao/index'
import DeclaracaoDetail from 'pages/declaracao/details'

const DeclaracaoListRoute =
  {
    path: '/declaracao',
    name: 'declaracao',
    component: DeclaracaoList
  }

const DeclaracaoShowRoute = {
  path: '/:id',
  name: 'declaracaoDetails',
  component: DeclaracaoDetail,
  props: true
}

export default [
  DeclaracaoListRoute, DeclaracaoShowRoute
]
