package org.informservice.criproman.acusado

import grails.gorm.services.Service

@Service(Acusado)
interface AcusadoService {

    Acusado get(Serializable id)

    List<Acusado> list(Map args)

    Long count()

    Acusado delete(Serializable id)

    Acusado save(Acusado acusado)

}
