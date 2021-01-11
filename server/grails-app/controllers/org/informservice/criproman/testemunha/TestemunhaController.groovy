package org.informservice.criproman.testemunha

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
class TestemunhaController {

    TestemunhaService testemunhaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond testemunhaService.list(params), model:[testemunhaCount: testemunhaService.count()]
    }

    def show(Long id) {
        respond testemunhaService.get(id)
    }

    @Transactional
    def save(Testemunha testemunha) {
        if (testemunha == null) {
            render status: NOT_FOUND
            return
        }
        if (testemunha.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond testemunha.errors
            return
        }

        try {
            testemunhaService.save(testemunha)
        } catch (ValidationException e) {
            respond testemunha.errors
            return
        }

        respond testemunha, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Testemunha testemunha) {
        if (testemunha == null) {
            render status: NOT_FOUND
            return
        }
        if (testemunha.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond testemunha.errors
            return
        }

        try {
            testemunhaService.save(testemunha)
        } catch (ValidationException e) {
            respond testemunha.errors
            return
        }

        respond testemunha, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || testemunhaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
