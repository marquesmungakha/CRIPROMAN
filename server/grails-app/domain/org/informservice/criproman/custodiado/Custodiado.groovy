package org.informservice.criproman.custodiado

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/custodiado')
class Custodiado extends Individuo {

    static belongsTo = [pecaProcesso: PecaProcesso]
    static constraints = {
    }

    String toString() {
        nome + " " + apelido
    }
}
