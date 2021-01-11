package org.informservice.criproman.declarante

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DeclaranteServiceSpec extends Specification {

    DeclaranteService declaranteService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Declarante(...).save(flush: true, failOnError: true)
        //new Declarante(...).save(flush: true, failOnError: true)
        //Declarante declarante = new Declarante(...).save(flush: true, failOnError: true)
        //new Declarante(...).save(flush: true, failOnError: true)
        //new Declarante(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //declarante.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        declaranteService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Declarante> declaranteList = declaranteService.list(max: 2, offset: 2)

        then:
        declaranteList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        declaranteService.count() == 5
    }

    void "test delete"() {
        Long declaranteId = setupData()

        expect:
        declaranteService.count() == 5

        when:
        declaranteService.delete(declaranteId)
        datastore.currentSession.flush()

        then:
        declaranteService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Declarante declarante = new Declarante()
        declaranteService.save(declarante)

        then:
        declarante.id != null
    }
}
