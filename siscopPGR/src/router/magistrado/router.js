// Magistrado Pages
import MagistradoList from 'pages/magistrado/index'
import MagistradoDetail from 'pages/magistrado/details'

const MagistradoListRoute =
  {
    path: '/magistrado',
    name: 'magistrado',
    component: MagistradoList
  }

const MagistradoShowRoute = {
  path: '/:id',
  name: 'magistradoDetails',
  component: MagistradoDetail,
  props: true
}

export default [
  MagistradoListRoute, MagistradoShowRoute
]
