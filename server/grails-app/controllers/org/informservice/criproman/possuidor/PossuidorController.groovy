package org.informservice.criproman.possuidor

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
class PossuidorController {

    PossuidorService possuidorService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond possuidorService.list(params), model:[possuidorCount: possuidorService.count()]
    }

    def show(Long id) {
        respond possuidorService.get(id)
    }

    @Transactional
    def save(Possuidor possuidor) {
        if (possuidor == null) {
            render status: NOT_FOUND
            return
        }
        if (possuidor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond possuidor.errors
            return
        }

        try {
            possuidorService.save(possuidor)
        } catch (ValidationException e) {
            respond possuidor.errors
            return
        }

        respond possuidor, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Possuidor possuidor) {
        if (possuidor == null) {
            render status: NOT_FOUND
            return
        }
        if (possuidor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond possuidor.errors
            return
        }

        try {
            possuidorService.save(possuidor)
        } catch (ValidationException e) {
            respond possuidor.errors
            return
        }

        respond possuidor, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || possuidorService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
