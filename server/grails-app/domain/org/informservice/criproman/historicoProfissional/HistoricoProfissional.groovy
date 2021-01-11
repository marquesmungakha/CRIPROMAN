package org.informservice.criproman.historicoProfissional

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/historicoProfissional')
class HistoricoProfissional implements Auditable {

    int anoIncio
    int anoFim
    String descricao
    String cargoOcupado

    static mapping = {
        version false
    }

    static belongsTo = [individuo: Individuo]

    static constraints = {
        cargoOcupado(blank: false, unique: ['individuo', 'anoIncio'])
        descricao(blank: false, maxSize: 15000)
        anoIncio(range: (new Date().year + 1900)..1930)
        anoFim(range: (new Date().year + 1900)..1930)
    }

    String toString() {
        return "${anoIncio} - ${anoFim}"
    }
}
