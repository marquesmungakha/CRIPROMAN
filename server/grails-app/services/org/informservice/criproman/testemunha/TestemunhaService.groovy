package org.informservice.criproman.testemunha

import grails.gorm.services.Service

@Service(Testemunha)
interface TestemunhaService {

    Testemunha get(Serializable id)

    List<Testemunha> list(Map args)

    Long count()

    Testemunha delete(Serializable id)

    Testemunha save(Testemunha testemunha)

}
