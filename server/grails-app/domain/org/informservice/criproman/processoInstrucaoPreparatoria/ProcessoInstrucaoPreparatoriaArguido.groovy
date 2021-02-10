package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.arguido.Arguido

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaArguido')
class ProcessoInstrucaoPreparatoriaArguido {

    String localTrabalho
    String ocupacao
    ProcessoInstrucaoPreparatoria processo
    Arguido arguido

    static belongsTo = ProcessoInstrucaoPreparatoria

    static mapping = {
        version false
    }

    static constraints = {
        ocupacao(nullable: false, blank: false)
        localTrabalho(nullable: false, maxSize: 500, blank: false)
    }
}
