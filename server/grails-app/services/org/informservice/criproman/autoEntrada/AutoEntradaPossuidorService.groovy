package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaPossuidor)
interface AutoEntradaPossuidorService {

    AutoEntradaPossuidor get(Serializable id)

    List<AutoEntradaPossuidor> list(Map args)

    Long count()

    AutoEntradaPossuidor delete(Serializable id)

    AutoEntradaPossuidor save(AutoEntradaPossuidor autoEntradaPossuidor)

}
