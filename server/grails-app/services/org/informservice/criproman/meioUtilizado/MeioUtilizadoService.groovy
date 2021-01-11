package org.informservice.criproman.meioUtilizado

import grails.gorm.services.Service

@Service(MeioUtilizado)
interface MeioUtilizadoService {

    MeioUtilizado get(Serializable id)

    List<MeioUtilizado> list(Map args)

    Long count()

    MeioUtilizado delete(Serializable id)

    MeioUtilizado save(MeioUtilizado meioUtilizado)

}
