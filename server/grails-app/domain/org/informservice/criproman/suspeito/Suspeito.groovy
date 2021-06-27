package org.informservice.criproman.suspeito

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/suspeito')
class Suspeito extends Individuo {

    static mapping = {
        version false
    }

    String toString() {
        return nome + " " + apelido
    }
}
