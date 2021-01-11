package org.informservice.criproman.tipoPeca

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TipoPecaServiceSpec extends Specification {

    TipoPecaService tipoPecaService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TipoPeca(...).save(flush: true, failOnError: true)
        //new TipoPeca(...).save(flush: true, failOnError: true)
        //TipoPeca tipoPeca = new TipoPeca(...).save(flush: true, failOnError: true)
        //new TipoPeca(...).save(flush: true, failOnError: true)
        //new TipoPeca(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tipoPeca.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        tipoPecaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TipoPeca> tipoPecaList = tipoPecaService.list(max: 2, offset: 2)

        then:
        tipoPecaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tipoPecaService.count() == 5
    }

    void "test delete"() {
        Long tipoPecaId = setupData()

        expect:
        tipoPecaService.count() == 5

        when:
        tipoPecaService.delete(tipoPecaId)
        datastore.currentSession.flush()

        then:
        tipoPecaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TipoPeca tipoPeca = new TipoPeca()
        tipoPecaService.save(tipoPeca)

        then:
        tipoPeca.id != null
    }
}
