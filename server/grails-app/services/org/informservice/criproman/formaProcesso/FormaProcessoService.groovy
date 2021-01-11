package org.informservice.criproman.formaProcesso

import grails.gorm.services.Service

@Service(FormaProcesso)
interface FormaProcessoService {

    FormaProcesso get(Serializable id)

    List<FormaProcesso> list(Map args)

    Long count()

    FormaProcesso delete(Serializable id)

    FormaProcesso save(FormaProcesso formaProcesso)

}
