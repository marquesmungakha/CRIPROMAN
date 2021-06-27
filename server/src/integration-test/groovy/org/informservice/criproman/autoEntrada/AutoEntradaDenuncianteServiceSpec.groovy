package org.informservice.criproman.autoEntrada

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AutoEntradaDenuncianteServiceSpec extends Specification {

    AutoEntradaDenuncianteService autoEntradaDenuncianteService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AutoEntradaDenunciante(...).save(flush: true, failOnError: true)
        //new AutoEntradaDenunciante(...).save(flush: true, failOnError: true)
        //AutoEntradaDenunciante autoEntradaDenunciante = new AutoEntradaDenunciante(...).save(flush: true, failOnError: true)
        //new AutoEntradaDenunciante(...).save(flush: true, failOnError: true)
        //new AutoEntradaDenunciante(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //autoEntradaDenunciante.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        autoEntradaDenuncianteService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AutoEntradaDenunciante> autoEntradaDenuncianteList = autoEntradaDenuncianteService.list(max: 2, offset: 2)

        then:
        autoEntradaDenuncianteList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        autoEntradaDenuncianteService.count() == 5
    }

    void "test delete"() {
        Long autoEntradaDenuncianteId = setupData()

        expect:
        autoEntradaDenuncianteService.count() == 5

        when:
        autoEntradaDenuncianteService.delete(autoEntradaDenuncianteId)
        datastore.currentSession.flush()

        then:
        autoEntradaDenuncianteService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AutoEntradaDenunciante autoEntradaDenunciante = new AutoEntradaDenunciante()
        autoEntradaDenuncianteService.save(autoEntradaDenunciante)

        then:
        autoEntradaDenunciante.id != null
    }
}
