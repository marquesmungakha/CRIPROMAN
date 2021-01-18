package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.arguido.Arguido
import org.informservice.criproman.classeJudicial.ClasseJudicial
import org.informservice.criproman.crimes.Crime
import org.informservice.criproman.denunciante.Denunciante
import org.informservice.criproman.formaProcesso.FormaProcesso
import org.informservice.criproman.magistrado.Magistrado
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao
import org.informservice.criproman.orgao.Orgao
import org.informservice.criproman.vitima.Vitima

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoria')
class ProcessoInstrucaoPreparatoria implements Auditable {

    String numeroProcesso
    String proveniencia
    String historial
    Date dataEntrada
    ClasseJudicial classeJudicial
    Crime accoesCrimes
    FormaProcesso formaProcesso
    Magistrado magistrado
    byte[] anexo
    ProcessoInvestigacao processoInvestigacao
    String uuid = UUID.randomUUID().toString()

    static belongsTo = [orgao: Orgao]
    static hasMany = [arguidos: Arguido, denunciantes: Denunciante, vitimas: Vitima]
    static constraints = {
        numeroProcesso(nullable: false, unique: true)
        classeJudicial(nullable: false, blank: false)
        accoesCrimes(nullable: false, blank: false)
        formaProcesso(nullable: false, blank: false)
        magistrado(nullable: false, blank: false)
        historial(blank: false, maxSize: 15000)
        anexo(nullable: true)
        processoInvestigacao(nullable: true)
        proveniencia(nullable: false)
        dataEntrada(nullable: true, blank: true, validator: { dataEntrada, urc ->
            return ((dataEntrada <= new Date()))
        })
    }

    String toString() {
        "Processo Nº. ${numeroProcesso}"
    }
}
