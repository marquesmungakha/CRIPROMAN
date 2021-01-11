package org.informservice.criproman.tipoOrgao

import grails.gorm.services.Service

@Service(TipoOrgao)
interface TipoOrgaoService {

    TipoOrgao get(Serializable id)

    List<TipoOrgao> list(Map args)

    Long count()

    TipoOrgao delete(Serializable id)

    TipoOrgao save(TipoOrgao tipoOrgao)

}
