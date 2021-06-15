package org.informservice.criproman.autoEntrada

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.Resource
import org.informservice.criproman.bemSubtraido.BemSubtraido
import org.informservice.criproman.classeJudicial.ClasseJudicial
import org.informservice.criproman.crimes.Crime
import org.informservice.criproman.inspector.Inspector
import org.informservice.criproman.meioUtilizado.MeioUtilizado
import org.informservice.criproman.objectosApreendidos.ObjectoApreendido
import org.informservice.criproman.objectosDetido.ObjectoDetido
import org.informservice.criproman.orgao.Orgao
import org.informservice.criproman.tipoauto.TipoAuto

@Secured('ROLE_ADMIN')
@Resource(uri='/api/autoEntrada')
class AutoEntrada {

    TipoAuto tipoAuto
    ClasseJudicial classeJudicial
    Crime crime
    Inspector inspector
    String modusOperandi
    Date horaOcorrencia
    String infraccao
    String endereco
    String responsavelLocal
    String contacto
    String numero
    Date dataAbertura
    String descricao
    byte[] anexo
    String uuid = UUID.randomUUID().toString()
    static belongsTo = [orgao: Orgao]

    static hasMany = [declarantes        : AutoEntradaDeclarante,
                      denunciantes       : AutoEntradaDenunciante,
                      ofendidos          : AutoEntradaOfendido,
                      suspeitos          : AutoEntradaSuspeito,
                      meiosUtilizados    : MeioUtilizado,
                      bensSubtraidos     : BemSubtraido,
                      objectosApreendidos: ObjectoApreendido,
                      bens               : ObjectoDetido,
                      testeminhas        : AutoEntradaTestemunha,
                      vitimas            : AutoEntradaVitima,
                      acusados           : AutoEntradaAcusado,
                      detidos            : AutoEntradaDetido,
                      custodiados        : AutoEntradaCustodiado,
                      possuidores        : AutoEntradaPossuidor,
                      arguidos           : AutoEntradaArguido
    ]

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
        numero(nullable: false, unique: true)
        anexo(nullable: true)
        descricao(maxSize: 15000)
        dataAbertura(nullable: true, blank: true, validator: { dataEntrada, urc ->
            return ((dataEntrada <= new Date()))
        })
    }

    @Override
    String toString() {
        "Auto Nº. ${numero}"
    }

}
