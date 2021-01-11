package org.informservice.criproman.vitima

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/vitima')
class Vitima extends Individuo implements Auditable {

    static mapping = {
        version false
    }

    static belongsTo = [pecaProcesso: PecaProcesso]
    static constraints = {
    }

    String toString() {
        return nome + " " + apelido
    }
}
