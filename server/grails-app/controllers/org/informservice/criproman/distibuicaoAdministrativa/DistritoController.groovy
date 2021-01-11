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
class DistritoController {

    DistritoService distritoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond distritoService.list(params), model:[distritoCount: distritoService.count()]
    }

    def show(Long id) {
        respond distritoService.get(id)
    }

    @Transactional
    def save(Distrito distrito) {
        if (distrito == null) {
            render status: NOT_FOUND
            return
        }
        if (distrito.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond distrito.errors
            return
        }

        try {
            distritoService.save(distrito)
        } catch (ValidationException e) {
            respond distrito.errors
            return
        }

        respond distrito, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Distrito distrito) {
        if (distrito == null) {
            render status: NOT_FOUND
            return
        }
        if (distrito.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond distrito.errors
            return
        }

        try {
            distritoService.save(distrito)
        } catch (ValidationException e) {
            respond distrito.errors
            return
        }

        respond distrito, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || distritoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
