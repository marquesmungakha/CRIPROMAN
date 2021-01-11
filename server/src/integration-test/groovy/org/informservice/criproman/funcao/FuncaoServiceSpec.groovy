package org.informservice.criproman.funcao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class FuncaoServiceSpec extends Specification {

    FuncaoService funcaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Funcao(...).save(flush: true, failOnError: true)
        //new Funcao(...).save(flush: true, failOnError: true)
        //Funcao funcao = new Funcao(...).save(flush: true, failOnError: true)
        //new Funcao(...).save(flush: true, failOnError: true)
        //new Funcao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //funcao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        funcaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Funcao> funcaoList = funcaoService.list(max: 2, offset: 2)

        then:
        funcaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        funcaoService.count() == 5
    }

    void "test delete"() {
        Long funcaoId = setupData()

        expect:
        funcaoService.count() == 5

        when:
        funcaoService.delete(funcaoId)
        datastore.currentSession.flush()

        then:
        funcaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Funcao funcao = new Funcao()
        funcaoService.save(funcao)

        then:
        funcao.id != null
    }
}
