package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.gorm.services.Service

@Service(ProcessoInstrucaoPreparatoria)
interface ProcessoInstrucaoPreparatoriaService {

    ProcessoInstrucaoPreparatoria get(Serializable id)

    List<ProcessoInstrucaoPreparatoria> list(Map args)

    Long count()

    ProcessoInstrucaoPreparatoria delete(Serializable id)

    ProcessoInstrucaoPreparatoria save(ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria)

}
