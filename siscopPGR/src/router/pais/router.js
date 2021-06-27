import PaisList from 'pages/pais/index'
import PaisDetail from 'pages/pais/details'

const PaisListRoute =
  {
    path: '/pais',
    name: 'pais',
    component: PaisList
  }

const PaisShowRoute = {
  path: '/pais/:id',
  component: PaisDetail,
  props: true
}

export default [
  PaisListRoute, PaisShowRoute
]
