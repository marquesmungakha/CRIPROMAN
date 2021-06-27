import JurisdicaoList from 'pages/jurisdicao/index'
import JurisdicaoDetail from 'pages/jurisdicao/details'

const JurisdicaoListRoute =
  {
    path: '/jurisdicao',
    name: 'jurisdicao',
    component: JurisdicaoList
  }

const JurisdicaoShowRoute = {
  path: '/jurisdicao/:id',
  component: JurisdicaoDetail,
  props: true
}

export default [
  JurisdicaoListRoute, JurisdicaoShowRoute
]
