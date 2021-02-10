package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.custodiado.Custodiado
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoCustodiado')
class PecaProcessoCustodiado {

    PecaProcesso pecaProcesso
    Custodiado custodiado

    static belongsTo = PecaProcesso

    static constraints = {
    }

    static mapping = {
        version false
    }
}
