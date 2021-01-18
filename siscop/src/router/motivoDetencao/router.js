import MotivoDetencaoList from 'pages/motivoDetencao/index'
import MotivoDetencaoDetail from 'pages/motivoDetencao/details'

const MotivoDetencaoListRoute =
  {
    path: '/motivoDetencao',
    name: 'motivoDetencao',
    component: MotivoDetencaoList
  }

const MotivoDetencaoShowRoute = {
  path: '/motivoDetencao/:id',
  component: MotivoDetencaoDetail,
  props: true
}

export default [
  MotivoDetencaoListRoute, MotivoDetencaoShowRoute
]
