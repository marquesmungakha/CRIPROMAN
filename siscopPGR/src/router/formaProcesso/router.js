import FormaProcessoList from 'pages/formaProcesso/index'
import FormaProcessoDetail from 'pages/formaProcesso/details'

const FormaProcessoListRoute =
  {
    path: '/formaProcesso',
    name: 'formaProcesso',
    component: FormaProcessoList
  }

const FormaProcessoShowRoute = {
  path: '/formaProcesso/:id',
  component: FormaProcessoDetail,
  props: true
}

export default [
  FormaProcessoListRoute, FormaProcessoShowRoute
]
