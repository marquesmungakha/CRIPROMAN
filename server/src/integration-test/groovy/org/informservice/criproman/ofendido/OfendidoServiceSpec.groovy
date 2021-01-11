package org.informservice.criproman.ofendido

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class OfendidoServiceSpec extends Specification {

    OfendidoService ofendidoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Ofendido(...).save(flush: true, failOnError: true)
        //new Ofendido(...).save(flush: true, failOnError: true)
        //Ofendido ofendido = new Ofendido(...).save(flush: true, failOnError: true)
        //new Ofendido(...).save(flush: true, failOnError: true)
        //new Ofendido(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //ofendido.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        ofendidoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Ofendido> ofendidoList = ofendidoService.list(max: 2, offset: 2)

        then:
        ofendidoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        ofendidoService.count() == 5
    }

    void "test delete"() {
        Long ofendidoId = setupData()

        expect:
        ofendidoService.count() == 5

        when:
        ofendidoService.delete(ofendidoId)
        datastore.currentSession.flush()

        then:
        ofendidoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Ofendido ofendido = new Ofendido()
        ofendidoService.save(ofendido)

        then:
        ofendido.id != null
    }
}
