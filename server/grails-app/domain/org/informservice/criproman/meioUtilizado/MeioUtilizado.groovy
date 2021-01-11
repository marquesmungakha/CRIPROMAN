package org.informservice.criproman.meioUtilizado

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.marca.Marca
import org.informservice.criproman.modelo.Modelo
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.tipoMeio.TipoMeio

@Secured('ROLE_ADMIN')
@Resource(uri='/api/meioUtilizado')
class MeioUtilizado implements Auditable{

    String matricula
    String cor
    String calibre
    String designacao
    TipoMeio tipoMeio
    Marca marca
    Modelo modelo
    String chassi
    String numeroMotor

    static mapping = {
        version false
    }

    static belongsTo = [pecaProcesso: PecaProcesso]

    static constraints = {
        matricula nullable: false, unique: true
        calibre nullable: true
        cor nullable: false
        designacao nullable: false
        chassi nullable: true
        numeroMotor nullable: true
        marca nullable: false
        modelo nullable: true
    }

    @Override
    String toString() {
        return designacao
    }
}
