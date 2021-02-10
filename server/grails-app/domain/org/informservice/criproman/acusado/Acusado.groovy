package org.informservice.criproman.acusado

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/acusado')
class Acusado extends Individuo {

    static constraints = {
    }
    static mapping = {
        version false
    }
    String toString() {
        nome + " " + apelido
    }

}
