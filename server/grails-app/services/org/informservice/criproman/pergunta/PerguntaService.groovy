package org.informservice.criproman.pergunta

import grails.gorm.services.Service

@Service(Pergunta)
interface PerguntaService {

    Pergunta get(Serializable id)

    List<Pergunta> list(Map args)

    Long count()

    Pergunta delete(Serializable id)

    Pergunta save(Pergunta pergunta)

}
