import TipoParecerAutoList from 'pages/tipoParecerAuto/index'
import TipoParecerAutoDetail from 'pages/tipoParecerAuto/details'

const TipoParecerAutoListRoute =
  {
    path: '/tipoParecer',
    name: 'tipoParecer',
    component: TipoParecerAutoList
  }

const TipoParecerAutoShowRoute = {
  path: '/tipoParecer/:id',
  component: TipoParecerAutoDetail,
  props: true
}

export default [
  TipoParecerAutoListRoute, TipoParecerAutoShowRoute
]
