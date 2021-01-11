package org.informservice.criproman.distibuicaoAdministrativa

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
class DistritoUrbanoController {

    DistritoUrbanoService distritoUrbanoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond distritoUrbanoService.list(params), model:[distritoUrbanoCount: distritoUrbanoService.count()]
    }

    def show(Long id) {
        respond distritoUrbanoService.get(id)
    }

    @Transactional
    def save(DistritoUrbano distritoUrbano) {
        if (distritoUrbano == null) {
            render status: NOT_FOUND
            return
        }
        if (distritoUrbano.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond distritoUrbano.errors
            return
        }

        try {
            distritoUrbanoService.save(distritoUrbano)
        } catch (ValidationException e) {
            respond distritoUrbano.errors
            return
        }

        respond distritoUrbano, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(DistritoUrbano distritoUrbano) {
        if (distritoUrbano == null) {
            render status: NOT_FOUND
            return
        }
        if (distritoUrbano.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond distritoUrbano.errors
            return
        }

        try {
            distritoUrbanoService.save(distritoUrbano)
        } catch (ValidationException e) {
            respond distritoUrbano.errors
            return
        }

        respond distritoUrbano, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || distritoUrbanoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
