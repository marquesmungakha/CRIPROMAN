package org.informservice.criproman.distibuicaoAdministrativa

import grails.gorm.services.Service

@Service(Distrito)
interface DistritoService {

    Distrito get(Serializable id)

    List<Distrito> list(Map args)

    Long count()

    Distrito delete(Serializable id)

    Distrito save(Distrito distrito)

}
