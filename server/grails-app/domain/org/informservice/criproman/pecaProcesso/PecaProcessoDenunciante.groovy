package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.denunciante.Denunciante
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcessoDenunciante')
class PecaProcessoDenunciante {

    String descricaoOcorrencia
    String localTrabalho
    String ocupacao
    String qualidadeDe
    PecaProcesso pecaProcesso
    Denunciante denunciante

    static belongsTo = PecaProcesso
    static mapping = {
        version false
    }

    static constraints = {
        ocupacao(nullable: false, blank: false)
        localTrabalho(nullable: false, maxSize: 500, blank: false)
        descricaoOcorrencia maxSize: 1500, nullable: false
    }

}
