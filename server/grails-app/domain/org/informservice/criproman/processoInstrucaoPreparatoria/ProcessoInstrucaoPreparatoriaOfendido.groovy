package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.ofendido.Ofendido
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaOfendido')
class ProcessoInstrucaoPreparatoriaOfendido {

    ProcessoInstrucaoPreparatoria processo
    Ofendido ofendido

    static belongsTo = ProcessoInstrucaoPreparatoria

    static constraints = {
    }

    static mapping = {
        version false
    }

}
