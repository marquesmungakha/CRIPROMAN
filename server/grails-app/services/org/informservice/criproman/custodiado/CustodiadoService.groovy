package org.informservice.criproman.custodiado

import grails.gorm.services.Service

@Service(Custodiado)
interface CustodiadoService {

    Custodiado get(Serializable id)

    List<Custodiado> list(Map args)

    Long count()

    Custodiado delete(Serializable id)

    Custodiado save(Custodiado custodiado)

}
