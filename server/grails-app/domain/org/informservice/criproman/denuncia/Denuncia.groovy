package org.informservice.criproman.denuncia

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/denuncia')
class Denuncia extends PecaProcesso {

    String procedimentoCriminal

    static belongsTo = [processo: ProcessoInvestigacao]

    static constraints = {
        procedimentoCriminal inList: ['Sim','Não'], nullable: false
    }

    String toString(){
        "Auto Nº. ${numero}"
    }
}
