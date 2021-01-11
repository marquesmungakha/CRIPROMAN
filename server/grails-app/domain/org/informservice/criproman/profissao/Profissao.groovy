package org.informservice.criproman.profissao

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/profissao')
class Profissao implements Auditable{

    String designacao

    static mapping = {
        version false
    }

    static constraints = {
        designacao nullable: false, unique: true
    }

    @Override
    String toString() {
        return designacao
    }
}
