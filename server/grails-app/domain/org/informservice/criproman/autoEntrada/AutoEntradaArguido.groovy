package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.arguido.Arguido
import org.informservice.criproman.situacaoPrisional.SituacaoPrisional

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaArguido')
class AutoEntradaArguido {

    String localTrabalho
    String ocupacao
    SituacaoPrisional situacaoPrisional
    Date dataSituacaoPrisional
    AutoEntrada autoEntrada
    Arguido arguido

    static belongsTo = AutoEntrada

    static constraints = {
    }
    static mapping = {
        version false
    }
}
