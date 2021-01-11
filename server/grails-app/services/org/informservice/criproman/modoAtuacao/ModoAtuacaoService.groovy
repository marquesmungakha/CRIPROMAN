package org.informservice.criproman.modoAtuacao

import grails.gorm.services.Service

@Service(ModoAtuacao)
interface ModoAtuacaoService {

    ModoAtuacao get(Serializable id)

    List<ModoAtuacao> list(Map args)

    Long count()

    ModoAtuacao delete(Serializable id)

    ModoAtuacao save(ModoAtuacao modoAtuacao)

}
