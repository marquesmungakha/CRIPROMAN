package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.possuidor.Possuidor

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoPossuidor')
class PecaProcessoPossuidor {

    PecaProcesso pecaProcesso
    Possuidor possuidor

    static belongsTo = PecaProcesso

    static constraints = {
    }

    static mapping = {
        version false
    }
}
