package org.informservice.criproman.tipoDocumentoIdentificacao

import grails.gorm.services.Service

@Service(TipoDocumentoIdentificacao)
interface TipoDocumentoIdentificacaoService {

    TipoDocumentoIdentificacao get(Serializable id)

    List<TipoDocumentoIdentificacao> list(Map args)

    Long count()

    TipoDocumentoIdentificacao delete(Serializable id)

    TipoDocumentoIdentificacao save(TipoDocumentoIdentificacao tipoDocumentoIdentificacao)

}
