package org.informservice.criproman.pergunta

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
class PerguntaController {

    PerguntaService perguntaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond perguntaService.list(params), model:[perguntaCount: perguntaService.count()]
    }

    def show(Long id) {
        respond perguntaService.get(id)
    }

    @Transactional
    def save(Pergunta pergunta) {
        if (pergunta == null) {
            render status: NOT_FOUND
            return
        }
        if (pergunta.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pergunta.errors
            return
        }

        try {
            perguntaService.save(pergunta)
        } catch (ValidationException e) {
            respond pergunta.errors
            return
        }

        respond pergunta, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Pergunta pergunta) {
        if (pergunta == null) {
            render status: NOT_FOUND
            return
        }
        if (pergunta.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pergunta.errors
            return
        }

        try {
            perguntaService.save(pergunta)
        } catch (ValidationException e) {
            respond pergunta.errors
            return
        }

        respond pergunta, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || perguntaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
