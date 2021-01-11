package org.informservice.criproman.suspeito

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class SuspeitoServiceSpec extends Specification {

    SuspeitoService suspeitoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Suspeito(...).save(flush: true, failOnError: true)
        //new Suspeito(...).save(flush: true, failOnError: true)
        //Suspeito suspeito = new Suspeito(...).save(flush: true, failOnError: true)
        //new Suspeito(...).save(flush: true, failOnError: true)
        //new Suspeito(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //suspeito.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        suspeitoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Suspeito> suspeitoList = suspeitoService.list(max: 2, offset: 2)

        then:
        suspeitoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        suspeitoService.count() == 5
    }

    void "test delete"() {
        Long suspeitoId = setupData()

        expect:
        suspeitoService.count() == 5

        when:
        suspeitoService.delete(suspeitoId)
        datastore.currentSession.flush()

        then:
        suspeitoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Suspeito suspeito = new Suspeito()
        suspeitoService.save(suspeito)

        then:
        suspeito.id != null
    }
}
