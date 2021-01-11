package org.informservice.criproman.alocacao

import grails.gorm.services.Service

@Service(Alocacao)
interface AlocacaoService {

    Alocacao get(Serializable id)

    List<Alocacao> list(Map args)

    Long count()

    Alocacao delete(Serializable id)

    Alocacao save(Alocacao alocacao)

}
