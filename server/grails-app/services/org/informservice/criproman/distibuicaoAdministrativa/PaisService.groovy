package org.informservice.criproman.distibuicaoAdministrativa

import grails.gorm.services.Service

@Service(Pais)
interface PaisService {

    Pais get(Serializable id)

    List<Pais> list(Map args)

    Long count()

    Pais delete(Serializable id)

    Pais save(Pais pais)

}
