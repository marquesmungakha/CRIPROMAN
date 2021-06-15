package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaVitima)
interface AutoEntradaVitimaService {

    AutoEntradaVitima get(Serializable id)

    List<AutoEntradaVitima> list(Map args)

    Long count()

    AutoEntradaVitima delete(Serializable id)

    AutoEntradaVitima save(AutoEntradaVitima autoEntradaVitima)

}
