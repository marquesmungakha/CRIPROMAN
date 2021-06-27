import ProfissaoList from 'pages/profissao/index'
import ProfissaoDetail from 'pages/profissao/details'

const ProfissaoListRoute =
  {
    path: '/profissao',
    name: 'profissao',
    component: ProfissaoList
  }

const ProfissaoShowRoute = {
  path: '/profissao/:id',
  component: ProfissaoDetail,
  props: true
}

export default [
  ProfissaoListRoute, ProfissaoShowRoute
]
