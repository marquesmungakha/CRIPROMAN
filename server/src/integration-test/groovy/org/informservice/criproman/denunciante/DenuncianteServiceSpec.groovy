package org.informservice.criproman.denunciante

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class DenuncianteServiceSpec extends Specification {

    DenuncianteService denuncianteService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Denunciante(...).save(flush: true, failOnError: true)
        //new Denunciante(...).save(flush: true, failOnError: true)
        //Denunciante denunciante = new Denunciante(...).save(flush: true, failOnError: true)
        //new Denunciante(...).save(flush: true, failOnError: true)
        //new Denunciante(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //denunciante.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        denuncianteService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Denunciante> denuncianteList = denuncianteService.list(max: 2, offset: 2)

        then:
        denuncianteList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        denuncianteService.count() == 5
    }

    void "test delete"() {
        Long denuncianteId = setupData()

        expect:
        denuncianteService.count() == 5

        when:
        denuncianteService.delete(denuncianteId)
        datastore.currentSession.flush()

        then:
        denuncianteService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Denunciante denunciante = new Denunciante()
        denuncianteService.save(denunciante)

        then:
        denunciante.id != null
    }
}
