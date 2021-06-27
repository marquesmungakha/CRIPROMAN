package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.declarante.Declarante
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoDeclarante')
class PecaProcessoDeclarante {

    PecaProcesso pecaProcesso
    Declarante declarante
    String declaracao

    static belongsTo = PecaProcesso
    static constraints = {
        declaracao(maxSize: 15000)
    }

    static mapping = {
        version false
    }

}
