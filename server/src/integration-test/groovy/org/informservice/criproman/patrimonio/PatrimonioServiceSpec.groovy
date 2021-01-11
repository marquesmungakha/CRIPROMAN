package org.informservice.criproman.patrimonio

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PatrimonioServiceSpec extends Specification {

    PatrimonioService patrimonioService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Patrimonio(...).save(flush: true, failOnError: true)
        //new Patrimonio(...).save(flush: true, failOnError: true)
        //Patrimonio patrimonio = new Patrimonio(...).save(flush: true, failOnError: true)
        //new Patrimonio(...).save(flush: true, failOnError: true)
        //new Patrimonio(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //patrimonio.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        patrimonioService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Patrimonio> patrimonioList = patrimonioService.list(max: 2, offset: 2)

        then:
        patrimonioList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        patrimonioService.count() == 5
    }

    void "test delete"() {
        Long patrimonioId = setupData()

        expect:
        patrimonioService.count() == 5

        when:
        patrimonioService.delete(patrimonioId)
        datastore.currentSession.flush()

        then:
        patrimonioService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Patrimonio patrimonio = new Patrimonio()
        patrimonioService.save(patrimonio)

        then:
        patrimonio.id != null
    }
}
