package org.informservice.criproman.denunciante

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/denunciante')
class Denunciante extends Individuo {

    static mapping = {
        version false
    }

    String toString() {
        return nome + " " + apelido
    }
}
