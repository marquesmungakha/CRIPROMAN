package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaTestemunhaServiceSpec extends Specification {

    AutoEntradaTestemunhaService autoEntradaTestemunhaService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaTestemunha(...).save(flush: true, failOnError: true)
        //new AutoEntradaTestemunha(...).save(flush: true, failOnError: true)
        //AutoEntradaTestemunha autoEntradaTestemunha = new AutoEntradaTestemunha(...).save(flush: true, failOnError: true)
        //new AutoEntradaTestemunha(...).save(flush: true, failOnError: true)
        //new AutoEntradaTestemunha(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaTestemunha.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaTestemunhaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaTestemunha> autoEntradaTestemunhaList = autoEntradaTestemunhaService.list(max: 2, offset: 2)

        then:
        autoEntradaTestemunhaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaTestemunhaService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaTestemunhaId = setupData()

        expect:
        autoEntradaTestemunhaService.count() == 5

        when:
        autoEntradaTestemunhaService.delete(autoEntradaTestemunhaId)
        datastore.currentSession.flush()

        then:
        autoEntradaTestemunhaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaTestemunha autoEntradaTestemunha = new AutoEntradaTestemunha()
        autoEntradaTestemunhaService.save(autoEntradaTestemunha)

        then:
        autoEntradaTestemunha.id != null
    }
}
