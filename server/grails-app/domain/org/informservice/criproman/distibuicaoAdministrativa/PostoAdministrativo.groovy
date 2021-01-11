package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured

 import grails.plugins.orm.auditable.Auditable

import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/postoAdministrativo')
class PostoAdministrativo implements Auditable {

    String designacao
    String codigo

    static belongsTo = [distrito: Distrito]
    static hasMany = [localidades: Localidade, bairros: Bairro]

    static constraints = {
        codigo(maxSize: 20)
        designacao(blank: false, maxSize: 200, unique: true)
    }

    static mapping = {
        version false
        localidades cascade: 'delete-orphan'
        bairros cascade: 'delete-orphan'
    }

    String toString() {
        return "${designacao}"
    }
}
