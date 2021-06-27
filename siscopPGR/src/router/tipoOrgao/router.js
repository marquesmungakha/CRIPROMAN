import TipoOrgaoList from 'pages/tipoOrgao/index'
import TipoOrgaoDetail from 'pages/tipoOrgao/details'

const TipoOrgaoListRoute =
  {
    path: '/tipoOrgao',
    name: 'tipoOrgao',
    component: TipoOrgaoList
  }

const TipoOrgaoShowRoute = {
  path: '/tipoOrgao/:id',
  component: TipoOrgaoDetail,
  props: true
}

export default [
  TipoOrgaoListRoute, TipoOrgaoShowRoute
]
