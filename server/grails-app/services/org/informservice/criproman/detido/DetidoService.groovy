package org.informservice.criproman.detido

import grails.gorm.services.Service

@Service(Detido)
interface DetidoService {

    Detido get(Serializable id)

    List<Detido> list(Map args)

    Long count()

    Detido delete(Serializable id)

    Detido save(Detido detido)

}
