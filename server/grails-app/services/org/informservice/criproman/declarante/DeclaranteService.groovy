package org.informservice.criproman.declarante

import grails.gorm.services.Service

@Service(Declarante)
interface DeclaranteService {

    Declarante get(Serializable id)

    List<Declarante> list(Map args)

    Long count()

    Declarante delete(Serializable id)

    Declarante save(Declarante declarante)

}
