package org.informservice.criproman.inspector

import grails.gorm.services.Service

@Service(Inspector)
interface InspectorService {

    Inspector get(Serializable id)

    List<Inspector> list(Map args)

    Long count()

    Inspector delete(Serializable id)

    Inspector save(Inspector inspector)

}
