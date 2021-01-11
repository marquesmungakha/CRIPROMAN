package org.informservice.criproman.autoEntrada

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
class AutoEntradaController {

    AutoEntradaService autoEntradaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaService.list(params), model:[autoEntradaCount: autoEntradaService.count()]
    }

    def show(Long id) {
        respond autoEntradaService.get(id)
    }

    @Transactional
    def save(AutoEntrada autoEntrada) {
        if (autoEntrada == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntrada.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntrada.errors
            return
        }

        try {
            autoEntradaService.save(autoEntrada)
        } catch (ValidationException e) {
            respond autoEntrada.errors
            return
        }

        respond autoEntrada, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntrada autoEntrada) {
        if (autoEntrada == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntrada.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntrada.errors
            return
        }

        try {
            autoEntradaService.save(autoEntrada)
        } catch (ValidationException e) {
            respond autoEntrada.errors
            return
        }

        respond autoEntrada, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
