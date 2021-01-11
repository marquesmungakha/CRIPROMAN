package org.informservice.criproman.declarante

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/declarante')
class Declarante extends Individuo {

    String declaracao

    static belongsTo = [pecaProcesso: PecaProcesso]
    static constraints = {
        declaracao(maxSize: 15000)
    }

    String toString() {
        nome + " " + apelido
    }
}
