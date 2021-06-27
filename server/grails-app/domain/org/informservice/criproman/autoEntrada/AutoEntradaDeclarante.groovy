package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.declarante.Declarante

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaDeclarante')
class AutoEntradaDeclarante {

    AutoEntrada autoEntrada
    Declarante declarante
    String declaracao

    static belongsTo = AutoEntrada

    static constraints = {
        declaracao(maxSize: 15000)
    }
    static mapping = {
        version false
    }
}
