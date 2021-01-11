package org.informservice.criproman.tipoMeio

import grails.gorm.services.Service

@Service(TipoMeio)
interface TipoMeioService {

    TipoMeio get(Serializable id)

    List<TipoMeio> list(Map args)

    Long count()

    TipoMeio delete(Serializable id)

    TipoMeio save(TipoMeio tipoMeio)

}
