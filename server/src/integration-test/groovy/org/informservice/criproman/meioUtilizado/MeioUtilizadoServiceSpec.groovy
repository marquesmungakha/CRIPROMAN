package org.informservice.criproman.meioUtilizado

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class MeioUtilizadoServiceSpec extends Specification {

    MeioUtilizadoService meioUtilizadoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new MeioUtilizado(...).save(flush: true, failOnError: true)
        //new MeioUtilizado(...).save(flush: true, failOnError: true)
        //MeioUtilizado meioUtilizado = new MeioUtilizado(...).save(flush: true, failOnError: true)
        //new MeioUtilizado(...).save(flush: true, failOnError: true)
        //new MeioUtilizado(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //meioUtilizado.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        meioUtilizadoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<MeioUtilizado> meioUtilizadoList = meioUtilizadoService.list(max: 2, offset: 2)

        then:
        meioUtilizadoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        meioUtilizadoService.count() == 5
    }

    void "test delete"() {
        Long meioUtilizadoId = setupData()

        expect:
        meioUtilizadoService.count() == 5

        when:
        meioUtilizadoService.delete(meioUtilizadoId)
        datastore.currentSession.flush()

        then:
        meioUtilizadoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        MeioUtilizado meioUtilizado = new MeioUtilizado()
        meioUtilizadoService.save(meioUtilizado)

        then:
        meioUtilizado.id != null
    }
}
