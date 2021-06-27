package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.profissao.Profissao
import org.informservice.criproman.testemunha.Testemunha

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaTestemunha')
class AutoEntradaTestemunha {

    AutoEntrada autoEntrada
    Testemunha testemunha
    Profissao profissao
    String ocupacao
    String depoimento

    static belongsTo = AutoEntrada


    static constraints = {
        depoimento maxSize: 15000
        profissao nullable: true
        ocupacao nullable: true
    }
    static mapping = {
        version false
    }
}
