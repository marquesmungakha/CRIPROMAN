package org.informservice.criproman.tipoDedo

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/tipoDedo')
class TipoDedo implements Auditable{

    String designacao

    static mapping = {
        version false
    }

    static constraints = {
        designacao nullable: false
    }

    @Override
    String toString() {
        designacao
    }
}
