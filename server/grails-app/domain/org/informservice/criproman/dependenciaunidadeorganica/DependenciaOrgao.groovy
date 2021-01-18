package org.informservice.criproman.dependenciaunidadeorganica

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.orgao.Orgao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/dependenciaOrgao')
class DependenciaOrgao extends Orgao {

    static belongsTo = [
            orgao: Orgao
    ]

    static mapping = {
        version false
    }

}
