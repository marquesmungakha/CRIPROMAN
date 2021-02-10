package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria
import org.informservice.criproman.situacaoPrisional.SituacaoPrisional
import org.informservice.criproman.suspeito.Suspeito

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaSuspeito')
class ProcessoInstrucaoPreparatoriaSuspeito {

    SituacaoPrisional situacaoPrisional
    Date dataSituacaoPrisional
    ProcessoInstrucaoPreparatoria processo
    Suspeito suspeito

    static belongsTo = ProcessoInstrucaoPreparatoria

    static mapping = {
        version false
    }

    static constraints = {
        dataSituacaoPrisional(nullable: true, blank: true, validator: { dataSituacaoPrisional, urc ->
            return ((dataSituacaoPrisional <= new Date()))
        })
    }
}
