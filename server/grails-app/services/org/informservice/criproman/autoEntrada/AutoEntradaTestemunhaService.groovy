package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaTestemunha)
interface AutoEntradaTestemunhaService {

    AutoEntradaTestemunha get(Serializable id)

    List<AutoEntradaTestemunha> list(Map args)

    Long count()

    AutoEntradaTestemunha delete(Serializable id)

    AutoEntradaTestemunha save(AutoEntradaTestemunha autoEntradaTestemunha)

}
