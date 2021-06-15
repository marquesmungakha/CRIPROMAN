package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaPossuidorServiceSpec extends Specification {

    AutoEntradaPossuidorService autoEntradaPossuidorService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaPossuidor(...).save(flush: true, failOnError: true)
        //new AutoEntradaPossuidor(...).save(flush: true, failOnError: true)
        //AutoEntradaPossuidor autoEntradaPossuidor = new AutoEntradaPossuidor(...).save(flush: true, failOnError: true)
        //new AutoEntradaPossuidor(...).save(flush: true, failOnError: true)
        //new AutoEntradaPossuidor(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaPossuidor.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaPossuidorService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaPossuidor> autoEntradaPossuidorList = autoEntradaPossuidorService.list(max: 2, offset: 2)

        then:
        autoEntradaPossuidorList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaPossuidorService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaPossuidorId = setupData()

        expect:
        autoEntradaPossuidorService.count() == 5

        when:
        autoEntradaPossuidorService.delete(autoEntradaPossuidorId)
        datastore.currentSession.flush()

        then:
        autoEntradaPossuidorService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaPossuidor autoEntradaPossuidor = new AutoEntradaPossuidor()
        autoEntradaPossuidorService.save(autoEntradaPossuidor)

        then:
        autoEntradaPossuidor.id != null
    }
}
