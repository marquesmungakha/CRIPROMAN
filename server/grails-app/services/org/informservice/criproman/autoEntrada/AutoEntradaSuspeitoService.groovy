package org.informservice.criproman.autoEntrada

import grails.gorm.services.Service

@Service(AutoEntradaSuspeito)
interface AutoEntradaSuspeitoService {

    AutoEntradaSuspeito get(Serializable id)

    List<AutoEntradaSuspeito> list(Map args)

    Long count()

    AutoEntradaSuspeito delete(Serializable id)

    AutoEntradaSuspeito save(AutoEntradaSuspeito autoEntradaSuspeito)

}
