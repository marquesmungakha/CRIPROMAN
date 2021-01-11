package org.informservice.criproman.ofendido

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
class OfendidoController {

    OfendidoService ofendidoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ofendidoService.list(params), model:[ofendidoCount: ofendidoService.count()]
    }

    def show(Long id) {
        respond ofendidoService.get(id)
    }

    @Transactional
    def save(Ofendido ofendido) {
        if (ofendido == null) {
            render status: NOT_FOUND
            return
        }
        if (ofendido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ofendido.errors
            return
        }

        try {
            ofendidoService.save(ofendido)
        } catch (ValidationException e) {
            respond ofendido.errors
            return
        }

        respond ofendido, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Ofendido ofendido) {
        if (ofendido == null) {
            render status: NOT_FOUND
            return
        }
        if (ofendido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond ofendido.errors
            return
        }

        try {
            ofendidoService.save(ofendido)
        } catch (ValidationException e) {
            respond ofendido.errors
            return
        }

        respond ofendido, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || ofendidoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
