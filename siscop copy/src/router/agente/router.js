// Agente Pages
import AgenteList from 'pages/agente/index'
import AgenteDetail from 'pages/agente/details'

const AgenteListRoute =
  {
    path: '/agente',
    name: 'agente',
    component: AgenteList
  }

const AgenteShowRoute = {
  path: '/:id',
  name: 'agenteDetails',
  component: AgenteDetail,
  props: true
}

export default [
  AgenteListRoute, AgenteShowRoute
]
