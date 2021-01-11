package org.informservice.criproman.tipoDocumentoIdentificacao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TipoDocumentoIdentificacaoServiceSpec extends Specification {

    TipoDocumentoIdentificacaoService tipoDocumentoIdentificacaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TipoDocumentoIdentificacao(...).save(flush: true, failOnError: true)
        //new TipoDocumentoIdentificacao(...).save(flush: true, failOnError: true)
        //TipoDocumentoIdentificacao tipoDocumentoIdentificacao = new TipoDocumentoIdentificacao(...).save(flush: true, failOnError: true)
        //new TipoDocumentoIdentificacao(...).save(flush: true, failOnError: true)
        //new TipoDocumentoIdentificacao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tipoDocumentoIdentificacao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        tipoDocumentoIdentificacaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TipoDocumentoIdentificacao> tipoDocumentoIdentificacaoList = tipoDocumentoIdentificacaoService.list(max: 2, offset: 2)

        then:
        tipoDocumentoIdentificacaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tipoDocumentoIdentificacaoService.count() == 5
    }

    void "test delete"() {
        Long tipoDocumentoIdentificacaoId = setupData()

        expect:
        tipoDocumentoIdentificacaoService.count() == 5

        when:
        tipoDocumentoIdentificacaoService.delete(tipoDocumentoIdentificacaoId)
        datastore.currentSession.flush()

        then:
        tipoDocumentoIdentificacaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TipoDocumentoIdentificacao tipoDocumentoIdentificacao = new TipoDocumentoIdentificacao()
        tipoDocumentoIdentificacaoService.save(tipoDocumentoIdentificacao)

        then:
        tipoDocumentoIdentificacao.id != null
    }
}
