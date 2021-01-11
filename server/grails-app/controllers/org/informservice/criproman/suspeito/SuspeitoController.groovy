package org.informservice.criproman.suspeito

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
class SuspeitoController {

    SuspeitoService suspeitoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond suspeitoService.list(params), model:[suspeitoCount: suspeitoService.count()]
    }

    def show(Long id) {
        respond suspeitoService.get(id)
    }

    @Transactional
    def save(Suspeito suspeito) {
        if (suspeito == null) {
            render status: NOT_FOUND
            return
        }
        if (suspeito.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond suspeito.errors
            return
        }

        try {
            suspeitoService.save(suspeito)
        } catch (ValidationException e) {
            respond suspeito.errors
            return
        }

        respond suspeito, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Suspeito suspeito) {
        if (suspeito == null) {
            render status: NOT_FOUND
            return
        }
        if (suspeito.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond suspeito.errors
            return
        }

        try {
            suspeitoService.save(suspeito)
        } catch (ValidationException e) {
            respond suspeito.errors
            return
        }

        respond suspeito, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || suspeitoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
