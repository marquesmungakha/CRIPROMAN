package org.informservice.criproman.denunciante

import grails.gorm.services.Service

@Service(Denunciante)
interface DenuncianteService {

    Denunciante get(Serializable id)

    List<Denunciante> list(Map args)

    Long count()

    Denunciante delete(Serializable id)

    Denunciante save(Denunciante denunciante)

}
