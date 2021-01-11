package org.informservice.criproman.corpoDelito

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
@Secured('ROLE_ADMIN')
class CorpoDelitoController {

    CorpoDelitoService corpoDelitoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond corpoDelitoService.list(params), model:[corpoDelitoCount: corpoDelitoService.count()]
    }

    def show(Long id) {
        respond corpoDelitoService.get(id)
    }

    @Transactional
    def save(CorpoDelito corpoDelito) {
        if (corpoDelito == null) {
            render status: NOT_FOUND
            return
        }
        if (corpoDelito.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond corpoDelito.errors
            return
        }

        try {
            corpoDelitoService.save(corpoDelito)
        } catch (ValidationException e) {
            respond corpoDelito.errors
            return
        }

        respond corpoDelito, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(CorpoDelito corpoDelito) {
        if (corpoDelito == null) {
            render status: NOT_FOUND
            return
        }
        if (corpoDelito.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond corpoDelito.errors
            return
        }

        try {
            corpoDelitoService.save(corpoDelito)
        } catch (ValidationException e) {
            respond corpoDelito.errors
            return
        }

        respond corpoDelito, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || corpoDelitoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
