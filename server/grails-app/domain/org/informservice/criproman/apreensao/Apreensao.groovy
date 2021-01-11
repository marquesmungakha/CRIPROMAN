package org.informservice.criproman.apreensao

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/apreensao')
class Apreensao extends PecaProcesso {

    String localApreencao

    static belongsTo = [processo: ProcessoInvestigacao]
    static constraints = {
        localApreencao nullable: false
    }

    String toString(){
        "Auto Nº. ${numero}"
    }
}
