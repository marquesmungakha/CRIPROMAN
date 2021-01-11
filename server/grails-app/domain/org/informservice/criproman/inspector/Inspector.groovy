package org.informservice.criproman.inspector

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.quadro.Quadro

@Secured('ROLE_ADMIN')
@Resource(uri='/api/inspector')
class Inspector extends Quadro {

    String descricao
    String cargoOcupado

    static mapping = {
        version false
    }

    static constraints = {
        cargoOcupado(blank: false)
        descricao(blank: false, maxSize: 15000)
    }

    String toString() {
        return numero + " " + nome + " " + apelido
    }
}
