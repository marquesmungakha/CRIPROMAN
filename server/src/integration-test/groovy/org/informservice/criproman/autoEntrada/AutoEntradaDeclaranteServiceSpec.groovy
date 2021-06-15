package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaDeclaranteServiceSpec extends Specification {

    AutoEntradaDeclaranteService autoEntradaDeclaranteService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaDeclarante(...).save(flush: true, failOnError: true)
        //new AutoEntradaDeclarante(...).save(flush: true, failOnError: true)
        //AutoEntradaDeclarante autoEntradaDeclarante = new AutoEntradaDeclarante(...).save(flush: true, failOnError: true)
        //new AutoEntradaDeclarante(...).save(flush: true, failOnError: true)
        //new AutoEntradaDeclarante(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaDeclarante.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaDeclaranteService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaDeclarante> autoEntradaDeclaranteList = autoEntradaDeclaranteService.list(max: 2, offset: 2)

        then:
        autoEntradaDeclaranteList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaDeclaranteService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaDeclaranteId = setupData()

        expect:
        autoEntradaDeclaranteService.count() == 5

        when:
        autoEntradaDeclaranteService.delete(autoEntradaDeclaranteId)
        datastore.currentSession.flush()

        then:
        autoEntradaDeclaranteService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaDeclarante autoEntradaDeclarante = new AutoEntradaDeclarante()
        autoEntradaDeclaranteService.save(autoEntradaDeclarante)

        then:
        autoEntradaDeclarante.id != null
    }
}
