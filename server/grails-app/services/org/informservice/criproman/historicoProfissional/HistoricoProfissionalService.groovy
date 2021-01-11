package org.informservice.criproman.historicoProfissional

import grails.gorm.services.Service

@Service(HistoricoProfissional)
interface HistoricoProfissionalService {

    HistoricoProfissional get(Serializable id)

    List<HistoricoProfissional> list(Map args)

    Long count()

    HistoricoProfissional delete(Serializable id)

    HistoricoProfissional save(HistoricoProfissional historicoProfissional)

}
