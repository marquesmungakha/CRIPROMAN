package org.informservice.criproman.testemunha

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/testemunha')
class Testemunha extends Individuo {

    static mapping = {
        version false
    }

    String toString() {
        nome + " " + apelido
    }
}
