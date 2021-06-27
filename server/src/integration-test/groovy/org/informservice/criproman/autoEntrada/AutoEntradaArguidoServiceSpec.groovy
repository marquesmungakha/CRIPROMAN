package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaArguidoServiceSpec extends Specification {

    AutoEntradaArguidoService autoEntradaArguidoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaArguido(...).save(flush: true, failOnError: true)
        //new AutoEntradaArguido(...).save(flush: true, failOnError: true)
        //AutoEntradaArguido autoEntradaArguido = new AutoEntradaArguido(...).save(flush: true, failOnError: true)
        //new AutoEntradaArguido(...).save(flush: true, failOnError: true)
        //new AutoEntradaArguido(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaArguido.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaArguidoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaArguido> autoEntradaArguidoList = autoEntradaArguidoService.list(max: 2, offset: 2)

        then:
        autoEntradaArguidoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaArguidoService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaArguidoId = setupData()

        expect:
        autoEntradaArguidoService.count() == 5

        when:
        autoEntradaArguidoService.delete(autoEntradaArguidoId)
        datastore.currentSession.flush()

        then:
        autoEntradaArguidoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaArguido autoEntradaArguido = new AutoEntradaArguido()
        autoEntradaArguidoService.save(autoEntradaArguido)

        then:
        autoEntradaArguido.id != null
    }
}
