package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.detido.Detido

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaDetido')
class AutoEntradaDetido {

    AutoEntrada autoEntrada
    Detido detido

    static belongsTo = AutoEntrada

    static constraints = {
    }
    static mapping = {
        version false
    }
}
