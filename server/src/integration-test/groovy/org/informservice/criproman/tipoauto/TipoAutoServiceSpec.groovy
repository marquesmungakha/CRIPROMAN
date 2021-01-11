package org.informservice.criproman.tipoauto

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TipoAutoServiceSpec extends Specification {

    TipoAutoService tipoAutoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TipoAuto(...).save(flush: true, failOnError: true)
        //new TipoAuto(...).save(flush: true, failOnError: true)
        //TipoAuto tipoAuto = new TipoAuto(...).save(flush: true, failOnError: true)
        //new TipoAuto(...).save(flush: true, failOnError: true)
        //new TipoAuto(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tipoAuto.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        tipoAutoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TipoAuto> tipoAutoList = tipoAutoService.list(max: 2, offset: 2)

        then:
        tipoAutoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tipoAutoService.count() == 5
    }

    void "test delete"() {
        Long tipoAutoId = setupData()

        expect:
        tipoAutoService.count() == 5

        when:
        tipoAutoService.delete(tipoAutoId)
        datastore.currentSession.flush()

        then:
        tipoAutoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TipoAuto tipoAuto = new TipoAuto()
        tipoAutoService.save(tipoAuto)

        then:
        tipoAuto.id != null
    }
}
