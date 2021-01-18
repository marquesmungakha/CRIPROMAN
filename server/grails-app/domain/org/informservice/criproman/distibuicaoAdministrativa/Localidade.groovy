package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
 import grails.plugins.orm.auditable.Auditable

import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/localidade')
class Localidade implements Auditable {

    String designacao
    String codigo
    PostoAdministrativo postoAdministrativo

    static belongsTo = [distrito: Distrito]
    static hasMany = [bairros: Bairro]

    static constraints = {
        codigo(maxSize: 20)
        designacao(blank: false, maxSize: 200, unique: true)
        postoAdministrativo(nullable: true)
    }

    static mapping = {
        version false
//        bairros cascade: 'delete-orphan'
    }

    String toString() {
        return designacao
    }
}
