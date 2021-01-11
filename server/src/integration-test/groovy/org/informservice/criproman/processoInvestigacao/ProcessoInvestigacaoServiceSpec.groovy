package org.informservice.criproman.processoInvestigacao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ProcessoInvestigacaoServiceSpec extends Specification {

    ProcessoInvestigacaoService processoInvestigacaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ProcessoInvestigacao(...).save(flush: true, failOnError: true)
        //new ProcessoInvestigacao(...).save(flush: true, failOnError: true)
        //ProcessoInvestigacao processoInvestigacao = new ProcessoInvestigacao(...).save(flush: true, failOnError: true)
        //new ProcessoInvestigacao(...).save(flush: true, failOnError: true)
        //new ProcessoInvestigacao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //processoInvestigacao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        processoInvestigacaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ProcessoInvestigacao> processoInvestigacaoList = processoInvestigacaoService.list(max: 2, offset: 2)

        then:
        processoInvestigacaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        processoInvestigacaoService.count() == 5
    }

    void "test delete"() {
        Long processoInvestigacaoId = setupData()

        expect:
        processoInvestigacaoService.count() == 5

        when:
        processoInvestigacaoService.delete(processoInvestigacaoId)
        datastore.currentSession.flush()

        then:
        processoInvestigacaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ProcessoInvestigacao processoInvestigacao = new ProcessoInvestigacao()
        processoInvestigacaoService.save(processoInvestigacao)

        then:
        processoInvestigacao.id != null
    }
}
