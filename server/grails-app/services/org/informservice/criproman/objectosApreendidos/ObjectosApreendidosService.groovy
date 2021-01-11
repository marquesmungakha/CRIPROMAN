package org.informservice.criproman.objectosApreendidos

import grails.gorm.services.Service

@Service(ObjectosApreendido)
interface ObjectosApreendidosService {

    ObjectosApreendido get(Serializable id)

    List<ObjectosApreendido> list(Map args)

    Long count()

    ObjectosApreendido delete(Serializable id)

    ObjectosApreendido save(ObjectosApreendido objectosApreendidos)

}
