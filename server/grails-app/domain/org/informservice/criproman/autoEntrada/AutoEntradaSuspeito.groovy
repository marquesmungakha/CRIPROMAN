package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.situacaoPrisional.SituacaoPrisional
import org.informservice.criproman.suspeito.Suspeito

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaSuspeito')
class AutoEntradaSuspeito {

    SituacaoPrisional situacaoPrisional
    Date dataSituacaoPrisional
    AutoEntrada autoEntrada
    Suspeito suspeito

    static belongsTo = AutoEntrada

    static constraints = {
        dataSituacaoPrisional(nullable: true, blank: true, validator: { dataSituacaoPrisional, urc ->
            return ((dataSituacaoPrisional <= new Date()))
        })
    }
    static mapping = {
        version false
    }
}
