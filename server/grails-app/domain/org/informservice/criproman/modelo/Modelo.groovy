package org.informservice.criproman.modelo

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.marca.Marca

@Secured('ROLE_ADMIN')
@Resource(uri='/api/modelo')
class Modelo implements Auditable{

    String codigo
    String designacao

    static mapping = {
        version false
    }

    static belongsTo = [marca: Marca]

    static constraints = {
        codigo nullable: false, unique: true
        designacao nullable: false
    }

    @Override
    String toString() {
        return designacao
    }
}
