package org.informservice.criproman.patrimonio

import grails.gorm.services.Service

@Service(Patrimonio)
interface PatrimonioService {

    Patrimonio get(Serializable id)

    List<Patrimonio> list(Map args)

    Long count()

    Patrimonio delete(Serializable id)

    Patrimonio save(Patrimonio patrimonio)

}
