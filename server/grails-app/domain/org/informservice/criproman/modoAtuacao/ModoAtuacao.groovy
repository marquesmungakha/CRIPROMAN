package org.informservice.criproman.modoAtuacao

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/modoAtuacao')
class ModoAtuacao implements Auditable{

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
