package org.informservice.criproman.cargo

import grails.gorm.services.Service

@Service(Cargo)
interface CargoService {

    Cargo get(Serializable id)

    List<Cargo> list(Map args)

    Long count()

    Cargo delete(Serializable id)

    Cargo save(Cargo cargo)

}
