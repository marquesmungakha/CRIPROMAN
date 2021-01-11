package org.informservice.criproman.cargo

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable

// import grails.plugins.orm.auditable.Auditable

import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/cargo')
class Cargo implements Auditable {

    static auditable = true
    String designacao

    static mapping = {
        version false
    }

    static constraints = {
        designacao(blank: false, maxSize: 200, unique: true)
    }

    String toString() {
        return "${designacao}"
    }
}
