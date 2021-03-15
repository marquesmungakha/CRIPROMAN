package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.arguido.Arguido
import org.informservice.criproman.situacaoPrisional.SituacaoPrisional

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaArguido')
class ProcessoInstrucaoPreparatoriaArguido {

    String localTrabalho
    String ocupacao
    SituacaoPrisional situacaoPrisional
    Date dataSituacaoPrisional
    ProcessoInstrucaoPreparatoria processo
    Arguido arguido

    static belongsTo = ProcessoInstrucaoPreparatoria

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
