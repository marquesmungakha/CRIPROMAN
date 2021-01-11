package org.informservice.criproman.tipoParecer

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TipoParecerServiceSpec extends Specification {

    TipoParecerService tipoParecerService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TipoParecer(...).save(flush: true, failOnError: true)
        //new TipoParecer(...).save(flush: true, failOnError: true)
        //TipoParecer tipoParecer = new TipoParecer(...).save(flush: true, failOnError: true)
        //new TipoParecer(...).save(flush: true, failOnError: true)
        //new TipoParecer(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tipoParecer.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        tipoParecerService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TipoParecer> tipoParecerList = tipoParecerService.list(max: 2, offset: 2)

        then:
        tipoParecerList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tipoParecerService.count() == 5
    }

    void "test delete"() {
        Long tipoParecerId = setupData()

        expect:
        tipoParecerService.count() == 5

        when:
        tipoParecerService.delete(tipoParecerId)
        datastore.currentSession.flush()

        then:
        tipoParecerService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TipoParecer tipoParecer = new TipoParecer()
        tipoParecerService.save(tipoParecer)

        then:
        tipoParecer.id != null
    }
}
