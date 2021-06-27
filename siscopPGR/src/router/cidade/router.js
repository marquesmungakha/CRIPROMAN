// Provincia Pages
import CidadeList from 'pages/cidade/index'
import CidadeDetail from 'pages/cidade/details'

const CidadeListRoute =
  {
    path: '/cidade',
    name: 'cidade',
    component: CidadeList
  }

const CidadeShowRoute = {
  path: '/:id',
  name: 'cidadeDetails',
  component: CidadeDetail,
  props: true
}

export default [
  CidadeListRoute, CidadeShowRoute
]
