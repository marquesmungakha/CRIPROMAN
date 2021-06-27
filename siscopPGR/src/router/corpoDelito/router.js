// Provincia Pages
import CorpoDelitoList from 'pages/corpoDelito/index'
import CorpoDelitoDetail from 'pages/corpoDelito/details'

const CorpoDelitoListRoute =
  {
    path: '/corpoDelito',
    name: 'corpoDelito',
    component: CorpoDelitoList
  }

const CorpoDelitoShowRoute = {
  path: '/:id',
  name: 'corpoDelitoDetails',
  component: CorpoDelitoDetail,
  props: true
}

export default [
  CorpoDelitoListRoute, CorpoDelitoShowRoute
]
