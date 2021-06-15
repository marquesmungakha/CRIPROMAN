package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.acusado.Acusado

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaAcusado')
class AutoEntradaAcusado {

    AutoEntrada autoEntrada
    Acusado acusado

    static belongsTo = AutoEntrada
    static constraints = {
    }
    static mapping = {
        version false
    }
}
