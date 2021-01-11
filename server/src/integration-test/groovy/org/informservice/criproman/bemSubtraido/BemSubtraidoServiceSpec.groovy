package org.informservice.criproman.bemSubtraido

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class BemSubtraidoServiceSpec extends Specification {

    BemSubtraidoService bemSubtraidoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new BemSubtraido(...).save(flush: true, failOnError: true)
        //new BemSubtraido(...).save(flush: true, failOnError: true)
        //BemSubtraido bemSubtraido = new BemSubtraido(...).save(flush: true, failOnError: true)
        //new BemSubtraido(...).save(flush: true, failOnError: true)
        //new BemSubtraido(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //bemSubtraido.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        bemSubtraidoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<BemSubtraido> bemSubtraidoList = bemSubtraidoService.list(max: 2, offset: 2)

        then:
        bemSubtraidoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        bemSubtraidoService.count() == 5
    }

    void "test delete"() {
        Long bemSubtraidoId = setupData()

        expect:
        bemSubtraidoService.count() == 5

        when:
        bemSubtraidoService.delete(bemSubtraidoId)
        datastore.currentSession.flush()

        then:
        bemSubtraidoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        BemSubtraido bemSubtraido = new BemSubtraido()
        bemSubtraidoService.save(bemSubtraido)

        then:
        bemSubtraido.id != null
    }
}
