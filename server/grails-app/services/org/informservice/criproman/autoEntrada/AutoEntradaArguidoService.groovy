package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaArguido)
interface AutoEntradaArguidoService {

    AutoEntradaArguido get(Serializable id)

    List<AutoEntradaArguido> list(Map args)

    Long count()

    AutoEntradaArguido delete(Serializable id)

    AutoEntradaArguido save(AutoEntradaArguido autoEntradaArguido)

}
