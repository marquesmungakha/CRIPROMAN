package org.informservice.criproman.magistrado

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
class MagistradoController {

    MagistradoService magistradoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond magistradoService.list(params), model:[magistradoCount: magistradoService.count()]
    }

    def show(Long id) {
        respond magistradoService.get(id)
    }

    @Transactional
    def save(Magistrado magistrado) {
        if (magistrado == null) {
            render status: NOT_FOUND
            return
        }
        if (magistrado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond magistrado.errors
            return
        }

        try {
            magistradoService.save(magistrado)
        } catch (ValidationException e) {
            respond magistrado.errors
            return
        }

        respond magistrado, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Magistrado magistrado) {
        if (magistrado == null) {
            render status: NOT_FOUND
            return
        }
        if (magistrado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond magistrado.errors
            return
        }

        try {
            magistradoService.save(magistrado)
        } catch (ValidationException e) {
            respond magistrado.errors
            return
        }

        respond magistrado, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || magistradoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
