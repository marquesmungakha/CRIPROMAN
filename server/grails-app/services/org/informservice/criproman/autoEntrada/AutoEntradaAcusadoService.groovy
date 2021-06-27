package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaAcusado)
interface AutoEntradaAcusadoService {

    AutoEntradaAcusado get(Serializable id)

    List<AutoEntradaAcusado> list(Map args)

    Long count()

    AutoEntradaAcusado delete(Serializable id)

    AutoEntradaAcusado save(AutoEntradaAcusado autoEntradaAcusado)

}
