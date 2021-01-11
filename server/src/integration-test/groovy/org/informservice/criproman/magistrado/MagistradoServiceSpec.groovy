package org.informservice.criproman.magistrado

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class MagistradoServiceSpec extends Specification {

    MagistradoService magistradoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Magistrado(...).save(flush: true, failOnError: true)
        //new Magistrado(...).save(flush: true, failOnError: true)
        //Magistrado magistrado = new Magistrado(...).save(flush: true, failOnError: true)
        //new Magistrado(...).save(flush: true, failOnError: true)
        //new Magistrado(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //magistrado.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        magistradoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Magistrado> magistradoList = magistradoService.list(max: 2, offset: 2)

        then:
        magistradoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        magistradoService.count() == 5
    }

    void "test delete"() {
        Long magistradoId = setupData()

        expect:
        magistradoService.count() == 5

        when:
        magistradoService.delete(magistradoId)
        datastore.currentSession.flush()

        then:
        magistradoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Magistrado magistrado = new Magistrado()
        magistradoService.save(magistrado)

        then:
        magistrado.id != null
    }
}
