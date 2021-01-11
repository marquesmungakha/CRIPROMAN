package org.informservice.criproman.pecaProcesso

import grails.gorm.services.Service

@Service(PecaProcesso)
interface PecaProcessoService {

    PecaProcesso get(Serializable id)

    List<PecaProcesso> list(Map args)

    Long count()

    PecaProcesso delete(Serializable id)

    PecaProcesso save(PecaProcesso pecaProcesso)

}
