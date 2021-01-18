package org.informservice.criproman.objectosApreendidos

import grails.gorm.services.Service

@Service(ObjectoApreendido)
interface ObjectosApreendidosService {

    ObjectoApreendido get(Serializable id)

    List<ObjectoApreendido> list(Map args)

    Long count()

    ObjectoApreendido delete(Serializable id)

    ObjectoApreendido save(ObjectoApreendido objectosApreendidos)

}
