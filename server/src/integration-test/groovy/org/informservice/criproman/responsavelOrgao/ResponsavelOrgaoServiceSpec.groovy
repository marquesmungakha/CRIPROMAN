package org.informservice.criproman.responsavelOrgao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ResponsavelOrgaoServiceSpec extends Specification {

    ResponsavelOrgaoService responsavelOrgaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ResponsavelOrgao(...).save(flush: true, failOnError: true)
        //new ResponsavelOrgao(...).save(flush: true, failOnError: true)
        //ResponsavelOrgao responsavelOrgao = new ResponsavelOrgao(...).save(flush: true, failOnError: true)
        //new ResponsavelOrgao(...).save(flush: true, failOnError: true)
        //new ResponsavelOrgao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //responsavelOrgao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        responsavelOrgaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ResponsavelOrgao> responsavelOrgaoList = responsavelOrgaoService.list(max: 2, offset: 2)

        then:
        responsavelOrgaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        responsavelOrgaoService.count() == 5
    }

    void "test delete"() {
        Long responsavelOrgaoId = setupData()

        expect:
        responsavelOrgaoService.count() == 5

        when:
        responsavelOrgaoService.delete(responsavelOrgaoId)
        datastore.currentSession.flush()

        then:
        responsavelOrgaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ResponsavelOrgao responsavelOrgao = new ResponsavelOrgao()
        responsavelOrgaoService.save(responsavelOrgao)

        then:
        responsavelOrgao.id != null
    }
}
