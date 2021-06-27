package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.classeJudicial.ClasseJudicial
import org.informservice.criproman.crimes.Crime
import org.informservice.criproman.formaProcesso.FormaProcesso
import org.informservice.criproman.magistrado.Magistrado
import org.informservice.criproman.orgao.Orgao
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoria')
class ProcessoInstrucaoPreparatoria { //implements Auditable {

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
    String autor
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static belongsTo = [orgao: Orgao]
    static hasMany = [
            arguidos: ProcessoInstrucaoPreparatoriaArguido,
            acudados: ProcessoInstrucaoPreparatoriaAcusado,
            custodiados: ProcessoInstrucaoPreparatoriaCustodiado,
            declarantes: ProcessoInstrucaoPreparatoriaDeclarante,
            denunciantes: ProcessoInstrucaoPreparatoriaDenunciante,
            detidos: ProcessoInstrucaoPreparatoriaDetido,
            ofendidos: ProcessoInstrucaoPreparatoriaOfendido,
            possuidores: ProcessoInstrucaoPreparatoriaPossuidor,
            suspeitos: ProcessoInstrucaoPreparatoriaSuspeito,
            testemunhas: ProcessoInstrucaoPreparatoriaTestemunha,
            vitimas: ProcessoInstrucaoPreparatoriaVitima

    ]
    static constraints = {
        numeroProcesso(nullable: false, unique: true)
        classeJudicial(nullable: false, blank: false)
        accoesCrimes(nullable: false, blank: false)
        formaProcesso(nullable: false, blank: false)
        magistrado(nullable: false, blank: false)
        historial(blank: false, maxSize: 15000)
        anexo(nullable: true)
        autor(nullable: true)
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
