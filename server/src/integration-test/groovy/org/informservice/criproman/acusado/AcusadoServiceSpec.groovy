package org.informservice.criproman.acusado

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AcusadoServiceSpec extends Specification {

    AcusadoService acusadoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Acusado(...).save(flush: true, failOnError: true)
        //new Acusado(...).save(flush: true, failOnError: true)
        //Acusado acusado = new Acusado(...).save(flush: true, failOnError: true)
        //new Acusado(...).save(flush: true, failOnError: true)
        //new Acusado(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //acusado.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        acusadoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Acusado> acusadoList = acusadoService.list(max: 2, offset: 2)

        then:
        acusadoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        acusadoService.count() == 5
    }

    void "test delete"() {
        Long acusadoId = setupData()

        expect:
        acusadoService.count() == 5

        when:
        acusadoService.delete(acusadoId)
        datastore.currentSession.flush()

        then:
        acusadoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Acusado acusado = new Acusado()
        acusadoService.save(acusado)

        then:
        acusado.id != null
    }
}
