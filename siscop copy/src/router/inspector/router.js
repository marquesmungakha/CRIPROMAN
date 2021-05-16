// Inspector Pages
import InspectorList from 'pages/inspector/index'
import InspectorDetail from 'pages/inspector/details'

const InspectorListRoute =
  {
    path: '/inspector',
    name: 'inspector',
    component: InspectorList
  }

const InspectorShowRoute = {
  path: '/:id',
  name: 'inspectorDetails',
  component: InspectorDetail,
  props: true
}

export default [
  InspectorListRoute, InspectorShowRoute
]
