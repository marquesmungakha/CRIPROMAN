package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.vitima.Vitima

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoVitima')
class PecaProcessoVitima {

    PecaProcesso pecaProcesso
    Vitima vitima

    static belongsTo = PecaProcesso

    static constraints = {
    }

    static mapping = {
        version false
    }
}
