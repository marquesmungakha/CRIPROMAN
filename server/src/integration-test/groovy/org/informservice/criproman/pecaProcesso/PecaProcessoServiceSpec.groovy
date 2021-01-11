package org.informservice.criproman.pecaProcesso

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class PecaProcessoServiceSpec extends Specification {

    PecaProcessoService pecaProcessoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PecaProcesso(...).save(flush: true, failOnError: true)
        //new PecaProcesso(...).save(flush: true, failOnError: true)
        //PecaProcesso pecaProcesso = new PecaProcesso(...).save(flush: true, failOnError: true)
        //new PecaProcesso(...).save(flush: true, failOnError: true)
        //new PecaProcesso(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //pecaProcesso.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        pecaProcessoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PecaProcesso> pecaProcessoList = pecaProcessoService.list(max: 2, offset: 2)

        then:
        pecaProcessoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        pecaProcessoService.count() == 5
    }

    void "test delete"() {
        Long pecaProcessoId = setupData()

        expect:
        pecaProcessoService.count() == 5

        when:
        pecaProcessoService.delete(pecaProcessoId)
        datastore.currentSession.flush()

        then:
        pecaProcessoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PecaProcesso pecaProcesso = new PecaProcesso()
        pecaProcessoService.save(pecaProcesso)

        then:
        pecaProcesso.id != null
    }
}
