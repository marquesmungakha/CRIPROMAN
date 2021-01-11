package org.informservice.criproman.classeJudicial

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class ClasseJudicialServiceSpec extends Specification {

    ClasseJudicialService classeJudicialService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new ClasseJudicial(...).save(flush: true, failOnError: true)
        //new ClasseJudicial(...).save(flush: true, failOnError: true)
        //ClasseJudicial classeJudicial = new ClasseJudicial(...).save(flush: true, failOnError: true)
        //new ClasseJudicial(...).save(flush: true, failOnError: true)
        //new ClasseJudicial(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //classeJudicial.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        classeJudicialService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<ClasseJudicial> classeJudicialList = classeJudicialService.list(max: 2, offset: 2)

        then:
        classeJudicialList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        classeJudicialService.count() == 5
    }

    void "test delete"() {
        Long classeJudicialId = setupData()

        expect:
        classeJudicialService.count() == 5

        when:
        classeJudicialService.delete(classeJudicialId)
        datastore.currentSession.flush()

        then:
        classeJudicialService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        ClasseJudicial classeJudicial = new ClasseJudicial()
        classeJudicialService.save(classeJudicial)

        then:
        classeJudicial.id != null
    }
}
