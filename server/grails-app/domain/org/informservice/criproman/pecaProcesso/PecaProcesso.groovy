package org.informservice.criproman.pecaProcesso

import grails.plugin.springsecurity.annotation.Secured
import grails.plugins.orm.auditable.Auditable
import grails.rest.Resource
import org.informservice.criproman.bemSubtraido.BemSubtraido
import org.informservice.criproman.inspector.Inspector
import org.informservice.criproman.meioUtilizado.MeioUtilizado
import org.informservice.criproman.objectosApreendidos.ObjectoApreendido
import org.informservice.criproman.objectosDetido.ObjectoDetido

@Secured('ROLE_ADMIN')
@Resource(uri='/api/pecaProcesso')
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
    static hasMany = [declarantes        : PecaProcessoDeclarante,
                      denunciantes       : PecaProcessoDenunciante,
                      ofendidos          : PecaProcessoOfendido,
                      suspeitos          : PecaProcessoSuspeito,
                      meiosUtilizados    : MeioUtilizado,
                      bensSubtraidos     : BemSubtraido,
                      objectosApreendidos: ObjectoApreendido,
                      bens               : ObjectoDetido,
                      testeminhas        : PecaProcessoTestemunha,
                      vitimas            : PecaProcessoVitima,
                      acusados           : PecaProcessoAcusado,
                      detidos            : PecaProcessoDetido,
                      custodiados        : PecaProcessoCustodiado,
                      possuidores        : PecaProcessoPossuidor,
                      arguidos           : PecaProcessoArguido
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
