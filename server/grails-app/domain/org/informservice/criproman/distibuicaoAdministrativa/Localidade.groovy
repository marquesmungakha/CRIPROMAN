package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/localidade')
class Localidade { //implements Auditable {

    String designacao
    String codigo = UUID.randomUUID().toString()
    PostoAdministrativo postoAdministrativo

    static belongsTo = [distrito: Distrito]
    static hasMany = [bairros: Bairro]

    static constraints = {
        codigo(maxSize: 255)
        designacao(blank: false, maxSize: 200, unique: true)
        postoAdministrativo(nullable: true)
    }

    static mapping = {
        version false
    }

    String toString() {
        return designacao
    }
}
