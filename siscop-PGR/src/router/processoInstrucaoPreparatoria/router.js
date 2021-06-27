// Provincia Pages
import ProcessoInstrucaoPreparatoriaList from 'pages/processoInstrucaoPreparatoria/index'
import ProcessoInstrucaoPreparatoriaDetail from 'pages/processoInstrucaoPreparatoria/details'

const ProcessoInstrucaoPreparatoriaListRoute =
  {
    path: '/processoInstrucaoPreparatoria',
    name: 'processoInstrucaoPreparatoria',
    component: ProcessoInstrucaoPreparatoriaList
  }

const ProcessoInstrucaoPreparatoriaShowRoute = {
  path: '/:id',
  name: 'processoInstrucaoPreparatoriaDetails',
  component: ProcessoInstrucaoPreparatoriaDetail,
  props: true
}

export default [
  ProcessoInstrucaoPreparatoriaListRoute, ProcessoInstrucaoPreparatoriaShowRoute
]
