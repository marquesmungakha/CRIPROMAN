package org.informservice.criproman.distibuicaoAdministrativa

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DistritoUrbanoServiceSpec extends Specification {

    DistritoUrbanoService distritoUrbanoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DistritoUrbano(...).save(flush: true, failOnError: true)
        //new DistritoUrbano(...).save(flush: true, failOnError: true)
        //DistritoUrbano distritoUrbano = new DistritoUrbano(...).save(flush: true, failOnError: true)
        //new DistritoUrbano(...).save(flush: true, failOnError: true)
        //new DistritoUrbano(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //distritoUrbano.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        distritoUrbanoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DistritoUrbano> distritoUrbanoList = distritoUrbanoService.list(max: 2, offset: 2)

        then:
        distritoUrbanoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        distritoUrbanoService.count() == 5
    }

    void "test delete"() {
        Long distritoUrbanoId = setupData()

        expect:
        distritoUrbanoService.count() == 5

        when:
        distritoUrbanoService.delete(distritoUrbanoId)
        datastore.currentSession.flush()

        then:
        distritoUrbanoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DistritoUrbano distritoUrbano = new DistritoUrbano()
        distritoUrbanoService.save(distritoUrbano)

        then:
        distritoUrbano.id != null
    }
}
