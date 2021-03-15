package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria
import org.informservice.criproman.vitima.Vitima

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaVitima')
class ProcessoInstrucaoPreparatoriaVitima {

    ProcessoInstrucaoPreparatoria processo
    Vitima vitima
    String declaracao

    static belongsTo = ProcessoInstrucaoPreparatoria

    static constraints = {
        declaracao maxSize: 1500, nullable: false
    }

    static mapping = {
        version false
    }
}
