package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.denunciante.Denunciante
import org.informservice.criproman.processoInstrucaoPreparatoria.ProcessoInstrucaoPreparatoria

@Secured('ROLE_ADMIN')
@Resource(uri='/api/processoInstrucaoPreparatoriaDenunciante')
class ProcessoInstrucaoPreparatoriaDenunciante {

    String descricaoOcorrencia
    String localTrabalho
    String ocupacao
    String qualidadeDe
    ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria
    Denunciante denunciante

    static belongsTo = ProcessoInstrucaoPreparatoria
    static mapping = {
        version false
    }

    static constraints = {
        qualidadeDe(inList: ['Familiar', 'Amigo', 'Outro'])
        ocupacao(nullable: false, blank: false)
        localTrabalho(nullable: false, maxSize: 500, blank: false)
        descricaoOcorrencia maxSize: 1500, nullable: false
    }
}
