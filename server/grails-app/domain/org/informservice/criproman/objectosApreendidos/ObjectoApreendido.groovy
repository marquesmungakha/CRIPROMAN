package org.informservice.criproman.objectosApreendidos

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/objectoApreendido')
class ObjectoApreendido implements Auditable{

    String descricao
    String tipo
    String estado

    static belongsTo = [pecaProcesso: PecaProcesso]
    static constraints = {
        descricao nullable: false
        tipo nullable: false
        estado nullable: false
    }
}
