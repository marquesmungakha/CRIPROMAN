package org.informservice.criproman.parecerAuto

import grails.gorm.services.Service

@Service(ParecerAuto)
interface ParecerAutoService {

    ParecerAuto get(Serializable id)

    List<ParecerAuto> list(Map args)

    Long count()

    ParecerAuto delete(Serializable id)

    ParecerAuto save(ParecerAuto parecerAuto)

}
