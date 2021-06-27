package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaDetido)
interface AutoEntradaDetidoService {

    AutoEntradaDetido get(Serializable id)

    List<AutoEntradaDetido> list(Map args)

    Long count()

    AutoEntradaDetido delete(Serializable id)

    AutoEntradaDetido save(AutoEntradaDetido autoEntradaDetido)

}
