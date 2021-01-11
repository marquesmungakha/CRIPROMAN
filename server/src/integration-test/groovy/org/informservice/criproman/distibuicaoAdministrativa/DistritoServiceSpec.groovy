package org.informservice.criproman.distibuicaoAdministrativa

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DistritoServiceSpec extends Specification {

    DistritoService distritoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Distrito(...).save(flush: true, failOnError: true)
        //new Distrito(...).save(flush: true, failOnError: true)
        //Distrito distrito = new Distrito(...).save(flush: true, failOnError: true)
        //new Distrito(...).save(flush: true, failOnError: true)
        //new Distrito(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //distrito.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        distritoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Distrito> distritoList = distritoService.list(max: 2, offset: 2)

        then:
        distritoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        distritoService.count() == 5
    }

    void "test delete"() {
        Long distritoId = setupData()

        expect:
        distritoService.count() == 5

        when:
        distritoService.delete(distritoId)
        datastore.currentSession.flush()

        then:
        distritoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Distrito distrito = new Distrito()
        distritoService.save(distrito)

        then:
        distrito.id != null
    }
}
