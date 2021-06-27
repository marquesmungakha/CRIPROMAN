package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.detido.Detido
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoDetido')
class PecaProcessoDetido {

    PecaProcesso pecaProcesso
    Detido detido

    static belongsTo = PecaProcesso
    static constraints = {
    }

    static mapping = {
        version false
    }
}
