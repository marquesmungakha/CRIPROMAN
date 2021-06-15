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
class AutoEntradaTestemunhaController {

    AutoEntradaTestemunhaService autoEntradaTestemunhaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaTestemunhaService.list(params), model:[autoEntradaTestemunhaCount: autoEntradaTestemunhaService.count()]
    }

    def show(Long id) {
        respond autoEntradaTestemunhaService.get(id)
    }

    @Transactional
    def save(AutoEntradaTestemunha autoEntradaTestemunha) {
        if (autoEntradaTestemunha == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaTestemunha.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaTestemunha.errors
            return
        }

        try {
            autoEntradaTestemunhaService.save(autoEntradaTestemunha)
        } catch (ValidationException e) {
            respond autoEntradaTestemunha.errors
            return
        }

        respond autoEntradaTestemunha, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaTestemunha autoEntradaTestemunha) {
        if (autoEntradaTestemunha == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaTestemunha.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaTestemunha.errors
            return
        }

        try {
            autoEntradaTestemunhaService.save(autoEntradaTestemunha)
        } catch (ValidationException e) {
            respond autoEntradaTestemunha.errors
            return
        }

        respond autoEntradaTestemunha, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaTestemunhaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
