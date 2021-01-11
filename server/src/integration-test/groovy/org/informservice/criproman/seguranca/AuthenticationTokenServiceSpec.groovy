package org.informservice.criproman.seguranca

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration
@Rollback
class AuthenticationTokenServiceSpec extends Specification {

    AuthenticationTokenService authenticationTokenService
    @Autowired Datastore datastore

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new AuthenticationToken(...).save(flush: true, failOnError: true)
        //new AuthenticationToken(...).save(flush: true, failOnError: true)
        //AuthenticationToken authenticationToken = new AuthenticationToken(...).save(flush: true, failOnError: true)
        //new AuthenticationToken(...).save(flush: true, failOnError: true)
        //new AuthenticationToken(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //authenticationToken.id
    }

    void cleanup() {
        assert false, "TODO: Provide a cleanup implementation if using MongoDB"
    }

    void "test get"() {
        setupData()

        expect:
        authenticationTokenService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<AuthenticationToken> authenticationTokenList = authenticationTokenService.list(max: 2, offset: 2)

        then:
        authenticationTokenList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        authenticationTokenService.count() == 5
    }

    void "test delete"() {
        Long authenticationTokenId = setupData()

        expect:
        authenticationTokenService.count() == 5

        when:
        authenticationTokenService.delete(authenticationTokenId)
        datastore.currentSession.flush()

        then:
        authenticationTokenService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        AuthenticationToken authenticationToken = new AuthenticationToken()
        authenticationTokenService.save(authenticationToken)

        then:
        authenticationToken.id != null
    }
}
