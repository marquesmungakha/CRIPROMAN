package org.informservice.criproman.despacho

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.inspector.Inspector
import org.informservice.criproman.orgao.Orgao
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao
import org.informservice.criproman.tipoParecer.TipoParecer

@Secured('ROLE_ADMIN')
@Resource(uri='/api/despacho')
class Despacho implements Auditable {

    String parecer
    TipoParecer tipoParecer
    Date dataRegisto
    Orgao destino
    byte[] anexo
    String uuid = UUID.randomUUID().toString()
    Inspector inspector

    static mapping = {
        version false
    }

    static belongsTo = [processo: ProcessoInvestigacao]

    static constraints = {
        anexo nullable: true
        parecer(maxSize: 15000, nullable: true)
        dataRegisto(nullable: true, blank: true, validator: { dataRegisto, urc ->
            return ((dataRegisto <= new Date()))
        })
    }

}