package org.informservice.criproman.seguranca

import grails.gorm.services.Service

@Service(SecUser)
interface SecUserService {

    SecUser get(Serializable id)

    List<SecUser> list(Map args)

    Long count()

    SecUser delete(Serializable id)

    SecUser save(SecUser secUser)

}
