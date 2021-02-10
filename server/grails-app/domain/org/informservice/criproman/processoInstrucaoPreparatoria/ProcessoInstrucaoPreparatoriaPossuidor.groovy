package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.possuidor.Possuidor
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaPossuidor')
class ProcessoInstrucaoPreparatoriaPossuidor {

    ProcessoInstrucaoPreparatoria processo
    Possuidor possuidor

    static belongsTo = ProcessoInstrucaoPreparatoria

    static constraints = {
    }

    static mapping = {
        version false
    }
}
