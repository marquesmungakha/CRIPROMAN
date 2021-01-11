package org.informservice.criproman.corpoDelito

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class CorpoDelitoServiceSpec extends Specification {

    CorpoDelitoService corpoDelitoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new CorpoDelito(...).save(flush: true, failOnError: true)
        //new CorpoDelito(...).save(flush: true, failOnError: true)
        //CorpoDelito corpoDelito = new CorpoDelito(...).save(flush: true, failOnError: true)
        //new CorpoDelito(...).save(flush: true, failOnError: true)
        //new CorpoDelito(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //corpoDelito.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        corpoDelitoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<CorpoDelito> corpoDelitoList = corpoDelitoService.list(max: 2, offset: 2)

        then:
        corpoDelitoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        corpoDelitoService.count() == 5
    }

    void "test delete"() {
        Long corpoDelitoId = setupData()

        expect:
        corpoDelitoService.count() == 5

        when:
        corpoDelitoService.delete(corpoDelitoId)
        datastore.currentSession.flush()

        then:
        corpoDelitoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        CorpoDelito corpoDelito = new CorpoDelito()
        corpoDelitoService.save(corpoDelito)

        then:
        corpoDelito.id != null
    }
}
