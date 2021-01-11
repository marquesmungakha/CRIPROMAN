package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntrada)
interface AutoEntradaService {

    AutoEntrada get(Serializable id)

    List<AutoEntrada> list(Map args)

    Long count()

    AutoEntrada delete(Serializable id)

    AutoEntrada save(AutoEntrada autoEntrada)

}
