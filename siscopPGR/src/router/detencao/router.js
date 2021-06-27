// Provincia Pages
import DetencaoList from 'pages/detencao/index'
import DetencaoDetail from 'pages/detencao/details'

const DetencaoListRoute =
  {
    path: '/detencao',
    name: 'detencao',
    component: DetencaoList
  }

const DetencaoShowRoute = {
  path: '/:id',
  name: 'detencaoDetails',
  component: DetencaoDetail,
  props: true
}

export default [
  DetencaoListRoute, DetencaoShowRoute
]
