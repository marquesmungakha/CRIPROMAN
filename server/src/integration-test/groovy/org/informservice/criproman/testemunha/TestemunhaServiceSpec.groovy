package org.informservice.criproman.testemunha

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TestemunhaServiceSpec extends Specification {

    TestemunhaService testemunhaService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Testemunha(...).save(flush: true, failOnError: true)
        //new Testemunha(...).save(flush: true, failOnError: true)
        //Testemunha testemunha = new Testemunha(...).save(flush: true, failOnError: true)
        //new Testemunha(...).save(flush: true, failOnError: true)
        //new Testemunha(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //testemunha.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        testemunhaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Testemunha> testemunhaList = testemunhaService.list(max: 2, offset: 2)

        then:
        testemunhaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        testemunhaService.count() == 5
    }

    void "test delete"() {
        Long testemunhaId = setupData()

        expect:
        testemunhaService.count() == 5

        when:
        testemunhaService.delete(testemunhaId)
        datastore.currentSession.flush()

        then:
        testemunhaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Testemunha testemunha = new Testemunha()
        testemunhaService.save(testemunha)

        then:
        testemunha.id != null
    }
}
