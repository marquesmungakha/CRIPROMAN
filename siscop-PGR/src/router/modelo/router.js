// Modelo Pages
import ModeloList from 'pages/modelo/index'
import ModeloDetail from 'pages/modelo/details'

const ModeloListRoute =
  {
    path: '/modelo',
    name: 'modelo',
    component: ModeloList
  }

const ModeloShowRoute = {
  path: '/:id',
  name: 'modeloDetails',
  component: ModeloDetail,
  props: true
}

export default [
  ModeloListRoute, ModeloShowRoute
]
