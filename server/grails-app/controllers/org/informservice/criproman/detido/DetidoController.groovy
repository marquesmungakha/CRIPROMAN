package org.informservice.criproman.detido

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
class DetidoController {

    DetidoService detidoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond detidoService.list(params), model:[detidoCount: detidoService.count()]
    }

    def show(Long id) {
        respond detidoService.get(id)
    }

    @Transactional
    def save(Detido detido) {
        if (detido == null) {
            render status: NOT_FOUND
            return
        }
        if (detido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond detido.errors
            return
        }

        try {
            detidoService.save(detido)
        } catch (ValidationException e) {
            respond detido.errors
            return
        }

        respond detido, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Detido detido) {
        if (detido == null) {
            render status: NOT_FOUND
            return
        }
        if (detido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond detido.errors
            return
        }

        try {
            detidoService.save(detido)
        } catch (ValidationException e) {
            respond detido.errors
            return
        }

        respond detido, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || detidoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
