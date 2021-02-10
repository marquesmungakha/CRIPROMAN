package org.informservice.criproman.possuidor

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/possuidor')
class Possuidor extends Individuo {

    static constraints = {
    }

    static mapping = {
        version false
    }

    String toString() {
        return nome + " " + apelido
    }
}
