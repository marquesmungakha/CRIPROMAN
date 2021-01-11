package org.informservice.criproman.seguranca

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class AuthenticationTokenController {

    AuthenticationTokenService authenticationTokenService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond authenticationTokenService.list(params), model:[authenticationTokenCount: authenticationTokenService.count()]
    }

    def show(Long id) {
        respond authenticationTokenService.get(id)
    }

    @Transactional
    def save(AuthenticationToken authenticationToken) {
        if (authenticationToken == null) {
            render status: NOT_FOUND
            return
        }
        if (authenticationToken.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond authenticationToken.errors
            return
        }

        try {
            authenticationTokenService.save(authenticationToken)
        } catch (ValidationException e) {
            respond authenticationToken.errors
            return
        }

        respond authenticationToken, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AuthenticationToken authenticationToken) {
        if (authenticationToken == null) {
            render status: NOT_FOUND
            return
        }
        if (authenticationToken.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond authenticationToken.errors
            return
        }

        try {
            authenticationTokenService.save(authenticationToken)
        } catch (ValidationException e) {
            respond authenticationToken.errors
            return
        }

        respond authenticationToken, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || authenticationTokenService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
