package org.informservice.criproman.inspector

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
class InspectorController {

    InspectorService inspectorService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond inspectorService.list(params), model:[inspectorCount: inspectorService.count()]
    }

    def show(Long id) {
        respond inspectorService.get(id)
    }

    @Transactional
    def save(Inspector inspector) {
        if (inspector == null) {
            render status: NOT_FOUND
            return
        }
        if (inspector.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond inspector.errors
            return
        }

        try {
            inspectorService.save(inspector)
        } catch (ValidationException e) {
            respond inspector.errors
            return
        }

        respond inspector, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Inspector inspector) {
        if (inspector == null) {
            render status: NOT_FOUND
            return
        }
        if (inspector.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond inspector.errors
            return
        }

        try {
            inspectorService.save(inspector)
        } catch (ValidationException e) {
            respond inspector.errors
            return
        }

        respond inspector, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || inspectorService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
