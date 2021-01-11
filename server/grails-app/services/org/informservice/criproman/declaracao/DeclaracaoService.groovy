package org.informservice.criproman.declaracao

import grails.gorm.services.Service

@Service(Declaracao)
interface DeclaracaoService {

    Declaracao get(Serializable id)

    List<Declaracao> list(Map args)

    Long count()

    Declaracao delete(Serializable id)

    Declaracao save(Declaracao declaracao)

}
