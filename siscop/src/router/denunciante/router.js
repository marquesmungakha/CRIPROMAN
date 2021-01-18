// Denunciante Pages
import DenuncianteList from 'pages/denunciante/index'
import DenuncianteDetail from 'pages/denunciante/details'

const DenuncianteListRoute =
  {
    path: '/denunciante',
    name: 'denunciante',
    component: DenuncianteList
  }

const DenuncianteShowRoute = {
  path: '/:id',
  name: 'denuncianteDetails',
  component: DenuncianteDetail,
  props: true
}

export default [
  DenuncianteListRoute, DenuncianteShowRoute
]
