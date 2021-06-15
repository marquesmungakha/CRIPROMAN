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
class AutoEntradaDeclaranteController {

    AutoEntradaDeclaranteService autoEntradaDeclaranteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaDeclaranteService.list(params), model:[autoEntradaDeclaranteCount: autoEntradaDeclaranteService.count()]
    }

    def show(Long id) {
        respond autoEntradaDeclaranteService.get(id)
    }

    @Transactional
    def save(AutoEntradaDeclarante autoEntradaDeclarante) {
        if (autoEntradaDeclarante == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaDeclarante.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaDeclarante.errors
            return
        }

        try {
            autoEntradaDeclaranteService.save(autoEntradaDeclarante)
        } catch (ValidationException e) {
            respond autoEntradaDeclarante.errors
            return
        }

        respond autoEntradaDeclarante, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaDeclarante autoEntradaDeclarante) {
        if (autoEntradaDeclarante == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaDeclarante.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaDeclarante.errors
            return
        }

        try {
            autoEntradaDeclaranteService.save(autoEntradaDeclarante)
        } catch (ValidationException e) {
            respond autoEntradaDeclarante.errors
            return
        }

        respond autoEntradaDeclarante, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaDeclaranteService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
