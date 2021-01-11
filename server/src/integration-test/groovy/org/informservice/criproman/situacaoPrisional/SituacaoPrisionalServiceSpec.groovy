package org.informservice.criproman.situacaoPrisional

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class SituacaoPrisionalServiceSpec extends Specification {

    SituacaoPrisionalService situacaoPrisionalService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new SituacaoPrisional(...).save(flush: true, failOnError: true)
        //new SituacaoPrisional(...).save(flush: true, failOnError: true)
        //SituacaoPrisional situacaoPrisional = new SituacaoPrisional(...).save(flush: true, failOnError: true)
        //new SituacaoPrisional(...).save(flush: true, failOnError: true)
        //new SituacaoPrisional(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //situacaoPrisional.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        situacaoPrisionalService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<SituacaoPrisional> situacaoPrisionalList = situacaoPrisionalService.list(max: 2, offset: 2)

        then:
        situacaoPrisionalList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        situacaoPrisionalService.count() == 5
    }

    void "test delete"() {
        Long situacaoPrisionalId = setupData()

        expect:
        situacaoPrisionalService.count() == 5

        when:
        situacaoPrisionalService.delete(situacaoPrisionalId)
        datastore.currentSession.flush()

        then:
        situacaoPrisionalService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        SituacaoPrisional situacaoPrisional = new SituacaoPrisional()
        situacaoPrisionalService.save(situacaoPrisional)

        then:
        situacaoPrisional.id != null
    }
}
