package org.informservice.criproman.motivoDetencao

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/motivoDetencao')
class MotivoDetencao implements Auditable{

    String designacao

    static constraints = {
        designacao nullable: false, unique: true
    }
}
