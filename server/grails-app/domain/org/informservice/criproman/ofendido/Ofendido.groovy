package org.informservice.criproman.ofendido

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/ofendido')
class Ofendido extends Individuo {

    static belongsTo = [pecaProcesso: PecaProcesso]
    static constraints = {
    }

    String toString() {
        return nome + " " + apelido
    }
}
