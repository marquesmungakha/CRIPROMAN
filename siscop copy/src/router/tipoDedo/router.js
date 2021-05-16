import TipoDedoList from 'pages/tipoDedo/index'
import TipoDedoDetail from 'pages/tipoDedo/details'

const TipoDedoListRoute =
  {
    path: '/tipoDedo',
    name: 'tipoDedo',
    component: TipoDedoList
  }

const TipoDedoShowRoute = {
  path: '/tipoDedo/:id',
  component: TipoDedoDetail,
  props: true
}

export default [
  TipoDedoListRoute, TipoDedoShowRoute
]
