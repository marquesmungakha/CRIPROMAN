package org.informservice.criproman.testemunha

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.profissao.Profissao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/testemunha')
class Testemunha extends Individuo {

    String depoimento

    static mapping = {
        version false
    }

    static belongsTo = [pecaProcesso: PecaProcesso]
    static constraints = {
        depoimento maxSize: 15000
    }

    String toString() {
        nome + " " + apelido
    }
}
