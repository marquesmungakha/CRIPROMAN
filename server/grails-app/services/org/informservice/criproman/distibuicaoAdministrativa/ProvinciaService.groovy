package org.informservice.criproman.distibuicaoAdministrativa

import grails.gorm.services.Service

@Service(Provincia)
interface ProvinciaService {

    Provincia get(Serializable id)

    List<Provincia> list(Map args)

    Long count()

    Provincia delete(Serializable id)

    Provincia save(Provincia provincia)

}
