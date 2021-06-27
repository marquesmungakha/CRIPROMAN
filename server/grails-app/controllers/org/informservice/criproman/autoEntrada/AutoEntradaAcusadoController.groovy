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
class AutoEntradaAcusadoController {

    AutoEntradaAcusadoService autoEntradaAcusadoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaAcusadoService.list(params), model:[autoEntradaAcusadoCount: autoEntradaAcusadoService.count()]
    }

    def show(Long id) {
        respond autoEntradaAcusadoService.get(id)
    }

    @Transactional
    def save(AutoEntradaAcusado autoEntradaAcusado) {
        if (autoEntradaAcusado == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaAcusado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaAcusado.errors
            return
        }

        try {
            autoEntradaAcusadoService.save(autoEntradaAcusado)
        } catch (ValidationException e) {
            respond autoEntradaAcusado.errors
            return
        }

        respond autoEntradaAcusado, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaAcusado autoEntradaAcusado) {
        if (autoEntradaAcusado == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaAcusado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaAcusado.errors
            return
        }

        try {
            autoEntradaAcusadoService.save(autoEntradaAcusado)
        } catch (ValidationException e) {
            respond autoEntradaAcusado.errors
            return
        }

        respond autoEntradaAcusado, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaAcusadoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
