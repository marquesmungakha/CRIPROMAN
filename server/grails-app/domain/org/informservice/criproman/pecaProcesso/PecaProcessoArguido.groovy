package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.arguido.Arguido
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.situacaoPrisional.SituacaoPrisional

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoArguido')
class PecaProcessoArguido {

    String localTrabalho
    String ocupacao
    SituacaoPrisional situacaoPrisional
    Date dataSituacaoPrisional
    PecaProcesso pecaProcesso
    Arguido arguido

    static belongsTo = PecaProcesso

    static mapping = {
        version false
    }

    static constraints = {
        ocupacao(nullable: false, blank: false)
        localTrabalho(nullable: false, maxSize: 500, blank: false)
        dataSituacaoPrisional(nullable: true, blank: true, validator: { dataSituacaoPrisional, urc ->
            return ((dataSituacaoPrisional <= new Date()))
        })
    }
}
