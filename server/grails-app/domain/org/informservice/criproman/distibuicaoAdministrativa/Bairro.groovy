package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
 import grails.plugins.orm.auditable.Auditable

import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/bairro')
class Bairro implements Auditable {

    String designacao
    Localidade localidade
    PostoAdministrativo postoAdministrativo

    static belongsTo = [distrito: Distrito]
    static mapping = {
        version false
    }

    static constraints = {
        designacao(blank: false, maxSize: 200)
        localidade(nullable: true)
        postoAdministrativo(nullable: true)
    }

    String toString() {
        return "${designacao}"
    }
}
