package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.classeJudicial.ClasseJudicial
import org.informservice.criproman.crimes.Crime
import org.informservice.criproman.orgao.Orgao
import org.informservice.criproman.pecaProcesso.PecaProcesso
import org.informservice.criproman.tipoauto.TipoAuto

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntrada')
class AutoEntrada extends PecaProcesso{

    TipoAuto tipoAuto
    ClasseJudicial classeJudicial
    Crime crime
    String modusOperandi
    Date horaOcorrencia
    String infraccao
    String endereco
    String responsavelLocal
    String contacto

//    static hasOne = [processo: ProcessoInvestigacao]
    static belongsTo = [orgao: Orgao]

    static constraints = {
        modusOperandi(nullable: false, maxSize: 15000, blank: false)
        endereco(nullable: false, maxSize: 15000, blank: false)
        infraccao(nullable: false, maxSize: 15000, blank: false)
        tipoAuto(nullable: false, blank: false)
        classeJudicial(nullable: false, blank: false)
        crime(nullable: false, blank: false)
        responsavelLocal(nullable: true, blank: true)
        contacto(nullable: true, blank: true)
        horaOcorrencia(nullable: false, blank: false, validator: { horaOcorrencia, urc ->
            return ((horaOcorrencia <= new Date()))
        })
    }

    @Override
    String toString() {
        "Auto Nº. ${numero}"
    }

}