package org.informservice.criproman.vitima

import grails.gorm.services.Service

@Service(Vitima)
interface VitimaService {

    Vitima get(Serializable id)

    List<Vitima> list(Map args)

    Long count()

    Vitima delete(Serializable id)

    Vitima save(Vitima vitima)

}
