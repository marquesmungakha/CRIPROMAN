package org.informservice.criproman.detencao

import grails.gorm.services.Service

@Service(Detencao)
interface DetencaoService {

    Detencao get(Serializable id)

    List<Detencao> list(Map args)

    Long count()

    Detencao delete(Serializable id)

    Detencao save(Detencao detencao)

}
