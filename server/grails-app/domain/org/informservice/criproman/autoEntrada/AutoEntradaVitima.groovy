package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.vitima.Vitima

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaVitima')
class AutoEntradaVitima {

    AutoEntrada autoEntrada
    Vitima vitima
    String declaracao

    static belongsTo = AutoEntrada

    static constraints = {
        declaracao maxSize: 1500, nullable: false
    }
    static mapping = {
        version false
    }
}
