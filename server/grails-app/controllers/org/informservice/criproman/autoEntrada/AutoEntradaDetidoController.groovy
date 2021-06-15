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
class AutoEntradaDetidoController {

    AutoEntradaDetidoService autoEntradaDetidoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaDetidoService.list(params), model:[autoEntradaDetidoCount: autoEntradaDetidoService.count()]
    }

    def show(Long id) {
        respond autoEntradaDetidoService.get(id)
    }

    @Transactional
    def save(AutoEntradaDetido autoEntradaDetido) {
        if (autoEntradaDetido == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaDetido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaDetido.errors
            return
        }

        try {
            autoEntradaDetidoService.save(autoEntradaDetido)
        } catch (ValidationException e) {
            respond autoEntradaDetido.errors
            return
        }

        respond autoEntradaDetido, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaDetido autoEntradaDetido) {
        if (autoEntradaDetido == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaDetido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaDetido.errors
            return
        }

        try {
            autoEntradaDetidoService.save(autoEntradaDetido)
        } catch (ValidationException e) {
            respond autoEntradaDetido.errors
            return
        }

        respond autoEntradaDetido, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaDetidoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
