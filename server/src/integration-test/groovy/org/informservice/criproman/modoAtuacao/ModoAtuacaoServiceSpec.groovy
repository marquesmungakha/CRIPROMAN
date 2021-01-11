package org.informservice.criproman.modoAtuacao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ModoAtuacaoServiceSpec extends Specification {

    ModoAtuacaoService modoAtuacaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ModoAtuacao(...).save(flush: true, failOnError: true)
        //new ModoAtuacao(...).save(flush: true, failOnError: true)
        //ModoAtuacao modoAtuacao = new ModoAtuacao(...).save(flush: true, failOnError: true)
        //new ModoAtuacao(...).save(flush: true, failOnError: true)
        //new ModoAtuacao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //modoAtuacao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        modoAtuacaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ModoAtuacao> modoAtuacaoList = modoAtuacaoService.list(max: 2, offset: 2)

        then:
        modoAtuacaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        modoAtuacaoService.count() == 5
    }

    void "test delete"() {
        Long modoAtuacaoId = setupData()

        expect:
        modoAtuacaoService.count() == 5

        when:
        modoAtuacaoService.delete(modoAtuacaoId)
        datastore.currentSession.flush()

        then:
        modoAtuacaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ModoAtuacao modoAtuacao = new ModoAtuacao()
        modoAtuacaoService.save(modoAtuacao)

        then:
        modoAtuacao.id != null
    }
}
