package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaOfendidoServiceSpec extends Specification {

    AutoEntradaOfendidoService autoEntradaOfendidoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaOfendido(...).save(flush: true, failOnError: true)
        //new AutoEntradaOfendido(...).save(flush: true, failOnError: true)
        //AutoEntradaOfendido autoEntradaOfendido = new AutoEntradaOfendido(...).save(flush: true, failOnError: true)
        //new AutoEntradaOfendido(...).save(flush: true, failOnError: true)
        //new AutoEntradaOfendido(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaOfendido.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaOfendidoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaOfendido> autoEntradaOfendidoList = autoEntradaOfendidoService.list(max: 2, offset: 2)

        then:
        autoEntradaOfendidoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaOfendidoService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaOfendidoId = setupData()

        expect:
        autoEntradaOfendidoService.count() == 5

        when:
        autoEntradaOfendidoService.delete(autoEntradaOfendidoId)
        datastore.currentSession.flush()

        then:
        autoEntradaOfendidoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaOfendido autoEntradaOfendido = new AutoEntradaOfendido()
        autoEntradaOfendidoService.save(autoEntradaOfendido)

        then:
        autoEntradaOfendido.id != null
    }
}
