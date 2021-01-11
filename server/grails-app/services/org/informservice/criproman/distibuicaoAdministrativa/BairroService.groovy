package org.informservice.criproman.distibuicaoAdministrativa

import grails.gorm.services.Service

@Service(Bairro)
interface BairroService {

    Bairro get(Serializable id)

    List<Bairro> list(Map args)

    Long count()

    Bairro delete(Serializable id)

    Bairro save(Bairro bairro)

}
