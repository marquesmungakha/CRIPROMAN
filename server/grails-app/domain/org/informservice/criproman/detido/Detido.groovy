package org.informservice.criproman.detido

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.objectosDetido.ObjectosDetido
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/detido')
class Detido extends Individuo {

    static belongsTo = [pecaProcesso: PecaProcesso]
    static hasMany = [bens: ObjectosDetido]
    static constraints = {
    }

    String toString() {
        nome + " " + apelido
    }

}
