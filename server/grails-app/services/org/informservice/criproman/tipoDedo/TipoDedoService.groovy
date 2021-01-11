package org.informservice.criproman.tipoDedo

import grails.gorm.services.Service

@Service(TipoDedo)
interface TipoDedoService {

    TipoDedo get(Serializable id)

    List<TipoDedo> list(Map args)

    Long count()

    TipoDedo delete(Serializable id)

    TipoDedo save(TipoDedo tipoDedo)

}
