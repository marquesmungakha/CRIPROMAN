package org.informservice.criproman.tipoauto

import grails.gorm.services.Service

@Service(TipoAuto)
interface TipoAutoService {

    TipoAuto get(Serializable id)

    List<TipoAuto> list(Map args)

    Long count()

    TipoAuto delete(Serializable id)

    TipoAuto save(TipoAuto tipoAuto)

}
