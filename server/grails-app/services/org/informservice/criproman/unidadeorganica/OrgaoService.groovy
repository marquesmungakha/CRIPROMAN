package org.informservice.criproman.unidadeorganica

import grails.gorm.services.Service

@Service(Orgao)
interface OrgaoService {

    Orgao get(Serializable id)

    List<Orgao> list(Map args)

    Long count()

    Orgao delete(Serializable id)

    Orgao save(Orgao orgao)

}
