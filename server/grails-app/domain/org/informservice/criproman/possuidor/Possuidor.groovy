package org.informservice.criproman.possuidor

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/possuidor')
class Possuidor extends Individuo {

    static belongsTo = [pecaProcesso: PecaProcesso]
    static constraints = {
    }

    String toString() {
        return nome + " " + apelido
    }
}
