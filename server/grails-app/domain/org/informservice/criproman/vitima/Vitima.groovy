package org.informservice.criproman.vitima

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/vitima')
class Vitima extends Individuo { //implements Auditable {

    String toString() {
        return nome + " " + apelido
    }
}
