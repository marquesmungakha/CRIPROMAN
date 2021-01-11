package org.informservice.criproman.situacaoPrisional

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
class SituacaoPrisionalController {

    SituacaoPrisionalService situacaoPrisionalService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond situacaoPrisionalService.list(params), model:[situacaoPrisionalCount: situacaoPrisionalService.count()]
    }

    def show(Long id) {
        respond situacaoPrisionalService.get(id)
    }

    @Transactional
    def save(SituacaoPrisional situacaoPrisional) {
        if (situacaoPrisional == null) {
            render status: NOT_FOUND
            return
        }
        if (situacaoPrisional.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond situacaoPrisional.errors
            return
        }

        try {
            situacaoPrisionalService.save(situacaoPrisional)
        } catch (ValidationException e) {
            respond situacaoPrisional.errors
            return
        }

        respond situacaoPrisional, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SituacaoPrisional situacaoPrisional) {
        if (situacaoPrisional == null) {
            render status: NOT_FOUND
            return
        }
        if (situacaoPrisional.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond situacaoPrisional.errors
            return
        }

        try {
            situacaoPrisionalService.save(situacaoPrisional)
        } catch (ValidationException e) {
            respond situacaoPrisional.errors
            return
        }

        respond situacaoPrisional, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || situacaoPrisionalService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
