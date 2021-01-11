package org.informservice.criproman.formaProcesso

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class FormaProcessoServiceSpec extends Specification {

    FormaProcessoService formaProcessoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new FormaProcesso(...).save(flush: true, failOnError: true)
        //new FormaProcesso(...).save(flush: true, failOnError: true)
        //FormaProcesso formaProcesso = new FormaProcesso(...).save(flush: true, failOnError: true)
        //new FormaProcesso(...).save(flush: true, failOnError: true)
        //new FormaProcesso(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //formaProcesso.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        formaProcessoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<FormaProcesso> formaProcessoList = formaProcessoService.list(max: 2, offset: 2)

        then:
        formaProcessoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        formaProcessoService.count() == 5
    }

    void "test delete"() {
        Long formaProcessoId = setupData()

        expect:
        formaProcessoService.count() == 5

        when:
        formaProcessoService.delete(formaProcessoId)
        datastore.currentSession.flush()

        then:
        formaProcessoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        FormaProcesso formaProcesso = new FormaProcesso()
        formaProcessoService.save(formaProcesso)

        then:
        formaProcesso.id != null
    }
}
