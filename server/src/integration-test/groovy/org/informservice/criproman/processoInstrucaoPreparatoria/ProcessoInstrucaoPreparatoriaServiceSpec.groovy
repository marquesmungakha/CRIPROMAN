package org.informservice.criproman.processoInstrucaoPreparatoria

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ProcessoInstrucaoPreparatoriaServiceSpec extends Specification {

    ProcessoInstrucaoPreparatoriaService processoInstrucaoPreparatoriaService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ProcessoInstrucaoPreparatoria(...).save(flush: true, failOnError: true)
        //new ProcessoInstrucaoPreparatoria(...).save(flush: true, failOnError: true)
        //ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria = new ProcessoInstrucaoPreparatoria(...).save(flush: true, failOnError: true)
        //new ProcessoInstrucaoPreparatoria(...).save(flush: true, failOnError: true)
        //new ProcessoInstrucaoPreparatoria(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //processoInstrucaoPreparatoria.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        processoInstrucaoPreparatoriaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ProcessoInstrucaoPreparatoria> processoInstrucaoPreparatoriaList = processoInstrucaoPreparatoriaService.list(max: 2, offset: 2)

        then:
        processoInstrucaoPreparatoriaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        processoInstrucaoPreparatoriaService.count() == 5
    }

    void "test delete"() {
        Long processoInstrucaoPreparatoriaId = setupData()

        expect:
        processoInstrucaoPreparatoriaService.count() == 5

        when:
        processoInstrucaoPreparatoriaService.delete(processoInstrucaoPreparatoriaId)
        datastore.currentSession.flush()

        then:
        processoInstrucaoPreparatoriaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria = new ProcessoInstrucaoPreparatoria()
        processoInstrucaoPreparatoriaService.save(processoInstrucaoPreparatoria)

        then:
        processoInstrucaoPreparatoria.id != null
    }
}
