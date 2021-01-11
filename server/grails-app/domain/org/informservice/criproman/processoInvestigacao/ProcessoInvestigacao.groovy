package org.informservice.criproman.processoInvestigacao

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.agente.Agente
import org.informservice.criproman.autoEntrada.AutoEntrada
import org.informservice.criproman.formaProcesso.FormaProcesso
import org.informservice.criproman.inspector.Inspector
import org.informservice.criproman.magistrado.Magistrado
import org.informservice.criproman.unidadeorganica.Orgao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInvestigacao')
class ProcessoInvestigacao implements Auditable {

    String numeroProcesso
    Orgao orgao
    String proveniencia
    Date dataEntrada
    byte[] anexo
    FormaProcesso formaProcesso
    Inspector inspector
    Magistrado magistrado
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static belongsTo = [numeroAuto: AutoEntrada]
    static hasMany = [agentes: Agente]

    static constraints = {
        uuid nullable: true
        numeroProcesso(nullable: false, unique: true)
        numeroAuto(nullable: false, unique: true)
        anexo(nullable: true)
        magistrado(nullable: true)
        formaProcesso(nullable: false)
        inspector(nullable: false)
        proveniencia(nullable: false)
         dataEntrada(nullable: true, blank: true, validator: { dataEntrada, urc ->
             return ((dataEntrada <= new Date()))
         })
    }

    @Override
    String toString() {
        "Processo Nº. ${numeroProcesso}"
    }
}
