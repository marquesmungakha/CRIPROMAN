package org.informservice.criproman.possuidor

import grails.gorm.services.Service

@Service(Possuidor)
interface PossuidorService {

    Possuidor get(Serializable id)

    List<Possuidor> list(Map args)

    Long count()

    Possuidor delete(Serializable id)

    Possuidor save(Possuidor possuidor)

}
