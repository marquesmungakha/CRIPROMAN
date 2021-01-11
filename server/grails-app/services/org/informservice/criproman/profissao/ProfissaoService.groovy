package org.informservice.criproman.profissao

import grails.gorm.services.Service

@Service(Profissao)
interface ProfissaoService {

    Profissao get(Serializable id)

    List<Profissao> list(Map args)

    Long count()

    Profissao delete(Serializable id)

    Profissao save(Profissao profissao)

}
