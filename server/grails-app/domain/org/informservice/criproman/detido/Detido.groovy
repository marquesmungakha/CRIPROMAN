package org.informservice.criproman.detido

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/detido')
class Detido extends Individuo {

    static constraints = {
    }

    static mapping = {
        version false
    }

    String toString() {
        nome + " " + apelido
    }

}
