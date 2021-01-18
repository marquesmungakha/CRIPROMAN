package org.informservice.criproman.alocacao

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.funcao.Funcao
import org.informservice.criproman.quadro.Quadro
import org.informservice.criproman.orgao.Orgao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/alocacao')
class Alocacao {

    Date data
    Funcao funcao
    String descricao
    Quadro quadro
    boolean activo = true

    static mapping = {
        version false
    }

    static belongsTo = [orgao: Orgao]

    static constraints = {
        descricao descricao: false
        data(validator: { dataAprovacao -> return dataAprovacao <= new Date() })
    }
}
