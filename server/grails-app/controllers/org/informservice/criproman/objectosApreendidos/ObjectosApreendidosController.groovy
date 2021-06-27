package org.informservice.criproman.objectosApreendidos

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
@Secured('ROLE_ADMIN')
class ObjectosApreendidosController {

    ObjectosApreendidosService objectosApreendidosService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond objectosApreendidosService.list(params), model:[objectosApreendidosCount: objectosApreendidosService.count()]
    }

    def show(Long id) {
        respond objectosApreendidosService.get(id)
    }

    @Transactional
    def save(ObjectoApreendido objectosApreendidos) {
        if (objectosApreendidos == null) {
            render status: NOT_FOUND
            return
        }
        if (objectosApreendidos.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond objectosApreendidos.errors
            return
        }

        try {
            objectosApreendidosService.save(objectosApreendidos)
        } catch (ValidationException e) {
            respond objectosApreendidos.errors
            return
        }

        respond objectosApreendidos, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ObjectoApreendido objectosApreendidos) {
        if (objectosApreendidos == null) {
            render status: NOT_FOUND
            return
        }
        if (objectosApreendidos.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond objectosApreendidos.errors
            return
        }

        try {
            objectosApreendidosService.save(objectosApreendidos)
        } catch (ValidationException e) {
            respond objectosApreendidos.errors
            return
        }

        respond objectosApreendidos, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || objectosApreendidosService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
