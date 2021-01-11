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
class AuditoriaController {

    AuditoriaService auditoriaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond auditoriaService.list(params), model:[auditoriaCount: auditoriaService.count()]
    }

    def show(Long id) {
        respond auditoriaService.get(id)
    }

    @Transactional
    def save(Auditoria auditoria) {
        if (auditoria == null) {
            render status: NOT_FOUND
            return
        }
        if (auditoria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond auditoria.errors
            return
        }

        try {
            auditoriaService.save(auditoria)
        } catch (ValidationException e) {
            respond auditoria.errors
            return
        }

        respond auditoria, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Auditoria auditoria) {
        if (auditoria == null) {
            render status: NOT_FOUND
            return
        }
        if (auditoria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond auditoria.errors
            return
        }

        try {
            auditoriaService.save(auditoria)
        } catch (ValidationException e) {
            respond auditoria.errors
            return
        }

        respond auditoria, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || auditoriaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
