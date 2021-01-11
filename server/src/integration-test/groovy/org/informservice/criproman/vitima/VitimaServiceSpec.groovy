package org.informservice.criproman.vitima

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class VitimaServiceSpec extends Specification {

    VitimaService vitimaService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Vitima(...).save(flush: true, failOnError: true)
        //new Vitima(...).save(flush: true, failOnError: true)
        //Vitima vitima = new Vitima(...).save(flush: true, failOnError: true)
        //new Vitima(...).save(flush: true, failOnError: true)
        //new Vitima(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //vitima.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        vitimaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Vitima> vitimaList = vitimaService.list(max: 2, offset: 2)

        then:
        vitimaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        vitimaService.count() == 5
    }

    void "test delete"() {
        Long vitimaId = setupData()

        expect:
        vitimaService.count() == 5

        when:
        vitimaService.delete(vitimaId)
        datastore.currentSession.flush()

        then:
        vitimaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Vitima vitima = new Vitima()
        vitimaService.save(vitima)

        then:
        vitima.id != null
    }
}
