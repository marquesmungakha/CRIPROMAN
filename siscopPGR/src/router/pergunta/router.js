// Provincia Pages
import PerguntaList from 'pages/pergunta/index'
import PerguntaDetail from 'pages/pergunta/details'

const PerguntaListRoute =
  {
    path: '/pergunta',
    name: 'pergunta',
    component: PerguntaList
  }

const PerguntaShowRoute = {
  path: '/:id',
  name: 'perguntaDetails',
  component: PerguntaDetail,
  props: true
}

export default [
  PerguntaListRoute, PerguntaShowRoute
]
