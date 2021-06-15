package org.informservice.criproman.parecerProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.magistrado.Magistrado
import org.informservice.criproman.orgao.Orgao
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria
import org.informservice.criproman.tipoParecer.TipoParecer

@Secured('ROLE_ADMIN')
@Resource(uri='/api/parecerProcesso')
class ParecerProcesso { //implements Auditable {

    String parecer
    TipoParecer tipoParecer
    Date dataRegisto
    Orgao destino
    String uuid = UUID.randomUUID().toString()
    Magistrado magistrado

    static mapping = {
        version false
    }

    static belongsTo = [processo: ProcessoInstrucaoPreparatoria]

    static constraints = {
        parecer(maxSize: 15000, nullable: true)
        dataRegisto(nullable: true, blank: true, validator: { dataRegisto, urc ->
            return ((dataRegisto <= new Date()))
        })
    }
//
//    String toString() {
//        return processo + " [" + tipoParecer + "]"
//    }
}
