package org.informservice.criproman.arguido

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/arguido')
class Arguido extends Individuo {

    String localTrabalho
    String ocupacao

    static mapping = {
        version false
    }

    static constraints = {
        ocupacao(nullable: false, blank: false)
        localTrabalho(nullable: false, maxSize: 500, blank: false)
    }

    String toString() {
        nome + " " + apelido
    }
}