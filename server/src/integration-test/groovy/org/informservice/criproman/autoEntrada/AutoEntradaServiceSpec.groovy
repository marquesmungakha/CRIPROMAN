package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaServiceSpec extends Specification {

    AutoEntradaService autoEntradaService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntrada(...).save(flush: true, failOnError: true)
        //new AutoEntrada(...).save(flush: true, failOnError: true)
        //AutoEntrada autoEntrada = new AutoEntrada(...).save(flush: true, failOnError: true)
        //new AutoEntrada(...).save(flush: true, failOnError: true)
        //new AutoEntrada(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntrada.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntrada> autoEntradaList = autoEntradaService.list(max: 2, offset: 2)

        then:
        autoEntradaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaId = setupData()

        expect:
        autoEntradaService.count() == 5

        when:
        autoEntradaService.delete(autoEntradaId)
        datastore.currentSession.flush()

        then:
        autoEntradaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntrada autoEntrada = new AutoEntrada()
        autoEntradaService.save(autoEntrada)

        then:
        autoEntrada.id != null
    }
}
