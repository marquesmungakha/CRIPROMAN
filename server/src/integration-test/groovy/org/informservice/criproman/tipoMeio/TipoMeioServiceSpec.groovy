package org.informservice.criproman.tipoMeio

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TipoMeioServiceSpec extends Specification {

    TipoMeioService tipoMeioService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TipoMeio(...).save(flush: true, failOnError: true)
        //new TipoMeio(...).save(flush: true, failOnError: true)
        //TipoMeio tipoMeio = new TipoMeio(...).save(flush: true, failOnError: true)
        //new TipoMeio(...).save(flush: true, failOnError: true)
        //new TipoMeio(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tipoMeio.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        tipoMeioService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TipoMeio> tipoMeioList = tipoMeioService.list(max: 2, offset: 2)

        then:
        tipoMeioList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tipoMeioService.count() == 5
    }

    void "test delete"() {
        Long tipoMeioId = setupData()

        expect:
        tipoMeioService.count() == 5

        when:
        tipoMeioService.delete(tipoMeioId)
        datastore.currentSession.flush()

        then:
        tipoMeioService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TipoMeio tipoMeio = new TipoMeio()
        tipoMeioService.save(tipoMeio)

        then:
        tipoMeio.id != null
    }
}
