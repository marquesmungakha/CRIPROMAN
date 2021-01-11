package org.informservice.criproman.distibuicaoAdministrativa

import grails.gorm.services.Service

@Service(DistritoUrbano)
interface DistritoUrbanoService {

    DistritoUrbano get(Serializable id)

    List<DistritoUrbano> list(Map args)

    Long count()

    DistritoUrbano delete(Serializable id)

    DistritoUrbano save(DistritoUrbano distritoUrbano)

}
