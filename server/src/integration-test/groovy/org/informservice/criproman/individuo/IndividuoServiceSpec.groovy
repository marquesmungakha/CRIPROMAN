package org.informservice.criproman.individuo

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class IndividuoServiceSpec extends Specification {

    IndividuoService individuoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Individuo(...).save(flush: true, failOnError: true)
        //new Individuo(...).save(flush: true, failOnError: true)
        //Individuo individuo = new Individuo(...).save(flush: true, failOnError: true)
        //new Individuo(...).save(flush: true, failOnError: true)
        //new Individuo(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //individuo.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        individuoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Individuo> individuoList = individuoService.list(max: 2, offset: 2)

        then:
        individuoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        individuoService.count() == 5
    }

    void "test delete"() {
        Long individuoId = setupData()

        expect:
        individuoService.count() == 5

        when:
        individuoService.delete(individuoId)
        datastore.currentSession.flush()

        then:
        individuoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Individuo individuo = new Individuo()
        individuoService.save(individuo)

        then:
        individuo.id != null
    }
}
