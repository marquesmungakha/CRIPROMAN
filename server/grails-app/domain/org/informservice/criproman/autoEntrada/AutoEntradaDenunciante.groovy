package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.denunciante.Denunciante

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntradaDenunciante')
class AutoEntradaDenunciante {

    String descricaoOcorrencia
    String localTrabalho
    String ocupacao
    String qualidadeDe
    AutoEntrada autoEntrada
    Denunciante denunciante

    static belongsTo = AutoEntrada

    static constraints = {
        ocupacao(nullable: false, blank: false)
        localTrabalho(nullable: false, maxSize: 500, blank: false)
        descricaoOcorrencia maxSize: 1500, nullable: false
    }
    static mapping = {
        version false
    }
}
