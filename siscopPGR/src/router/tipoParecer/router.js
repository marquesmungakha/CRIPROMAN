import TipoParecerList from 'pages/tipoParecer/index'
import TipoParecerDetail from 'pages/tipoParecer/details'

const TipoParecerListRoute =
  {
    path: '/tipoParecer',
    name: 'tipoParecer',
    component: TipoParecerList
  }

const TipoParecerShowRoute = {
  path: '/tipoParecer/:id',
  component: TipoParecerDetail,
  props: true
}

export default [
  TipoParecerListRoute, TipoParecerShowRoute
]
