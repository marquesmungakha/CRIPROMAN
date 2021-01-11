package org.informservice.criproman.patrimonio

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/patrimonio')
class Patrimonio implements Auditable {

    String designacao
    String descricao
    Double valorPrejuizo
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static belongsTo = [auto: PecaProcesso]
    static constraints = {
        uuid nullable: true
        descricao(nullable: false, unique: ['auto'])
        designacao(nullable: false, blank: false)
        valorPrejuizo(nullable: false, blank: false, validator: { datanasvalorPrejuizocimento, urc ->
            return ((valorPrejuizo <= 0))
        })
    }

    String toString() {
        return designacao + " - valor do prejuizo" + valorPrejuizo
    }
}
