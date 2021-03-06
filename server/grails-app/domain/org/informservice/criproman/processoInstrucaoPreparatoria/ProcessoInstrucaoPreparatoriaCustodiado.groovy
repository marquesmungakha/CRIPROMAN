package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.custodiado.Custodiado
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaCustodiado')
class ProcessoInstrucaoPreparatoriaCustodiado {

    ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria
    Custodiado custodiado

    static belongsTo = ProcessoInstrucaoPreparatoria
    static constraints = {
    }

    static mapping = {
        version false
    }
}
