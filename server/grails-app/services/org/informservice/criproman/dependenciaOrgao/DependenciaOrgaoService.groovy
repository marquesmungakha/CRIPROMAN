package org.informservice.criproman.dependenciaOrgao

import grails.gorm.services.Service

@Service(DependenciaOrgao)
interface DependenciaOrgaoService {

    DependenciaOrgao get(Serializable id)

    List<DependenciaOrgao> list(Map args)

    Long count()

    DependenciaOrgao delete(Serializable id)

    DependenciaOrgao save(DependenciaOrgao dependenciaOrgao)

}
