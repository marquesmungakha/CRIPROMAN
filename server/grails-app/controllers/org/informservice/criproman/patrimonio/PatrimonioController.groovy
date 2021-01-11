package org.informservice.criproman.patrimonio

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
class PatrimonioController {

    PatrimonioService patrimonioService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond patrimonioService.list(params), model:[patrimonioCount: patrimonioService.count()]
    }

    def show(Long id) {
        respond patrimonioService.get(id)
    }

    @Transactional
    def save(Patrimonio patrimonio) {
        if (patrimonio == null) {
            render status: NOT_FOUND
            return
        }
        if (patrimonio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patrimonio.errors
            return
        }

        try {
            patrimonioService.save(patrimonio)
        } catch (ValidationException e) {
            respond patrimonio.errors
            return
        }

        respond patrimonio, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Patrimonio patrimonio) {
        if (patrimonio == null) {
            render status: NOT_FOUND
            return
        }
        if (patrimonio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond patrimonio.errors
            return
        }

        try {
            patrimonioService.save(patrimonio)
        } catch (ValidationException e) {
            respond patrimonio.errors
            return
        }

        respond patrimonio, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || patrimonioService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
