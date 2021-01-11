package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource

@Secured('ROLE_ADMIN')
@Resource(uri='/api/provincia')
class Provincia implements Auditable {

    String designacao
    String codigo

    static constraints = {
        codigo(maxSize: 20)
        designacao(blank: false, maxSize: 200, unique: true)
    }

    static belongsTo = [pais: Pais]

    static hasMany = [distritos: Distrito]

    static mapping = {
        version false
        distritos cascade: 'delete-orphan'
    }

    String toString() {
        return "${designacao}"
    }
}
