package org.informservice.criproman.objectosDetido

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
class ObjectosDetidoController {

    ObjectosDetidoService objectosDetidoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond objectosDetidoService.list(params), model:[objectosDetidoCount: objectosDetidoService.count()]
    }

    def show(Long id) {
        respond objectosDetidoService.get(id)
    }

    @Transactional
    def save(ObjectosDetido objectosDetido) {
        if (objectosDetido == null) {
            render status: NOT_FOUND
            return
        }
        if (objectosDetido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond objectosDetido.errors
            return
        }

        try {
            objectosDetidoService.save(objectosDetido)
        } catch (ValidationException e) {
            respond objectosDetido.errors
            return
        }

        respond objectosDetido, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ObjectosDetido objectosDetido) {
        if (objectosDetido == null) {
            render status: NOT_FOUND
            return
        }
        if (objectosDetido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond objectosDetido.errors
            return
        }

        try {
            objectosDetidoService.save(objectosDetido)
        } catch (ValidationException e) {
            respond objectosDetido.errors
            return
        }

        respond objectosDetido, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || objectosDetidoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
