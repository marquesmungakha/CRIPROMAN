package org.informservice.criproman.arguido

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/arguido')
class Arguido extends Individuo {

    static mapping = {
        version false
    }

    String toString() {
        nome + " " + apelido
    }
}