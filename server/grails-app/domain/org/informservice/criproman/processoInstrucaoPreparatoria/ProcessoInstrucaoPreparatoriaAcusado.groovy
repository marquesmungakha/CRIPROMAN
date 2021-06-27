package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.acusado.Acusado
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaAcusado')
class ProcessoInstrucaoPreparatoriaAcusado {

    ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria
    Acusado acusado

    static belongsTo = ProcessoInstrucaoPreparatoria
    static constraints = {
    }
    static mapping = {
        version false
    }
}
