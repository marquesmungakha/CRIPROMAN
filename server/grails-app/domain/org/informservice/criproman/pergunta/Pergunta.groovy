package org.informservice.criproman.pergunta

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pergunta')
class Pergunta extends PecaProcesso {

    String descricaoOcorencia
    String interrogatorio

    static belongsTo = [processo: ProcessoInvestigacao]

    static constraints = {
        descricaoOcorencia(nullable: true, maxSize: 15000, blank: true)
        interrogatorio(nullable: false, maxSize: 15000, blank: false)
    }

    String toString(){
        "Auto Nº. ${numero}"
    }
}
