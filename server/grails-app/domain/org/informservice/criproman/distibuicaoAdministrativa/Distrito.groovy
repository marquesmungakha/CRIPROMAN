package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/distrito')
class Distrito implements Auditable {

    String designacao
    String codigo

    static mapping = {
        version false
    }


    static constraints = {
        codigo(maxSize: 20)
        designacao(blank: false, maxSize: 200, unique: true)
    }

    static belongsTo = [provincia: Provincia]

    static hasMany = [cidades: Cidade, localidades: Localidade,
                      postos : PostoAdministrativo, bairros: Bairro]

    String toString() {
        return "${designacao}"
    }
}
