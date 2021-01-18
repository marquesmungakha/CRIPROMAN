package org.informservice.criproman.objectosDetido

import grails.gorm.services.Service

@Service(ObjectoDetido)
interface ObjectosDetidoService {

    ObjectoDetido get(Serializable id)

    List<ObjectoDetido> list(Map args)

    Long count()

    ObjectoDetido delete(Serializable id)

    ObjectoDetido save(ObjectoDetido objectosDetido)

}
