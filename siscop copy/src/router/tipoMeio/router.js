import TipoMeioList from 'pages/tipoMeio/index'
import TipoMeioDetail from 'pages/tipoMeio/details'

const TipoMeioListRoute =
  {
    path: '/tipoMeio',
    name: 'tipoMeio',
    component: TipoMeioList
  }

const TipoMeioShowRoute = {
  path: '/tipoMeio/:id',
  component: TipoMeioDetail,
  props: true
}

export default [
  TipoMeioListRoute, TipoMeioShowRoute
]
