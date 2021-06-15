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
class AutoEntradaPossuidorController {

    AutoEntradaPossuidorService autoEntradaPossuidorService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaPossuidorService.list(params), model:[autoEntradaPossuidorCount: autoEntradaPossuidorService.count()]
    }

    def show(Long id) {
        respond autoEntradaPossuidorService.get(id)
    }

    @Transactional
    def save(AutoEntradaPossuidor autoEntradaPossuidor) {
        if (autoEntradaPossuidor == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaPossuidor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaPossuidor.errors
            return
        }

        try {
            autoEntradaPossuidorService.save(autoEntradaPossuidor)
        } catch (ValidationException e) {
            respond autoEntradaPossuidor.errors
            return
        }

        respond autoEntradaPossuidor, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaPossuidor autoEntradaPossuidor) {
        if (autoEntradaPossuidor == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaPossuidor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaPossuidor.errors
            return
        }

        try {
            autoEntradaPossuidorService.save(autoEntradaPossuidor)
        } catch (ValidationException e) {
            respond autoEntradaPossuidor.errors
            return
        }

        respond autoEntradaPossuidor, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaPossuidorService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
