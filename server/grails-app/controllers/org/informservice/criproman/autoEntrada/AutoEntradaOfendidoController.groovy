package org.informservice.criproman.autoEntrada

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class AutoEntradaOfendidoController {

    AutoEntradaOfendidoService autoEntradaOfendidoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaOfendidoService.list(params), model:[autoEntradaOfendidoCount: autoEntradaOfendidoService.count()]
    }

    def show(Long id) {
        respond autoEntradaOfendidoService.get(id)
    }

    @Transactional
    def save(AutoEntradaOfendido autoEntradaOfendido) {
        if (autoEntradaOfendido == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaOfendido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaOfendido.errors
            return
        }

        try {
            autoEntradaOfendidoService.save(autoEntradaOfendido)
        } catch (ValidationException e) {
            respond autoEntradaOfendido.errors
            return
        }

        respond autoEntradaOfendido, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaOfendido autoEntradaOfendido) {
        if (autoEntradaOfendido == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaOfendido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaOfendido.errors
            return
        }

        try {
            autoEntradaOfendidoService.save(autoEntradaOfendido)
        } catch (ValidationException e) {
            respond autoEntradaOfendido.errors
            return
        }

        respond autoEntradaOfendido, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaOfendidoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
