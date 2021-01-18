package org.informservice.criproman.pecaProcesso


import grails.plugins.orm.auditable.Auditable
import org.informservice.criproman.acusado.Acusado
import org.informservice.criproman.arguido.Arguido
import org.informservice.criproman.bemSubtraido.BemSubtraido
import org.informservice.criproman.custodiado.Custodiado
import org.informservice.criproman.declarante.Declarante
import org.informservice.criproman.denunciante.Denunciante
import org.informservice.criproman.detido.Detido
import org.informservice.criproman.inspector.Inspector
import org.informservice.criproman.meioUtilizado.MeioUtilizado
import org.informservice.criproman.objectosApreendidos.ObjectoApreendido
import org.informservice.criproman.ofendido.Ofendido
import org.informservice.criproman.possuidor.Possuidor
import org.informservice.criproman.suspeito.Suspeito
import org.informservice.criproman.testemunha.Testemunha
import org.informservice.criproman.vitima.Vitima

class PecaProcesso implements Auditable {

    String numero
    Date dataAbertura
    String descricao
    byte[] anexo
    String uuid = UUID.randomUUID().toString()

    static mapping = {
        version false
    }

    static belongsTo = [inspector: Inspector]
    static hasMany = [declarantes        : Declarante,
                      denunciantes       : Denunciante,
                      ofendidos          : Ofendido,
                      suspeitos          : Suspeito,
                      meiosUtilizados    : MeioUtilizado,
                      bensSubtraidos     : BemSubtraido,
                      objectosApreendidos: ObjectoApreendido,
                      testeminhas        : Testemunha,
                      vitimas            : Vitima,
                      possuidores        : Possuidor,
                      objectosApreendidos: ObjectoApreendido,
                      acusados           : Acusado,
                      detidos            : Detido,
                      custodiados        : Custodiado,
                      possuidores        : Possuidor,
                      arguidos           : Arguido
    ]

    static constraints = {
        numero(nullable: false, unique: true)
        anexo(nullable: true)
        descricao(maxSize: 15000)
        dataAbertura(nullable: true, blank: true, validator: { dataEntrada, urc ->
            return ((dataEntrada <= new Date()))
        })
    }
}
