package org.informservice.criproman.agente

import grails.gorm.services.Service

@Service(Agente)
interface AgenteService {

    Agente get(Serializable id)

    List<Agente> list(Map args)

    Long count()

    Agente delete(Serializable id)

    Agente save(Agente agente)

}
