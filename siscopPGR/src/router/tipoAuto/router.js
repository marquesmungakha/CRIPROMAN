import TipoAutoList from 'pages/tipoAuto/index'
import TipoAutoDetail from 'pages/tipoAuto/details'

const TipoAutoListRoute =
  {
    path: '/tipoAuto',
    name: 'tipoAuto',
    component: TipoAutoList
  }

const TipoAutoShowRoute = {
  path: '/tipoAuto/:id',
  component: TipoAutoDetail,
  props: true
}

export default [
  TipoAutoListRoute, TipoAutoShowRoute
]
