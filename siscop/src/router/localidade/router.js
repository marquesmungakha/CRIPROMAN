// Provincia Pages
import LocalidadeList from 'pages/localidade/index'
import LocalidadeDetail from 'pages/localidade/details'

const LocalidadeListRoute =
  {
    path: '/localidade',
    name: 'localidade',
    component: LocalidadeList
  }

const LocalidadeShowRoute = {
  path: '/:id',
  name: 'localidadeDetails',
  component: LocalidadeDetail,
  props: true
}

export default [
  LocalidadeListRoute, LocalidadeShowRoute
]
