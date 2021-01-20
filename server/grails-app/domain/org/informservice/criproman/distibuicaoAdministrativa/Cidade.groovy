package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
 import grails.plugins.orm.auditable.Auditable

import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/cidade')
class Cidade implements Auditable {

    String designacao
    String codigo
    Distrito distrito

    static mapping = {
        version false
    }

    static constraints = {
        designacao(blank: false, maxSize: 200, unique: true)
        codigo(maxSize: 20)
        distrito(blank: true, nullable: true)
    }

    static belongsTo = [provincia: Provincia]

    String toString() {
        return designacao
    }
}
