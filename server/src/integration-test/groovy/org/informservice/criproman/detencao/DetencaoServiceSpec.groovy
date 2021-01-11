package org.informservice.criproman.detencao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DetencaoServiceSpec extends Specification {

    DetencaoService detencaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Detencao(...).save(flush: true, failOnError: true)
        //new Detencao(...).save(flush: true, failOnError: true)
        //Detencao detencao = new Detencao(...).save(flush: true, failOnError: true)
        //new Detencao(...).save(flush: true, failOnError: true)
        //new Detencao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //detencao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        detencaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Detencao> detencaoList = detencaoService.list(max: 2, offset: 2)

        then:
        detencaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        detencaoService.count() == 5
    }

    void "test delete"() {
        Long detencaoId = setupData()

        expect:
        detencaoService.count() == 5

        when:
        detencaoService.delete(detencaoId)
        datastore.currentSession.flush()

        then:
        detencaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Detencao detencao = new Detencao()
        detencaoService.save(detencao)

        then:
        detencao.id != null
    }
}