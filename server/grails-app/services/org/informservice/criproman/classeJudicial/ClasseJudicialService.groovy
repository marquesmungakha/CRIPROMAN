package org.informservice.criproman.classeJudicial

import grails.gorm.services.Service

@Service(ClasseJudicial)
interface ClasseJudicialService {

    ClasseJudicial get(Serializable id)

    List<ClasseJudicial> list(Map args)

    Long count()

    ClasseJudicial delete(Serializable id)

    ClasseJudicial save(ClasseJudicial classeJudicial)

}
