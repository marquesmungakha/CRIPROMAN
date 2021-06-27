// Provincia Pages
import ApreensaoList from 'pages/apreensao/index'
import ApreensaoDetail from 'pages/apreensao/details'

const ApreensaoListRoute =
  {
    path: '/apreensao',
    name: 'apreensao',
    component: ApreensaoList
  }

const ApreensaoShowRoute = {
  path: '/:id',
  name: 'apreensaoDetails',
  component: ApreensaoDetail,
  props: true
}

export default [
  ApreensaoListRoute, ApreensaoShowRoute
]
