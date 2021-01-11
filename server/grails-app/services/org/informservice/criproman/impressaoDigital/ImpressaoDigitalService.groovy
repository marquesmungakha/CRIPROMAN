package org.informservice.criproman.impressaoDigital

import grails.gorm.services.Service

@Service(ImpressaoDigital)
interface ImpressaoDigitalService {

    ImpressaoDigital get(Serializable id)

    List<ImpressaoDigital> list(Map args)

    Long count()

    ImpressaoDigital delete(Serializable id)

    ImpressaoDigital save(ImpressaoDigital impressaoDigital)

}
