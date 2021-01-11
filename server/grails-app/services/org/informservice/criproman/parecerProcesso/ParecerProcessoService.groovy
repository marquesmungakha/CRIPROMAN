package org.informservice.criproman.parecerProcesso

import grails.gorm.services.Service

@Service(ParecerProcesso)
interface ParecerProcessoService {

    ParecerProcesso get(Serializable id)

    List<ParecerProcesso> list(Map args)

    Long count()

    ParecerProcesso delete(Serializable id)

    ParecerProcesso save(ParecerProcesso parecerProcesso)

}
