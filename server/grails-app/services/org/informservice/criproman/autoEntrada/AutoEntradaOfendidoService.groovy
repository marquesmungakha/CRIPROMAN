package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaOfendido)
interface AutoEntradaOfendidoService {

    AutoEntradaOfendido get(Serializable id)

    List<AutoEntradaOfendido> list(Map args)

    Long count()

    AutoEntradaOfendido delete(Serializable id)

    AutoEntradaOfendido save(AutoEntradaOfendido autoEntradaOfendido)

}
