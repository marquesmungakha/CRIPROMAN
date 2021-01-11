package org.informservice.criproman.individuo

import grails.gorm.services.Service

@Service(Individuo)
interface IndividuoService {

    Individuo get(Serializable id)

    List<Individuo> list(Map args)

    Long count()

    Individuo delete(Serializable id)

    Individuo save(Individuo individuo)

}
