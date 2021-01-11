package org.informservice.criproman.objectosDetido

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ObjectosDetidoServiceSpec extends Specification {

    ObjectosDetidoService objectosDetidoService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ObjectosDetido(...).save(flush: true, failOnError: true)
        //new ObjectosDetido(...).save(flush: true, failOnError: true)
        //ObjectosDetido objectosDetido = new ObjectosDetido(...).save(flush: true, failOnError: true)
        //new ObjectosDetido(...).save(flush: true, failOnError: true)
        //new ObjectosDetido(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //objectosDetido.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        objectosDetidoService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ObjectosDetido> objectosDetidoList = objectosDetidoService.list(max: 2, offset: 2)

        then:
        objectosDetidoList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        objectosDetidoService.count() == 5
    }

    void "test delete"() {
        Long objectosDetidoId = setupData()

        expect:
        objectosDetidoService.count() == 5

        when:
        objectosDetidoService.delete(objectosDetidoId)
        datastore.currentSession.flush()

        then:
        objectosDetidoService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ObjectosDetido objectosDetido = new ObjectosDetido()
        objectosDetidoService.save(objectosDetido)

        then:
        objectosDetido.id != null
    }
}
