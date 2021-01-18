import FuncaoList from 'pages/funcao/index'
import FuncaoDetail from 'pages/funcao/details'

const FuncaoListRoute =
  {
    path: '/funcao',
    name: 'funcao',
    component: FuncaoList
  }

const FuncaoShowRoute = {
  path: '/funcao/:id',
  component: FuncaoDetail,
  props: true
}

export default [
  FuncaoListRoute, FuncaoShowRoute
]
