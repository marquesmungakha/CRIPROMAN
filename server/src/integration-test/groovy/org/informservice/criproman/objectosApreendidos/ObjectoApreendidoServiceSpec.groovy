package org.informservice.criproman.objectosApreendidos

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ObjectoApreendidoServiceSpec extends Specification {

    ObjectosApreendidosService objectosApreendidosService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ObjectosApreendidos(...).save(flush: true, failOnError: true)
        //new ObjectosApreendidos(...).save(flush: true, failOnError: true)
        //ObjectosApreendidos objectosApreendidos = new ObjectosApreendidos(...).save(flush: true, failOnError: true)
        //new ObjectosApreendidos(...).save(flush: true, failOnError: true)
        //new ObjectosApreendidos(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //objectosApreendidos.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        objectosApreendidosService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ObjectoApreendido> objectosApreendidosList = objectosApreendidosService.list(max: 2, offset: 2)

        then:
        objectosApreendidosList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        objectosApreendidosService.count() == 5
    }

    void "test delete"() {
        Long objectosApreendidosId = setupData()

        expect:
        objectosApreendidosService.count() == 5

        when:
        objectosApreendidosService.delete(objectosApreendidosId)
        datastore.currentSession.flush()

        then:
        objectosApreendidosService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ObjectoApreendido objectosApreendidos = new ObjectoApreendido()
        objectosApreendidosService.save(objectosApreendidos)

        then:
        objectosApreendidos.id != null
    }
}
