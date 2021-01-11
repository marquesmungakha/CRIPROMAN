package org.informservice.criproman.responsavelOrgao

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.cargo.Cargo
import org.informservice.criproman.inspector.Inspector
import org.informservice.criproman.quadro.Quadro
import org.informservice.criproman.unidadeorganica.Orgao

@Secured('ROLE_ADMIN')
@Resource(uri='/api/responsavelOrgao')
class ResponsavelOrgao implements Auditable {

    Orgao orgao
    Quadro membro
    Cargo cargo
    Date dataAtribuicao
    Date dataTermino
    boolean activo
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }
    static constraints = {
        uuid nullable: true
        orgao(unique: ['membro', 'cargo', 'dataAtribuicao'])
        dataAtribuicao(nullable: false, validator: { dataAtribuicao, urc ->
            return (dataAtribuicao <= new Date())
        })
        dataTermino(nullable: true, blank: true)
    }


    String toString() {
        return membro?.nome + " " + membro?.apelido + " - " + cargo
    }
}
