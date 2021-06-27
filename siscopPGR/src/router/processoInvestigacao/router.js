// Provincia Pages
import ProcessoList from 'pages/processoInvestigacao/index'
import ProcessoDetail from 'pages/processoInvestigacao/details'

const ProcessoListRoute =
  {
    path: '/processoInvestigacao',
    name: 'processoInvestigacao',
    component: ProcessoList
  }

const ProcessoShowRoute = {
  path: '/:id',
  name: 'processoInvestigacaoDetails',
  component: ProcessoDetail,
  props: true
}

export default [
  ProcessoListRoute, ProcessoShowRoute
]
