package org.informservice.criproman.despacho

import grails.gorm.services.Service

@Service(Despacho)
interface DespachoService {

    Despacho get(Serializable id)

    List<Despacho> list(Map args)

    Long count()

    Despacho delete(Serializable id)

    Despacho save(Despacho despacho)

}
