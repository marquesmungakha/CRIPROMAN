package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable

import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pais')
class Pais implements Auditable {

    String designacao
    String nacionalidade
    String codigo

    static constraints = {
        codigo(maxSize: 20)
        designacao(blank: false, maxSize: 200, unique: true)
        nacionalidade(blank: true, maxSize: 200, unique: true)
    }

    static hasMany = [provincia: Provincia]

    static mapping = {
        version false
//        provincia cascade: 'delete-orphan'
    }

    String toString() {
        return "${designacao}"
    }
}
