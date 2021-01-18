import TipoDocumentoIdentificacaoList from 'pages/tipoDocumentoIdentificacao/index'
import TipoDocumentoIdentificacaoDetail from 'pages/tipoDocumentoIdentificacao/details'

const TipoDocumentoIdentificacaoListRoute =
  {
    path: '/tipoDocumentoIdentificacao',
    name: 'tipoDocumentoIdentificacao',
    component: TipoDocumentoIdentificacaoList
  }

const TipoDocumentoIdentificacaoShowRoute = {
  path: '/tipoDocumentoIdentificacao/:id',
  component: TipoDocumentoIdentificacaoDetail,
  props: true
}

export default [
  TipoDocumentoIdentificacaoListRoute, TipoDocumentoIdentificacaoShowRoute
]
