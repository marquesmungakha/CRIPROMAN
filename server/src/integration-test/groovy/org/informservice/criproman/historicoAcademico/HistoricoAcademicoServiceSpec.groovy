package org.informservice.criproman.historicoAcademico

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class HistoricoAcademicoServiceSpec extends Specification {

    HistoricoAcademicoService historicoAcademicoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new HistoricoAcademico(...).save(flush: true, failOnError: true)
        //new HistoricoAcademico(...).save(flush: true, failOnError: true)
        //HistoricoAcademico historicoAcademico = new HistoricoAcademico(...).save(flush: true, failOnError: true)
        //new HistoricoAcademico(...).save(flush: true, failOnError: true)
        //new HistoricoAcademico(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //historicoAcademico.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        historicoAcademicoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<HistoricoAcademico> historicoAcademicoList = historicoAcademicoService.list(max: 2, offset: 2)

        then:
        historicoAcademicoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        historicoAcademicoService.count() == 5
    }

    void "test delete"() {
        Long historicoAcademicoId = setupData()

        expect:
        historicoAcademicoService.count() == 5

        when:
        historicoAcademicoService.delete(historicoAcademicoId)
        datastore.currentSession.flush()

        then:
        historicoAcademicoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        HistoricoAcademico historicoAcademico = new HistoricoAcademico()
        historicoAcademicoService.save(historicoAcademico)

        then:
        historicoAcademico.id != null
    }
}
