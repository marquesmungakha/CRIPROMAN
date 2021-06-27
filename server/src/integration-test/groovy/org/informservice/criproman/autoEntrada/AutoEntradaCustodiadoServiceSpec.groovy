package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaCustodiadoServiceSpec extends Specification {

    AutoEntradaCustodiadoService autoEntradaCustodiadoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaCustodiado(...).save(flush: true, failOnError: true)
        //new AutoEntradaCustodiado(...).save(flush: true, failOnError: true)
        //AutoEntradaCustodiado autoEntradaCustodiado = new AutoEntradaCustodiado(...).save(flush: true, failOnError: true)
        //new AutoEntradaCustodiado(...).save(flush: true, failOnError: true)
        //new AutoEntradaCustodiado(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaCustodiado.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaCustodiadoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaCustodiado> autoEntradaCustodiadoList = autoEntradaCustodiadoService.list(max: 2, offset: 2)

        then:
        autoEntradaCustodiadoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaCustodiadoService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaCustodiadoId = setupData()

        expect:
        autoEntradaCustodiadoService.count() == 5

        when:
        autoEntradaCustodiadoService.delete(autoEntradaCustodiadoId)
        datastore.currentSession.flush()

        then:
        autoEntradaCustodiadoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaCustodiado autoEntradaCustodiado = new AutoEntradaCustodiado()
        autoEntradaCustodiadoService.save(autoEntradaCustodiado)

        then:
        autoEntradaCustodiado.id != null
    }
}
