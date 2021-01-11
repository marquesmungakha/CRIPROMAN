package org.informservice.criproman.declaracao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DeclaracaoServiceSpec extends Specification {

    DeclaracaoService declaracaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Declaracao(...).save(flush: true, failOnError: true)
        //new Declaracao(...).save(flush: true, failOnError: true)
        //Declaracao declaracao = new Declaracao(...).save(flush: true, failOnError: true)
        //new Declaracao(...).save(flush: true, failOnError: true)
        //new Declaracao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //declaracao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        declaracaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Declaracao> declaracaoList = declaracaoService.list(max: 2, offset: 2)

        then:
        declaracaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        declaracaoService.count() == 5
    }

    void "test delete"() {
        Long declaracaoId = setupData()

        expect:
        declaracaoService.count() == 5

        when:
        declaracaoService.delete(declaracaoId)
        datastore.currentSession.flush()

        then:
        declaracaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Declaracao declaracao = new Declaracao()
        declaracaoService.save(declaracao)

        then:
        declaracao.id != null
    }
}
