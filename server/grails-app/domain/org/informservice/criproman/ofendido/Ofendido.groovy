package org.informservice.criproman.ofendido

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/ofendido')
class Ofendido extends Individuo {

    static constraints = {
    }

    static mapping = {
        version false
    }

    String toString() {
        return nome + " " + apelido
    }
}
