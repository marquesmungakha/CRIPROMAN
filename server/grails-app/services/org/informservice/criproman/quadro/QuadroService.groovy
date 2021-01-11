package org.informservice.criproman.quadro

import grails.gorm.services.Service

@Service(Quadro)
interface QuadroService {

    Quadro get(Serializable id)

    List<Quadro> list(Map args)

    Long count()

    Quadro delete(Serializable id)

    Quadro save(Quadro quadro)

}
