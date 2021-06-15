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
class AutoEntradaVitimaController {

    AutoEntradaVitimaService autoEntradaVitimaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaVitimaService.list(params), model:[autoEntradaVitimaCount: autoEntradaVitimaService.count()]
    }

    def show(Long id) {
        respond autoEntradaVitimaService.get(id)
    }

    @Transactional
    def save(AutoEntradaVitima autoEntradaVitima) {
        if (autoEntradaVitima == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaVitima.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaVitima.errors
            return
        }

        try {
            autoEntradaVitimaService.save(autoEntradaVitima)
        } catch (ValidationException e) {
            respond autoEntradaVitima.errors
            return
        }

        respond autoEntradaVitima, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaVitima autoEntradaVitima) {
        if (autoEntradaVitima == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaVitima.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaVitima.errors
            return
        }

        try {
            autoEntradaVitimaService.save(autoEntradaVitima)
        } catch (ValidationException e) {
            respond autoEntradaVitima.errors
            return
        }

        respond autoEntradaVitima, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaVitimaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
