package org.informservice.criproman.tipoParecerAuto

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TipoParecerAutoServiceSpec extends Specification {

    TipoParecerAutoService tipoParecerAutoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TipoParecerAuto(...).save(flush: true, failOnError: true)
        //new TipoParecerAuto(...).save(flush: true, failOnError: true)
        //TipoParecerAuto tipoParecerAuto = new TipoParecerAuto(...).save(flush: true, failOnError: true)
        //new TipoParecerAuto(...).save(flush: true, failOnError: true)
        //new TipoParecerAuto(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tipoParecerAuto.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        tipoParecerAutoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TipoParecerAuto> tipoParecerAutoList = tipoParecerAutoService.list(max: 2, offset: 2)

        then:
        tipoParecerAutoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tipoParecerAutoService.count() == 5
    }

    void "test delete"() {
        Long tipoParecerAutoId = setupData()

        expect:
        tipoParecerAutoService.count() == 5

        when:
        tipoParecerAutoService.delete(tipoParecerAutoId)
        datastore.currentSession.flush()

        then:
        tipoParecerAutoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TipoParecerAuto tipoParecerAuto = new TipoParecerAuto()
        tipoParecerAutoService.save(tipoParecerAuto)

        then:
        tipoParecerAuto.id != null
    }
}
