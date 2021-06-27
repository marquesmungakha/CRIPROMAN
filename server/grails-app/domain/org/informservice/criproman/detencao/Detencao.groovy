package org.informservice.criproman.detencao

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/detencao')
class Detencao extends PecaProcesso {

    String localDetencao
    String motivoDetencao
    // MotivoDetencao motivoDetencao

    static belongsTo = [processo: ProcessoInvestigacao]

    static constraints = {
        localDetencao nullable: false
    }

    String toString(){
        "Auto Nº. ${numero}"
    }
}
