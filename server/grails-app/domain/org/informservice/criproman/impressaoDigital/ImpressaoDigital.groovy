package org.informservice.criproman.impressaoDigital

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.tipoDedo.TipoDedo

@Secured('ROLE_ADMIN')
@Resource(uri='/api/impressaoDigital')
class ImpressaoDigital implements Auditable{

    byte[] anexo
    String nomeFicheiro

    static mapping = {
        version false
    }

    static belongsTo = [dedo: TipoDedo]

    static constraints = {
        anexo nullable: false
    }
}
