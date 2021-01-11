package org.informservice.criproman.situacaoPrisional

import grails.gorm.services.Service

@Service(SituacaoPrisional)
interface SituacaoPrisionalService {

    SituacaoPrisional get(Serializable id)

    List<SituacaoPrisional> list(Map args)

    Long count()

    SituacaoPrisional delete(Serializable id)

    SituacaoPrisional save(SituacaoPrisional situacaoPrisional)

}
