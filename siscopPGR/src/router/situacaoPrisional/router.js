import SituacaoPrisionalList from 'pages/situacaoPrisional/index'
import SituacaoPrisionalDetail from 'pages/situacaoPrisional/details'

const SituacaoPrisionalListRoute =
  {
    path: '/situacaoPrisional',
    name: 'situacaoPrisional',
    component: SituacaoPrisionalList
  }

const SituacaoPrisionalShowRoute = {
  path: '/situacaoPrisional/:id',
  component: SituacaoPrisionalDetail,
  props: true
}

export default [
  SituacaoPrisionalListRoute, SituacaoPrisionalShowRoute
]
