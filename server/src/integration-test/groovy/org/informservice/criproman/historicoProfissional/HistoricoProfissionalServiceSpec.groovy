package org.informservice.criproman.historicoProfissional

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class HistoricoProfissionalServiceSpec extends Specification {

    HistoricoProfissionalService historicoProfissionalService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new HistoricoProfissional(...).save(flush: true, failOnError: true)
        //new HistoricoProfissional(...).save(flush: true, failOnError: true)
        //HistoricoProfissional historicoProfissional = new HistoricoProfissional(...).save(flush: true, failOnError: true)
        //new HistoricoProfissional(...).save(flush: true, failOnError: true)
        //new HistoricoProfissional(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //historicoProfissional.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        historicoProfissionalService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<HistoricoProfissional> historicoProfissionalList = historicoProfissionalService.list(max: 2, offset: 2)

        then:
        historicoProfissionalList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        historicoProfissionalService.count() == 5
    }

    void "test delete"() {
        Long historicoProfissionalId = setupData()

        expect:
        historicoProfissionalService.count() == 5

        when:
        historicoProfissionalService.delete(historicoProfissionalId)
        datastore.currentSession.flush()

        then:
        historicoProfissionalService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        HistoricoProfissional historicoProfissional = new HistoricoProfissional()
        historicoProfissionalService.save(historicoProfissional)

        then:
        historicoProfissional.id != null
    }
}
