package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.possuidor.Possuidor
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaPossuidor')
class ProcessoInstrucaoPreparatoriaPossuidor {

    String declaracao
    ProcessoInstrucaoPreparatoria processo
    Possuidor possuidor

    static belongsTo = ProcessoInstrucaoPreparatoria

    static constraints = {
        declaracao maxSize: 15000
    }

    static mapping = {
        version false
    }
}
