package org.informservice.criproman.modelo

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ModeloServiceSpec extends Specification {

    ModeloService modeloService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Modelo(...).save(flush: true, failOnError: true)
        //new Modelo(...).save(flush: true, failOnError: true)
        //Modelo modelo = new Modelo(...).save(flush: true, failOnError: true)
        //new Modelo(...).save(flush: true, failOnError: true)
        //new Modelo(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //modelo.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        modeloService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Modelo> modeloList = modeloService.list(max: 2, offset: 2)

        then:
        modeloList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        modeloService.count() == 5
    }

    void "test delete"() {
        Long modeloId = setupData()

        expect:
        modeloService.count() == 5

        when:
        modeloService.delete(modeloId)
        datastore.currentSession.flush()

        then:
        modeloService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Modelo modelo = new Modelo()
        modeloService.save(modelo)

        then:
        modelo.id != null
    }
}
