package org.informservice.criproman.impressaoDigital

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ImpressaoDigitalServiceSpec extends Specification {

    ImpressaoDigitalService impressaoDigitalService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ImpressaoDigital(...).save(flush: true, failOnError: true)
        //new ImpressaoDigital(...).save(flush: true, failOnError: true)
        //ImpressaoDigital impressaoDigital = new ImpressaoDigital(...).save(flush: true, failOnError: true)
        //new ImpressaoDigital(...).save(flush: true, failOnError: true)
        //new ImpressaoDigital(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //impressaoDigital.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        impressaoDigitalService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ImpressaoDigital> impressaoDigitalList = impressaoDigitalService.list(max: 2, offset: 2)

        then:
        impressaoDigitalList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        impressaoDigitalService.count() == 5
    }

    void "test delete"() {
        Long impressaoDigitalId = setupData()

        expect:
        impressaoDigitalService.count() == 5

        when:
        impressaoDigitalService.delete(impressaoDigitalId)
        datastore.currentSession.flush()

        then:
        impressaoDigitalService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ImpressaoDigital impressaoDigital = new ImpressaoDigital()
        impressaoDigitalService.save(impressaoDigital)

        then:
        impressaoDigital.id != null
    }
}
