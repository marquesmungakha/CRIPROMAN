package org.informservice.criproman.modelo

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
class ModeloController {

    ModeloService modeloService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond modeloService.list(params), model:[modeloCount: modeloService.count()]
    }

    def show(Long id) {
        respond modeloService.get(id)
    }

    @Transactional
    def save(Modelo modelo) {
        if (modelo == null) {
            render status: NOT_FOUND
            return
        }
        if (modelo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond modelo.errors
            return
        }

        try {
            modeloService.save(modelo)
        } catch (ValidationException e) {
            respond modelo.errors
            return
        }

        respond modelo, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Modelo modelo) {
        if (modelo == null) {
            render status: NOT_FOUND
            return
        }
        if (modelo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond modelo.errors
            return
        }

        try {
            modeloService.save(modelo)
        } catch (ValidationException e) {
            respond modelo.errors
            return
        }

        respond modelo, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || modeloService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
