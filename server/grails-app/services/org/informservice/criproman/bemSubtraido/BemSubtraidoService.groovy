package org.informservice.criproman.bemSubtraido

import grails.gorm.services.Service

@Service(BemSubtraido)
interface BemSubtraidoService {

    BemSubtraido get(Serializable id)

    List<BemSubtraido> list(Map args)

    Long count()

    BemSubtraido delete(Serializable id)

    BemSubtraido save(BemSubtraido bemSubtraido)

}
