package org.informservice.criproman.seguranca

import grails.gorm.services.Service

@Service(AuthenticationToken)
interface AuthenticationTokenService {

    AuthenticationToken get(Serializable id)

    List<AuthenticationToken> list(Map args)

    Long count()

    AuthenticationToken delete(Serializable id)

    AuthenticationToken save(AuthenticationToken authenticationToken)

}
