package org.informservice.criproman.apreensao

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ApreensaoServiceSpec extends Specification {

    ApreensaoService apreensaoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Apreensao(...).save(flush: true, failOnError: true)
        //new Apreensao(...).save(flush: true, failOnError: true)
        //Apreensao apreensao = new Apreensao(...).save(flush: true, failOnError: true)
        //new Apreensao(...).save(flush: true, failOnError: true)
        //new Apreensao(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //apreensao.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        apreensaoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Apreensao> apreensaoList = apreensaoService.list(max: 2, offset: 2)

        then:
        apreensaoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        apreensaoService.count() == 5
    }

    void "test delete"() {
        Long apreensaoId = setupData()

        expect:
        apreensaoService.count() == 5

        when:
        apreensaoService.delete(apreensaoId)
        datastore.currentSession.flush()

        then:
        apreensaoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Apreensao apreensao = new Apreensao()
        apreensaoService.save(apreensao)

        then:
        apreensao.id != null
    }
}
