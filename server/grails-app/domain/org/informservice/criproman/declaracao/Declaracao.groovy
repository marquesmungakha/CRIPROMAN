package org.informservice.criproman.declaracao

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.processoInvestigacao.ProcessoInvestigacao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/declaracao')
class Declaracao extends PecaProcesso {

    static belongsTo = [processo: ProcessoInvestigacao]

    String toString(){
        "Auto Nº. ${numero}"
    }
}
