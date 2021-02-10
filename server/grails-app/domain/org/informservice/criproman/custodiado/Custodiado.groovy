package org.informservice.criproman.custodiado

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/custodiado')
class Custodiado extends Individuo {

    static constraints = {
    }
    static mapping = {
        version false
    }

    String toString() {
        nome + " " + apelido
    }
}
