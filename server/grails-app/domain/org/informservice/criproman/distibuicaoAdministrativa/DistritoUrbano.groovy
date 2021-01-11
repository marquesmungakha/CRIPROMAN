package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
 import grails.plugins.orm.auditable.Auditable

import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/distritoUrbano')
class DistritoUrbano implements Auditable {

    String nomeDistrito
    String sigla

    static mapping = {
        version false
        bairros cascade: 'delete-orphan'
    }

    static belongsTo = [cidade: Cidade]
    static hasMany = [bairros: Bairro]

    static constraints = {
        nomeDistrito(blank: false, nullable: false, unique: ['cidade'], maxSize: 500)
        sigla(blank: true, nullable: true, maxSize: 200)
    }

    String toString() {
        return "${nomeDistrito}"
    }

}
