package org.informservice.criproman.modelo

import grails.gorm.services.Service

@Service(Modelo)
interface ModeloService {

    Modelo get(Serializable id)

    List<Modelo> list(Map args)

    Long count()

    Modelo delete(Serializable id)

    Modelo save(Modelo modelo)

}
