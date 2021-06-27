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
class AutoEntradaSuspeitoController {

    AutoEntradaSuspeitoService autoEntradaSuspeitoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaSuspeitoService.list(params), model:[autoEntradaSuspeitoCount: autoEntradaSuspeitoService.count()]
    }

    def show(Long id) {
        respond autoEntradaSuspeitoService.get(id)
    }

    @Transactional
    def save(AutoEntradaSuspeito autoEntradaSuspeito) {
        if (autoEntradaSuspeito == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaSuspeito.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaSuspeito.errors
            return
        }

        try {
            autoEntradaSuspeitoService.save(autoEntradaSuspeito)
        } catch (ValidationException e) {
            respond autoEntradaSuspeito.errors
            return
        }

        respond autoEntradaSuspeito, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaSuspeito autoEntradaSuspeito) {
        if (autoEntradaSuspeito == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaSuspeito.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaSuspeito.errors
            return
        }

        try {
            autoEntradaSuspeitoService.save(autoEntradaSuspeito)
        } catch (ValidationException e) {
            respond autoEntradaSuspeito.errors
            return
        }

        respond autoEntradaSuspeito, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaSuspeitoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
