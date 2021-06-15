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
class AutoEntradaCustodiadoController {

    AutoEntradaCustodiadoService autoEntradaCustodiadoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaCustodiadoService.list(params), model:[autoEntradaCustodiadoCount: autoEntradaCustodiadoService.count()]
    }

    def show(Long id) {
        respond autoEntradaCustodiadoService.get(id)
    }

    @Transactional
    def save(AutoEntradaCustodiado autoEntradaCustodiado) {
        if (autoEntradaCustodiado == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaCustodiado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaCustodiado.errors
            return
        }

        try {
            autoEntradaCustodiadoService.save(autoEntradaCustodiado)
        } catch (ValidationException e) {
            respond autoEntradaCustodiado.errors
            return
        }

        respond autoEntradaCustodiado, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaCustodiado autoEntradaCustodiado) {
        if (autoEntradaCustodiado == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaCustodiado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaCustodiado.errors
            return
        }

        try {
            autoEntradaCustodiadoService.save(autoEntradaCustodiado)
        } catch (ValidationException e) {
            respond autoEntradaCustodiado.errors
            return
        }

        respond autoEntradaCustodiado, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaCustodiadoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
