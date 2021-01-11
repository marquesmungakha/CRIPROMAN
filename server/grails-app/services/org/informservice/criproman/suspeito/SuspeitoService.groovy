package org.informservice.criproman.suspeito

import grails.gorm.services.Service

@Service(Suspeito)
interface SuspeitoService {

    Suspeito get(Serializable id)

    List<Suspeito> list(Map args)

    Long count()

    Suspeito delete(Serializable id)

    Suspeito save(Suspeito suspeito)

}
