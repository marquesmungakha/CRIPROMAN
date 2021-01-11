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
class BairroController {

    BairroService bairroService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bairroService.list(params), model:[bairroCount: bairroService.count()]
    }

    def show(Long id) {
        respond bairroService.get(id)
    }

    @Transactional
    def save(Bairro bairro) {
        if (bairro == null) {
            render status: NOT_FOUND
            return
        }
        if (bairro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bairro.errors
            return
        }

        try {
            bairroService.save(bairro)
        } catch (ValidationException e) {
            respond bairro.errors
            return
        }

        respond bairro, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Bairro bairro) {
        if (bairro == null) {
            render status: NOT_FOUND
            return
        }
        if (bairro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bairro.errors
            return
        }

        try {
            bairroService.save(bairro)
        } catch (ValidationException e) {
            respond bairro.errors
            return
        }

        respond bairro, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || bairroService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
