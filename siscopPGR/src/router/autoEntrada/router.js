// Provincia Pages
import AutoEntradaList from 'pages/autoEntrada/index'
import AutoEntradaDetail from 'pages/autoEntrada/details'

const AutoEntradaListRoute =
  {
    path: '/autoEntrada',
    name: 'autoEntrada',
    component: AutoEntradaList
  }

const AutoEntradaShowRoute = {
  path: '/:id',
  name: 'autoEntradaDetails',
  component: AutoEntradaDetail,
  props: true
}

export default [
  AutoEntradaListRoute, AutoEntradaShowRoute
]
