package org.informservice.criproman.acarreacao

import grails.gorm.services.Service

@Service(Acarreacao)
interface AcarreacaoService {

    Acarreacao get(Serializable id)

    List<Acarreacao> list(Map args)


    Long count()

    Acarreacao delete(Serializable id)

    Acarreacao save(Acarreacao acarreacao)

}
