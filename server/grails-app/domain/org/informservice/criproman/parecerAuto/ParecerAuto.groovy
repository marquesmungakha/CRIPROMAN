package org.informservice.criproman.parecerAuto

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.autoEntrada.AutoEntrada
import org.informservice.criproman.inspector.Inspector
import org.informservice.criproman.tipoParecerAuto.TipoParecerAuto

@Secured('ROLE_ADMIN')
@Resource(uri='/api/parecerAuto')
class ParecerAuto implements Auditable {

    String parecer
    TipoParecerAuto tipoParecer
    Date dataRegisto
    String uuid = UUID.randomUUID().toString()
    Inspector inspector

    static mapping = {
        version false
    }

    static belongsTo = [auto: AutoEntrada]

    static constraints = {
        parecer(maxSize: 15000, nullable: true)
        dataRegisto(nullable: true, blank: true, validator: { dataRegisto, urc ->
            return ((dataRegisto <= new Date()))
        })
    }
}
