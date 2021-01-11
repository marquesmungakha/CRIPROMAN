package org.informservice.criproman.motivoDetencao

import grails.gorm.services.Service

@Service(MotivoDetencao)
interface MotivoDetencaoService {

    MotivoDetencao get(Serializable id)

    List<MotivoDetencao> list(Map args)

    Long count()

    MotivoDetencao delete(Serializable id)

    MotivoDetencao save(MotivoDetencao motivoDetencao)

}
