package org.informservice.criproman.processoInvestigacao

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.agente.Agente
import org.informservice.criproman.autoEntrada.AutoEntrada
import org.informservice.criproman.formaProcesso.FormaProcesso
import org.informservice.criproman.inspector.Inspector
import org.informservice.criproman.magistrado.Magistrado

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInvestigacao')
class ProcessoInvestigacao { //implements Auditable {

    String numeroProcesso
    String proveniencia
    Date dataEntrada
    byte[] anexo
    FormaProcesso formaProcesso
    Inspector inspector
    Magistrado magistrado
    String autor
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
        autor(nullable: true)
         dataEntrada(nullable: true, blank: true, validator: { dataEntrada, urc ->
             return ((dataEntrada <= new Date()))
         })
    }

    @Override
    String toString() {
        "Processo Nº. ${numeroProcesso}"
    }
}
