package org.informservice.criproman.situacaoPrisional

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable

import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/situacaoPrisional')
class SituacaoPrisional implements Auditable {

    String codigo
    String designacao

    static mapping = {
        version false
    }

    static constraints = {
        codigo nullable: false, unique: true
        designacao(blank: false, maxSize: 200, unique: true)
    }

    String toString() {
        designacao
    }
}
