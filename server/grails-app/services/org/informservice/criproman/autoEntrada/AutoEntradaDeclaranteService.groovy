package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaDeclarante)
interface AutoEntradaDeclaranteService {

    AutoEntradaDeclarante get(Serializable id)

    List<AutoEntradaDeclarante> list(Map args)

    Long count()

    AutoEntradaDeclarante delete(Serializable id)

    AutoEntradaDeclarante save(AutoEntradaDeclarante autoEntradaDeclarante)

}
