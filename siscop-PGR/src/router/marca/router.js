// Marca Pages
import MarcaList from 'pages/marca/index'
import MarcaDetail from 'pages/marca/details'

const MarcaListRoute =
  {
    path: '/marca',
    name: 'marca',
    component: MarcaList
  }

const MarcaShowRoute = {
  path: '/:id',
  name: 'marcaDetails',
  component: MarcaDetail,
  props: true
}

export default [
  MarcaListRoute, MarcaShowRoute
]
