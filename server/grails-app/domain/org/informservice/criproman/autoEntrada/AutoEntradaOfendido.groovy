package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.ofendido.Ofendido

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaOfendido')
class AutoEntradaOfendido {

    AutoEntrada autoEntrada
    Ofendido ofendido

    static belongsTo = AutoEntrada

    static constraints = {
    }
    static mapping = {
        version false
    }
}
