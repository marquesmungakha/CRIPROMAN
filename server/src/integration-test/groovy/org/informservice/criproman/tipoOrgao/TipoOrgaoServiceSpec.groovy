package org.informservice.criproman.tipoOrgao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class TipoOrgaoServiceSpec extends Specification {

    TipoOrgaoService tipoOrgaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new TipoOrgao(...).save(flush: true, failOnError: true)
        //new TipoOrgao(...).save(flush: true, failOnError: true)
        //TipoOrgao tipoOrgao = new TipoOrgao(...).save(flush: true, failOnError: true)
        //new TipoOrgao(...).save(flush: true, failOnError: true)
        //new TipoOrgao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //tipoOrgao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        tipoOrgaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<TipoOrgao> tipoOrgaoList = tipoOrgaoService.list(max: 2, offset: 2)

        then:
        tipoOrgaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        tipoOrgaoService.count() == 5
    }

    void "test delete"() {
        Long tipoOrgaoId = setupData()

        expect:
        tipoOrgaoService.count() == 5

        when:
        tipoOrgaoService.delete(tipoOrgaoId)
        datastore.currentSession.flush()

        then:
        tipoOrgaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        TipoOrgao tipoOrgao = new TipoOrgao()
        tipoOrgaoService.save(tipoOrgao)

        then:
        tipoOrgao.id != null
    }
}
