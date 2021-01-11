package org.informservice.criproman.historicoAcademico

import grails.gorm.services.Service

@Service(HistoricoAcademico)
interface HistoricoAcademicoService {

    HistoricoAcademico get(Serializable id)

    List<HistoricoAcademico> list(Map args)

    Long count()

    HistoricoAcademico delete(Serializable id)

    HistoricoAcademico save(HistoricoAcademico historicoAcademico)

}
