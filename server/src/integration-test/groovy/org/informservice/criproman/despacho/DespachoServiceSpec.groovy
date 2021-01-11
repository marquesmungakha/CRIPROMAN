package org.informservice.criproman.despacho

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DespachoServiceSpec extends Specification {

    DespachoService despachoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Despacho(...).save(flush: true, failOnError: true)
        //new Despacho(...).save(flush: true, failOnError: true)
        //Despacho despacho = new Despacho(...).save(flush: true, failOnError: true)
        //new Despacho(...).save(flush: true, failOnError: true)
        //new Despacho(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //despacho.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        despachoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Despacho> despachoList = despachoService.list(max: 2, offset: 2)

        then:
        despachoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        despachoService.count() == 5
    }

    void "test delete"() {
        Long despachoId = setupData()

        expect:
        despachoService.count() == 5

        when:
        despachoService.delete(despachoId)
        datastore.currentSession.flush()

        then:
        despachoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Despacho despacho = new Despacho()
        despachoService.save(despacho)

        then:
        despacho.id != null
    }
}
