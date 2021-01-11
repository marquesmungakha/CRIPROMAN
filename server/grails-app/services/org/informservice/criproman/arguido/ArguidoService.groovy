package org.informservice.criproman.arguido

import grails.gorm.services.Service

@Service(Arguido)
interface ArguidoService {

    Arguido get(Serializable id)

    List<Arguido> list(Map args)

    Long count()

    Arguido delete(Serializable id)

    Arguido save(Arguido arguido)

}
