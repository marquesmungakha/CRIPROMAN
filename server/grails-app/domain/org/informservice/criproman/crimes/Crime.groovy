package org.informservice.criproman.crimes

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.classeJudicial.ClasseJudicial

// import grails.plugins.orm.auditable.Auditable

@Secured('ROLE_ADMIN')
@Resource(uri='/api/crime')
class Crime { //implements Auditable {
    String designacao
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static belongsTo = [classeJudicial: ClasseJudicial]
    static constraints = {
        uuid nullable: true
        designacao(blank: false, maxSize: 200, unique: ['classeJudicial'])
    }

    String toString() {
       designacao
    }
}
