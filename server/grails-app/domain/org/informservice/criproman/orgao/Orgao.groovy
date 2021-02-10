package org.informservice.criproman.orgao

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.dependenciaunidadeorganica.DependenciaOrgao

// import grails.plugins.orm.auditable.Auditable

import org.informservice.criproman.distibuicaoAdministrativa.Distrito
import org.informservice.criproman.distibuicaoAdministrativa.Provincia
import org.informservice.criproman.responsavelOrgao.ResponsavelOrgao
import org.informservice.criproman.tipoOrgao.TipoOrgao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/orgao')
class Orgao implements Auditable {

    String sigla
    String designacao
    int nivel
    Provincia provincia
    Distrito distrito
    String endereco
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static constraints = {
        uuid nullable: true
        endereco(nullable: true)
        distrito(nullable: true)
        sigla(nullable: true, unique: true)
        nivel(inList: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9])
        designacao(nullable: false, unique: true, blank: false)
    }

    static belongsTo = [tipoOrgao: TipoOrgao]

    static hasMany = [
            dependenciasOrgao: DependenciaOrgao,
            responsavelOrgao: ResponsavelOrgao
    ]

    String toString() {
         designacao
    }

}


