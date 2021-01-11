package org.informservice.criproman.profissao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ProfissaoServiceSpec extends Specification {

    ProfissaoService profissaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Profissao(...).save(flush: true, failOnError: true)
        //new Profissao(...).save(flush: true, failOnError: true)
        //Profissao profissao = new Profissao(...).save(flush: true, failOnError: true)
        //new Profissao(...).save(flush: true, failOnError: true)
        //new Profissao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //profissao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        profissaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Profissao> profissaoList = profissaoService.list(max: 2, offset: 2)

        then:
        profissaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        profissaoService.count() == 5
    }

    void "test delete"() {
        Long profissaoId = setupData()

        expect:
        profissaoService.count() == 5

        when:
        profissaoService.delete(profissaoId)
        datastore.currentSession.flush()

        then:
        profissaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Profissao profissao = new Profissao()
        profissaoService.save(profissao)

        then:
        profissao.id != null
    }
}
