// Provincia Pages
import PostoAdministrativoList from 'pages/postoAdministrativo/index'
import PostoAdministrativoDetail from 'pages/postoAdministrativo/details'

const PostoAdministrativoListRoute =
  {
    path: '/postoAdministrativo',
    name: 'postoAdministrativo',
    component: PostoAdministrativoList
  }

const PostoAdministrativoShowRoute = {
  path: '/:id',
  name: 'postoAdministrativoDetails',
  component: PostoAdministrativoDetail,
  props: true
}

export default [
  PostoAdministrativoListRoute, PostoAdministrativoShowRoute
]
