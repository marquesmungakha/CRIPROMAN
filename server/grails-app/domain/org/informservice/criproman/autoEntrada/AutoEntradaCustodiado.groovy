package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.custodiado.Custodiado

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaCustodiado')
class AutoEntradaCustodiado {

    AutoEntrada autoEntrada
    Custodiado custodiado

    static belongsTo = AutoEntrada

    static constraints = {
    }
    static mapping = {
        version false
    }
}
