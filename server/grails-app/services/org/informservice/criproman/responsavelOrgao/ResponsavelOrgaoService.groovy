package org.informservice.criproman.responsavelOrgao

import grails.gorm.services.Service

@Service(ResponsavelOrgao)
interface ResponsavelOrgaoService {

    ResponsavelOrgao get(Serializable id)

    List<ResponsavelOrgao> list(Map args)

    Long count()

    ResponsavelOrgao delete(Serializable id)

    ResponsavelOrgao save(ResponsavelOrgao responsavelOrgao)

}
