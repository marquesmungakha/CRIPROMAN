package org.informservice.criproman.seguranca

import grails.gorm.services.Service

@Service(Auditoria)
interface AuditoriaService {

    Auditoria get(Serializable id)

    List<Auditoria> list(Map args)

    Long count()

    Auditoria delete(Serializable id)

    Auditoria save(Auditoria auditoria)

}
