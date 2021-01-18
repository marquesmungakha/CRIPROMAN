// Provincia Pages
import BairroList from 'pages/bairro/index'
import BairroDetail from 'pages/bairro/details'

const BairroListRoute =
  {
    path: '/bairro',
    name: 'bairro',
    component: BairroList
  }

const BairroShowRoute = {
  path: '/:id',
  name: 'bairroDetails',
  component: BairroDetail,
  props: true
}

export default [
  BairroListRoute, BairroShowRoute
]
