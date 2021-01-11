package org.informservice.criproman.apreensao

import grails.gorm.services.Service

@Service(Apreensao)
interface ApreensaoService {

    Apreensao get(Serializable id)

    List<Apreensao> list(Map args)

    Long count()

    Apreensao delete(Serializable id)

    Apreensao save(Apreensao apreensao)

}
