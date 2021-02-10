package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.situacaoPrisional.SituacaoPrisional
import org.informservice.criproman.suspeito.Suspeito

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoSuspeito')
class PecaProcessoSuspeito {

    SituacaoPrisional situacaoPrisional
    Date dataSituacaoPrisional
    PecaProcesso pecaProcesso
    Suspeito suspeito


    static belongsTo = PecaProcesso

    static mapping = {
        version false
    }

    static constraints = {
        dataSituacaoPrisional(nullable: true, blank: true, validator: { dataSituacaoPrisional, urc ->
            return ((dataSituacaoPrisional <= new Date()))
        })
    }
}
