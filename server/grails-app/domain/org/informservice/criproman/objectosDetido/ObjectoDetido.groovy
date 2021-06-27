package org.informservice.criproman.objectosDetido

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.detido.Detido

@Secured('ROLE_ADMIN')
@Resource(uri='/api/objectoDetido')
class ObjectoDetido implements Auditable{

    String descricao
    String estado

    static belongsTo = [detido: Detido]
    static constraints = {
        descricao nullable: false
        estado nullable: false
    }
}
