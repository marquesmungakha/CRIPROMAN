package org.informservice.criproman.acarreacao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AcarreacaoServiceSpec extends Specification {

    AcarreacaoService acarreacaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Acarreacao(...).save(flush: true, failOnError: true)
        //new Acarreacao(...).save(flush: true, failOnError: true)
        //Acarreacao acarreacao = new Acarreacao(...).save(flush: true, failOnError: true)
        //new Acarreacao(...).save(flush: true, failOnError: true)
        //new Acarreacao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //acarreacao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        acarreacaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Acarreacao> acarreacaoList = acarreacaoService.list(max: 2, offset: 2)

        then:
        acarreacaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        acarreacaoService.count() == 5
    }

    void "test delete"() {
        Long acarreacaoId = setupData()

        expect:
        acarreacaoService.count() == 5

        when:
        acarreacaoService.delete(acarreacaoId)
        datastore.currentSession.flush()

        then:
        acarreacaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Acarreacao acarreacao = new Acarreacao()
        acarreacaoService.save(acarreacao)

        then:
        acarreacao.id != null
    }
}
