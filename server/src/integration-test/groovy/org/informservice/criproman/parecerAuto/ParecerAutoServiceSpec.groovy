package org.informservice.criproman.parecerAuto

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ParecerAutoServiceSpec extends Specification {

    ParecerAutoService parecerAutoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ParecerAuto(...).save(flush: true, failOnError: true)
        //new ParecerAuto(...).save(flush: true, failOnError: true)
        //ParecerAuto parecerAuto = new ParecerAuto(...).save(flush: true, failOnError: true)
        //new ParecerAuto(...).save(flush: true, failOnError: true)
        //new ParecerAuto(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //parecerAuto.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        parecerAutoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ParecerAuto> parecerAutoList = parecerAutoService.list(max: 2, offset: 2)

        then:
        parecerAutoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        parecerAutoService.count() == 5
    }

    void "test delete"() {
        Long parecerAutoId = setupData()

        expect:
        parecerAutoService.count() == 5

        when:
        parecerAutoService.delete(parecerAutoId)
        datastore.currentSession.flush()

        then:
        parecerAutoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ParecerAuto parecerAuto = new ParecerAuto()
        parecerAutoService.save(parecerAuto)

        then:
        parecerAuto.id != null
    }
}
