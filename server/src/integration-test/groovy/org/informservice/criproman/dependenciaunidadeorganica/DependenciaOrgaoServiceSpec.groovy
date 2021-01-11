package org.informservice.criproman.dependenciaunidadeorganica

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DependenciaOrgaoServiceSpec extends Specification {

    DependenciaOrgaoService dependenciaOrgaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new DependenciaOrgao(...).save(flush: true, failOnError: true)
        //new DependenciaOrgao(...).save(flush: true, failOnError: true)
        //DependenciaOrgao dependenciaOrgao = new DependenciaOrgao(...).save(flush: true, failOnError: true)
        //new DependenciaOrgao(...).save(flush: true, failOnError: true)
        //new DependenciaOrgao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //dependenciaOrgao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        dependenciaOrgaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<DependenciaOrgao> dependenciaOrgaoList = dependenciaOrgaoService.list(max: 2, offset: 2)

        then:
        dependenciaOrgaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        dependenciaOrgaoService.count() == 5
    }

    void "test delete"() {
        Long dependenciaOrgaoId = setupData()

        expect:
        dependenciaOrgaoService.count() == 5

        when:
        dependenciaOrgaoService.delete(dependenciaOrgaoId)
        datastore.currentSession.flush()

        then:
        dependenciaOrgaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        DependenciaOrgao dependenciaOrgao = new DependenciaOrgao()
        dependenciaOrgaoService.save(dependenciaOrgao)

        then:
        dependenciaOrgao.id != null
    }
}
