package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.ofendido.Ofendido
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoOfendido')
class PecaProcessoOfendido {

    PecaProcesso pecaProcesso
    Ofendido ofendido

    static belongsTo = PecaProcesso

    static constraints = {
    }

    static mapping = {
        version false
    }
}
