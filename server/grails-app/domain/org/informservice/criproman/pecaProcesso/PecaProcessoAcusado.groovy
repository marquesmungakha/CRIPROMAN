package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.acusado.Acusado
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoAcusado')
class PecaProcessoAcusado {

    PecaProcesso pecaProcesso
    Acusado acusado

    static belongsTo = PecaProcesso
    static constraints = {
    }
    static mapping = {
        version false
    }
}
