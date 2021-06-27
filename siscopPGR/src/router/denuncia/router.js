// Provincia Pages
import DenunciaList from 'pages/denuncia/index'
import DenunciaDetail from 'pages/denuncia/details'

const DenunciaListRoute =
  {
    path: '/denuncia',
    name: 'denuncia',
    component: DenunciaList
  }

const DenunciaShowRoute = {
  path: '/:id',
  name: 'denunciaDetails',
  component: DenunciaDetail,
  props: true
}

export default [
  DenunciaListRoute, DenunciaShowRoute
]
