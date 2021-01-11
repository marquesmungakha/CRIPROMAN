package org.informservice.criproman.arguido

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ArguidoServiceSpec extends Specification {

    ArguidoService arguidoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Arguido(...).save(flush: true, failOnError: true)
        //new Arguido(...).save(flush: true, failOnError: true)
        //Arguido arguido = new Arguido(...).save(flush: true, failOnError: true)
        //new Arguido(...).save(flush: true, failOnError: true)
        //new Arguido(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //arguido.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        arguidoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Arguido> arguidoList = arguidoService.list(max: 2, offset: 2)

        then:
        arguidoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        arguidoService.count() == 5
    }

    void "test delete"() {
        Long arguidoId = setupData()

        expect:
        arguidoService.count() == 5

        when:
        arguidoService.delete(arguidoId)
        datastore.currentSession.flush()

        then:
        arguidoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Arguido arguido = new Arguido()
        arguidoService.save(arguido)

        then:
        arguido.id != null
    }
}
