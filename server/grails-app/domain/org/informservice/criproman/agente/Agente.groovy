package org.informservice.criproman.agente

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao
import org.informservice.criproman.quadro.Quadro

@Secured('ROLE_ADMIN')
@Resource(uri='/api/agente')
class Agente extends Quadro {

    String descricao
    String cargoOcupado

    static mapping = {
        version false
    }

    static belongsTo = ProcessoInvestigacao
    static hasMany = [processos: ProcessoInvestigacao]
    static constraints = {
        descricao(blank: false, maxSize: 15000)
        cargoOcupado(blank: false)
    }

    String toString() {
        return numero + " " + nome + " " + apelido
    }
}
