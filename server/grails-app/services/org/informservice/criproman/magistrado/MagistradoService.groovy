package org.informservice.criproman.magistrado

import grails.gorm.services.Service

@Service(Magistrado)
interface MagistradoService {

    Magistrado get(Serializable id)

    List<Magistrado> list(Map args)

    Long count()

    Magistrado delete(Serializable id)

    Magistrado save(Magistrado magistrado)

}
