package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaAcusadoServiceSpec extends Specification {

    AutoEntradaAcusadoService autoEntradaAcusadoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaAcusado(...).save(flush: true, failOnError: true)
        //new AutoEntradaAcusado(...).save(flush: true, failOnError: true)
        //AutoEntradaAcusado autoEntradaAcusado = new AutoEntradaAcusado(...).save(flush: true, failOnError: true)
        //new AutoEntradaAcusado(...).save(flush: true, failOnError: true)
        //new AutoEntradaAcusado(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaAcusado.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaAcusadoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaAcusado> autoEntradaAcusadoList = autoEntradaAcusadoService.list(max: 2, offset: 2)

        then:
        autoEntradaAcusadoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaAcusadoService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaAcusadoId = setupData()

        expect:
        autoEntradaAcusadoService.count() == 5

        when:
        autoEntradaAcusadoService.delete(autoEntradaAcusadoId)
        datastore.currentSession.flush()

        then:
        autoEntradaAcusadoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaAcusado autoEntradaAcusado = new AutoEntradaAcusado()
        autoEntradaAcusadoService.save(autoEntradaAcusado)

        then:
        autoEntradaAcusado.id != null
    }
}
