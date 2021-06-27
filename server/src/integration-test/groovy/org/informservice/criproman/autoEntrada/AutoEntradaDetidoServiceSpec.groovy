package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaDetidoServiceSpec extends Specification {

    AutoEntradaDetidoService autoEntradaDetidoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaDetido(...).save(flush: true, failOnError: true)
        //new AutoEntradaDetido(...).save(flush: true, failOnError: true)
        //AutoEntradaDetido autoEntradaDetido = new AutoEntradaDetido(...).save(flush: true, failOnError: true)
        //new AutoEntradaDetido(...).save(flush: true, failOnError: true)
        //new AutoEntradaDetido(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaDetido.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaDetidoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaDetido> autoEntradaDetidoList = autoEntradaDetidoService.list(max: 2, offset: 2)

        then:
        autoEntradaDetidoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaDetidoService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaDetidoId = setupData()

        expect:
        autoEntradaDetidoService.count() == 5

        when:
        autoEntradaDetidoService.delete(autoEntradaDetidoId)
        datastore.currentSession.flush()

        then:
        autoEntradaDetidoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaDetido autoEntradaDetido = new AutoEntradaDetido()
        autoEntradaDetidoService.save(autoEntradaDetido)

        then:
        autoEntradaDetido.id != null
    }
}
