package org.informservice.criproman.tipoParecerAuto

import grails.gorm.services.Service

@Service(TipoParecerAuto)
interface TipoParecerAutoService {

    TipoParecerAuto get(Serializable id)

    List<TipoParecerAuto> list(Map args)

    Long count()

    TipoParecerAuto delete(Serializable id)

    TipoParecerAuto save(TipoParecerAuto tipoParecerAuto)

}
