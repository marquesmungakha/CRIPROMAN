package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.declarante.Declarante
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaDeclarante')
class ProcessoInstrucaoPreparatoriaDeclarante {

    String declaracao
    ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria
    Declarante declarante

    static belongsTo = ProcessoInstrucaoPreparatoria
    static constraints = {
        declaracao(maxSize: 15000)
    }

    static mapping = {
        version false
    }

}
