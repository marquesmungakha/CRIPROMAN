package org.informservice.criproman.quadro

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class QuadroServiceSpec extends Specification {

    QuadroService quadroService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Quadro(...).save(flush: true, failOnError: true)
        //new Quadro(...).save(flush: true, failOnError: true)
        //Quadro quadro = new Quadro(...).save(flush: true, failOnError: true)
        //new Quadro(...).save(flush: true, failOnError: true)
        //new Quadro(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //quadro.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        quadroService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Quadro> quadroList = quadroService.list(max: 2, offset: 2)

        then:
        quadroList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        quadroService.count() == 5
    }

    void "test delete"() {
        Long quadroId = setupData()

        expect:
        quadroService.count() == 5

        when:
        quadroService.delete(quadroId)
        datastore.currentSession.flush()

        then:
        quadroService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Quadro quadro = new Quadro()
        quadroService.save(quadro)

        then:
        quadro.id != null
    }
}
