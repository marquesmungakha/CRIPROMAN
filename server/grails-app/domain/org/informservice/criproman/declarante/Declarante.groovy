package org.informservice.criproman.declarante

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/declarante')
class Declarante extends Individuo {


    static mapping = {
        version false
    }

    String toString() {
        nome + " " + apelido
    }
}
