package org.informservice.criproman.crimes

import grails.gorm.services.Service

@Service(Crime)
interface CrimeService {

    Crime get(Serializable id)

    List<Crime> list(Map args)

    Long count()

    Crime delete(Serializable id)

    Crime save(Crime crime)

}
