package org.informservice.criproman.acusado

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
class AcusadoController {

    AcusadoService acusadoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond acusadoService.list(params), model:[acusadoCount: acusadoService.count()]
    }

    def show(Long id) {
        respond acusadoService.get(id)
    }

    @Transactional
    def save(Acusado acusado) {
        if (acusado == null) {
            render status: NOT_FOUND
            return
        }
        if (acusado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond acusado.errors
            return
        }

        try {
            acusadoService.save(acusado)
        } catch (ValidationException e) {
            respond acusado.errors
            return
        }

        respond acusado, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Acusado acusado) {
        if (acusado == null) {
            render status: NOT_FOUND
            return
        }
        if (acusado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond acusado.errors
            return
        }

        try {
            acusadoService.save(acusado)
        } catch (ValidationException e) {
            respond acusado.errors
            return
        }

        respond acusado, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || acusadoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
