package org.informservice.criproman.parecerProcesso

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ParecerProcessoServiceSpec extends Specification {

    ParecerProcessoService parecerProcessoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ParecerProcesso(...).save(flush: true, failOnError: true)
        //new ParecerProcesso(...).save(flush: true, failOnError: true)
        //ParecerProcesso parecerProcesso = new ParecerProcesso(...).save(flush: true, failOnError: true)
        //new ParecerProcesso(...).save(flush: true, failOnError: true)
        //new ParecerProcesso(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //parecerProcesso.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        parecerProcessoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ParecerProcesso> parecerProcessoList = parecerProcessoService.list(max: 2, offset: 2)

        then:
        parecerProcessoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        parecerProcessoService.count() == 5
    }

    void "test delete"() {
        Long parecerProcessoId = setupData()

        expect:
        parecerProcessoService.count() == 5

        when:
        parecerProcessoService.delete(parecerProcessoId)
        datastore.currentSession.flush()

        then:
        parecerProcessoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ParecerProcesso parecerProcesso = new ParecerProcesso()
        parecerProcessoService.save(parecerProcesso)

        then:
        parecerProcesso.id != null
    }
}
