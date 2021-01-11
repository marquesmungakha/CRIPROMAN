package org.informservice.criproman.corpoDelito

import grails.gorm.services.Service

@Service(CorpoDelito)
interface CorpoDelitoService {

    CorpoDelito get(Serializable id)

    List<CorpoDelito> list(Map args)

    Long count()

    CorpoDelito delete(Serializable id)

    CorpoDelito save(CorpoDelito corpoDelito)

}
