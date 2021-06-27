// Provincia Pages
import DistritoList from 'pages/distrito/index'
import DistritoDetail from 'pages/distrito/details'

const DistritoListRoute =
  {
    path: '/distrito',
    name: 'distrito',
    component: DistritoList
  }

const DistritoShowRoute = {
  path: '/:id',
  name: 'distritoDetails',
  component: DistritoDetail,
  props: true
}

export default [
  DistritoListRoute, DistritoShowRoute
]
