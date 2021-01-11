package org.informservice.criproman.tipoDedo

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TipoDedoServiceSpec extends Specification {

    TipoDedoService tipoDedoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TipoDedo(...).save(flush: true, failOnError: true)
        //new TipoDedo(...).save(flush: true, failOnError: true)
        //TipoDedo tipoDedo = new TipoDedo(...).save(flush: true, failOnError: true)
        //new TipoDedo(...).save(flush: true, failOnError: true)
        //new TipoDedo(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tipoDedo.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        tipoDedoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TipoDedo> tipoDedoList = tipoDedoService.list(max: 2, offset: 2)

        then:
        tipoDedoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tipoDedoService.count() == 5
    }

    void "test delete"() {
        Long tipoDedoId = setupData()

        expect:
        tipoDedoService.count() == 5

        when:
        tipoDedoService.delete(tipoDedoId)
        datastore.currentSession.flush()

        then:
        tipoDedoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TipoDedo tipoDedo = new TipoDedo()
        tipoDedoService.save(tipoDedo)

        then:
        tipoDedo.id != null
    }
}
