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
class AutoEntradaArguidoController {

    AutoEntradaArguidoService autoEntradaArguidoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaArguidoService.list(params), model:[autoEntradaArguidoCount: autoEntradaArguidoService.count()]
    }

    def show(Long id) {
        respond autoEntradaArguidoService.get(id)
    }

    @Transactional
    def save(AutoEntradaArguido autoEntradaArguido) {
        if (autoEntradaArguido == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaArguido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaArguido.errors
            return
        }

        try {
            autoEntradaArguidoService.save(autoEntradaArguido)
        } catch (ValidationException e) {
            respond autoEntradaArguido.errors
            return
        }

        respond autoEntradaArguido, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaArguido autoEntradaArguido) {
        if (autoEntradaArguido == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaArguido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaArguido.errors
            return
        }

        try {
            autoEntradaArguidoService.save(autoEntradaArguido)
        } catch (ValidationException e) {
            respond autoEntradaArguido.errors
            return
        }

        respond autoEntradaArguido, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaArguidoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
