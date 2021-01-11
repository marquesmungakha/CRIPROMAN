package org.informservice.criproman.seguranca

import grails.gorm.services.Service

@Service(SecRole)
interface SecRoleService {

    SecRole get(Serializable id)

    List<SecRole> list(Map args)

    Long count()

    SecRole delete(Serializable id)

    SecRole save(SecRole secRole)

}
