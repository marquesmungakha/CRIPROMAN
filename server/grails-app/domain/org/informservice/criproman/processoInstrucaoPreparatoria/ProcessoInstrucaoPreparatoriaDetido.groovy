package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.detido.Detido
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaDetido')
class ProcessoInstrucaoPreparatoriaDetido {

    ProcessoInstrucaoPreparatoria processo
    Detido detido

    static belongsTo = ProcessoInstrucaoPreparatoria

    static constraints = {
    }

    static mapping = {
        version false
    }
}
