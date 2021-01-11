package org.informservice.criproman.marca

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.tipoMeio.TipoMeio

@Secured('ROLE_ADMIN')
@Resource(uri='/api/marca')
class Marca implements Auditable{

    String codigo
    String designacao

    static mapping = {
        version false
    }

    static belongsTo = [tipoMeio: TipoMeio]

    static constraints = {
        codigo nullable: false, unique: true
        designacao nullable: false
    }

    @Override
    String toString() {
        return designacao
    }
}
