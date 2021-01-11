package org.informservice.criproman.tipoParecer

import grails.gorm.services.Service

@Service(TipoParecer)
interface TipoParecerService {

    TipoParecer get(Serializable id)

    List<TipoParecer> list(Map args)

    Long count()

    TipoParecer delete(Serializable id)

    TipoParecer save(TipoParecer tipoParecer)

}
