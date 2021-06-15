package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaCustodiado)
interface AutoEntradaCustodiadoService {

    AutoEntradaCustodiado get(Serializable id)

    List<AutoEntradaCustodiado> list(Map args)

    Long count()

    AutoEntradaCustodiado delete(Serializable id)

    AutoEntradaCustodiado save(AutoEntradaCustodiado autoEntradaCustodiado)

}
