package org.informservice.criproman.tipoParecer

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/tipoParecer')
class TipoParecer implements Auditable{

    String codigo
    String designacao

    static mapping = {
        version false
    }

    static constraints = {
        codigo nullable: false, unique: true
        designacao nullable: false, unique: true
    }

    @Override
    String toString() {
        designacao
    }
}
