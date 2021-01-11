package org.informservice.criproman.seguranca

import grails.gorm.services.Service

@Service(SecUserSecRole)
interface SecUserSecRoleService {

    SecUserSecRole get(Serializable id)

    List<SecUserSecRole> list(Map args)

    Long count()

    SecUserSecRole delete(Serializable id)

    SecUserSecRole save(SecUserSecRole secUserSecRole)

}
