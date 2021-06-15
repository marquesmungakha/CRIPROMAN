package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/postoAdministrativo')
class PostoAdministrativo { //implements Auditable {

    String designacao
    String codigo = UUID.randomUUID().toString()

    static belongsTo = [distrito: Distrito]
    static hasMany = [localidades: Localidade, bairros: Bairro]

    static constraints = {
        codigo(maxSize: 255)
        designacao(blank: false, maxSize: 200, unique: true)
    }

    static mapping = {
        version false
    }

    String toString() {
        return "${designacao}"
    }
}
