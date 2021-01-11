package org.informservice.criproman.objectosDetido

import grails.gorm.services.Service

@Service(ObjectosDetido)
interface ObjectosDetidoService {

    ObjectosDetido get(Serializable id)

    List<ObjectosDetido> list(Map args)

    Long count()

    ObjectosDetido delete(Serializable id)

    ObjectosDetido save(ObjectosDetido objectosDetido)

}
