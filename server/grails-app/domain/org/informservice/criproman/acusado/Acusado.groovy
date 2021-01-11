package org.informservice.criproman.acusado

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/acusado')
class Acusado extends Individuo {

    static belongsTo = [pecaProcesso: PecaProcesso]
    static constraints = {
    }

    String toString() {
        nome + " " + apelido
    }

}
