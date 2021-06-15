package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.profissao.Profissao
import org.informservice.criproman.testemunha.Testemunha

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoTestemunha')
class PecaProcessoTestemunha {


    PecaProcesso pecaProcesso
    Testemunha testemunha
    Profissao profissao
    String ocupacao
    String depoimento

    static mapping = {
        version false
    }

    static belongsTo = PecaProcesso

    static constraints = {
        depoimento maxSize: 15000
        profissao nullable: true
        ocupacao nullable: true
    }
}
