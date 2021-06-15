// Orgao Pages
import OrgaoList from 'pages/orgao/index'
import OrgaoDetail from 'pages/orgao/details'

const OrgaoListRoute =
  {
    path: '/orgao',
    name: 'orgao',
    component: OrgaoList
  }

const OrgaoShowRoute = {
  path: '/:id',
  name: 'orgaoDetails',
  component: OrgaoDetail,
  props: true
}

export default [
  OrgaoListRoute, OrgaoShowRoute
]
