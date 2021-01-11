package org.informservice.criproman.funcao

import grails.gorm.services.Service

@Service(Funcao)
interface FuncaoService {

    Funcao get(Serializable id)

    List<Funcao> list(Map args)

    Long count()

    Funcao delete(Serializable id)

    Funcao save(Funcao funcao)

}
