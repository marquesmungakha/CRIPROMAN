package org.informservice.criproman.classeJudicial

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

// import grails.plugins.orm.auditable.Auditable

import org.informservice.criproman.crimes.Crime

@Secured('ROLE_ADMIN')
@Resource(uri='/api/classeJudicial')
class ClasseJudicial implements Auditable {

    String designacao
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }


    static hasMany = [crimes: Crime]
    static constraints = {
        uuid nullable: true
        designacao(blank: false, maxSize: 200, unique: true)
    }

    String toString() {
       designacao
    }
}
