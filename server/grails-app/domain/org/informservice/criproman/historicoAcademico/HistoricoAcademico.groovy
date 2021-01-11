package org.informservice.criproman.historicoAcademico

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/historicoAcademico')
class HistoricoAcademico implements Auditable {

    int anoIncio
    int anoFim
    String descricao
    String observacao

    static mapping = {
        version false
    }

    static belongsTo = [individuo: Individuo]

    static constraints = {
        descricao(blank: false, maxSize: 15000, unique: ['individuo', 'anoIncio'])
        anoIncio(range: 1930..(new Date().year + 1900))
        anoFim(range: 1930..(new Date().year + 1900))
        observacao(nullable: true, maxSize: 1500)
    }

    String toString() {
        return "${anoIncio} - ${anoFim}"
    }
}
