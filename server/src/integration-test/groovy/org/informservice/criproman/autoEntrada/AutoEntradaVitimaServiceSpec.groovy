package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaVitimaServiceSpec extends Specification {

    AutoEntradaVitimaService autoEntradaVitimaService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaVitima(...).save(flush: true, failOnError: true)
        //new AutoEntradaVitima(...).save(flush: true, failOnError: true)
        //AutoEntradaVitima autoEntradaVitima = new AutoEntradaVitima(...).save(flush: true, failOnError: true)
        //new AutoEntradaVitima(...).save(flush: true, failOnError: true)
        //new AutoEntradaVitima(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaVitima.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaVitimaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaVitima> autoEntradaVitimaList = autoEntradaVitimaService.list(max: 2, offset: 2)

        then:
        autoEntradaVitimaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaVitimaService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaVitimaId = setupData()

        expect:
        autoEntradaVitimaService.count() == 5

        when:
        autoEntradaVitimaService.delete(autoEntradaVitimaId)
        datastore.currentSession.flush()

        then:
        autoEntradaVitimaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaVitima autoEntradaVitima = new AutoEntradaVitima()
        autoEntradaVitimaService.save(autoEntradaVitima)

        then:
        autoEntradaVitima.id != null
    }
}
