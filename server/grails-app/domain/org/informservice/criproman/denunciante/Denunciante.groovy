package org.informservice.criproman.denunciante

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.individuo.Individuo
import org.informservice.criproman.pecaProcesso.PecaProcesso

@Secured('ROLE_ADMIN')
@Resource(uri='/api/denunciante')
class Denunciante extends Individuo {

    String descricaoOcorrencia
    String localTrabalho
    String ocupacao
    String qualidadeDe

    static belongsTo = [pecaProcesso: PecaProcesso]
    static mapping = {
        version false
    }

    static constraints = {
        qualidadeDe(inList: ['Familiar', 'Amigo', 'Outro'])
        ocupacao(nullable: false, blank: false)
        localTrabalho(nullable: false, maxSize: 500, blank: false)
        descricaoOcorrencia maxSize: 1500, nullable: false
    }

    String toString() {
        return nome + " " + apelido + " - " + qualidadeDe
    }
}
