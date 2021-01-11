package org.informservice.criproman.ofendido

import grails.gorm.services.Service

@Service(Ofendido)
interface OfendidoService {

    Ofendido get(Serializable id)

    List<Ofendido> list(Map args)

    Long count()

    Ofendido delete(Serializable id)

    Ofendido save(Ofendido ofendido)

}
