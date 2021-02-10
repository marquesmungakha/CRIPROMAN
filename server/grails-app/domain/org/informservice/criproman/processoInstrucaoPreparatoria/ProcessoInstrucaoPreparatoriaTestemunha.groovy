package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria
import org.informservice.criproman.testemunha.Testemunha

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaTestemunha')
class ProcessoInstrucaoPreparatoriaTestemunha {

    String depoimento
    ProcessoInstrucaoPreparatoria processo
    Testemunha testemunha

    static mapping = {
        version false
    }

    static belongsTo = ProcessoInstrucaoPreparatoria

    static constraints = {
        depoimento maxSize: 15000
    }
}
