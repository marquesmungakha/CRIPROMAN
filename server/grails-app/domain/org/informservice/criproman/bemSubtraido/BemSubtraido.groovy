package org.informservice.criproman.bemSubtraido

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.autoEntrada.AutoEntrada
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/bemSubtraido')
class BemSubtraido {

    String descricao
    double valorPrejuizo
    AutoEntrada autoEntrada
    PecaProcesso pecaProcesso

    static constraints = {
        autoEntrada nullable: true
        pecaProcesso nullable: true
    }
}
