package org.informservice.criproman.suspeito

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.profissao.Profissao
import org.informservice.criproman.situacaoPrisional.SituacaoPrisional

@Secured('ROLE_ADMIN')
@Resource(uri='/api/suspeito')
class Suspeito extends Individuo {

    SituacaoPrisional situacaoPrisional
    Date dataSituacaoPrisional

    static belongsTo = [pecaProcesso: PecaProcesso]
    static mapping = {
        version false
    }

    static constraints = {
         dataSituacaoPrisional(nullable: true, blank: true, validator: { dataSituacaoPrisional, urc ->
             return ((dataSituacaoPrisional <= new Date()))
         })
    }

    String toString() {
        return nome + " " + apelido
    }
}
