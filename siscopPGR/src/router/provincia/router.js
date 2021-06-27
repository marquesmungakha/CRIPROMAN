// Provincia Pages
import ProvinciaList from 'pages/provincia/index'
import ProvinciaDetail from 'pages/provincia/details'

const ProvinciaListRoute =
  {
    path: '/provincia',
    name: 'provincia',
    component: ProvinciaList
  }

const ProvinciaShowRoute = {
  path: '/:id',
  name: 'provinciaDetails',
  component: ProvinciaDetail,
  props: true
}

export default [
  ProvinciaListRoute, ProvinciaShowRoute
]
