package org.informservice.criproman.processoInvestigacao

import grails.gorm.services.Service

@Service(ProcessoInvestigacao)
interface ProcessoInvestigacaoService {

    ProcessoInvestigacao get(Serializable id)

    List<ProcessoInvestigacao> list(Map args)

    Long count()

    ProcessoInvestigacao delete(Serializable id)

    ProcessoInvestigacao save(ProcessoInvestigacao processoInvestigacao)

}
