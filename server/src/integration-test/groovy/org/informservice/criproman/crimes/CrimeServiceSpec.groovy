package org.informservice.criproman.crimes

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class CrimeServiceSpec extends Specification {

    CrimeService crimeService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Crime(...).save(flush: true, failOnError: true)
        //new Crime(...).save(flush: true, failOnError: true)
        //Crime crime = new Crime(...).save(flush: true, failOnError: true)
        //new Crime(...).save(flush: true, failOnError: true)
        //new Crime(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //crime.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        crimeService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Crime> crimeList = crimeService.list(max: 2, offset: 2)

        then:
        crimeList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        crimeService.count() == 5
    }

    void "test delete"() {
        Long crimeId = setupData()

        expect:
        crimeService.count() == 5

        when:
        crimeService.delete(crimeId)
        datastore.currentSession.flush()

        then:
        crimeService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Crime crime = new Crime()
        crimeService.save(crime)

        then:
        crime.id != null
    }
}
