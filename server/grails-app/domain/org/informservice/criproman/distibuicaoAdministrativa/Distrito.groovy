package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/distrito')
class Distrito { //implements Auditable {

    String designacao
    String codigo

    static mapping = {
        version false
    }


    static constraints = {
        codigo(maxSize: 20, unique: ["provincia"])
        designacao(blank: false, maxSize: 200)
    }

    static belongsTo = [provincia: Provincia]

    static hasMany = [cidades: Cidade, localidades: Localidade,
                      postos : PostoAdministrativo, bairros: Bairro]

    String toString() {
        return "${designacao}"
    }
}
