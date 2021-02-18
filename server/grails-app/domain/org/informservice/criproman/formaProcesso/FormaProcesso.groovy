package org.informservice.criproman.formaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/formaProcesso')
class FormaProcesso implements Auditable {

    String codigo
    String designacao
    boolean activo = true

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
