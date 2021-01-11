package org.informservice.criproman.arguido

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
class ArguidoController {

    ArguidoService arguidoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond arguidoService.list(params), model:[arguidoCount: arguidoService.count()]
    }

    def show(Long id) {
        respond arguidoService.get(id)
    }

    @Transactional
    def save(Arguido arguido) {
        if (arguido == null) {
            render status: NOT_FOUND
            return
        }
        if (arguido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond arguido.errors
            return
        }

        try {
            arguidoService.save(arguido)
        } catch (ValidationException e) {
            respond arguido.errors
            return
        }

        respond arguido, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Arguido arguido) {
        if (arguido == null) {
            render status: NOT_FOUND
            return
        }
        if (arguido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond arguido.errors
            return
        }

        try {
            arguidoService.save(arguido)
        } catch (ValidationException e) {
            respond arguido.errors
            return
        }

        respond arguido, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || arguidoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
