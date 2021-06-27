package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.possuidor.Possuidor

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaPossuidor')
class AutoEntradaPossuidor {

    String declaracao
    AutoEntrada autoEntrada
    Possuidor possuidor

    static belongsTo = AutoEntrada

    static constraints = {
        declaracao maxSize: 15000
    }
    static mapping = {
        version false
    }
}
