import ModoAtuacaoList from 'pages/modoAtuacao/index'
import ModoAtuacaoDetail from 'pages/modoAtuacao/details'

const ModoAtuacaoListRoute =
  {
    path: '/modoAtuacao',
    name: 'modoAtuacao',
    component: ModoAtuacaoList
  }

const ModoAtuacaoShowRoute = {
  path: '/modoAtuacao/:id',
  component: ModoAtuacaoDetail,
  props: true
}

export default [
  ModoAtuacaoListRoute, ModoAtuacaoShowRoute
]
