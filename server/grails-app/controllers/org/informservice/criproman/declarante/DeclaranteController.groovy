package org.informservice.criproman.declarante

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
class DeclaranteController {

    DeclaranteService declaranteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond declaranteService.list(params), model:[declaranteCount: declaranteService.count()]
    }

    def show(Long id) {
        respond declaranteService.get(id)
    }

    @Transactional
    def save(Declarante declarante) {
        if (declarante == null) {
            render status: NOT_FOUND
            return
        }
        if (declarante.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond declarante.errors
            return
        }

        try {
            declaranteService.save(declarante)
        } catch (ValidationException e) {
            respond declarante.errors
            return
        }

        respond declarante, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Declarante declarante) {
        if (declarante == null) {
            render status: NOT_FOUND
            return
        }
        if (declarante.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond declarante.errors
            return
        }

        try {
            declaranteService.save(declarante)
        } catch (ValidationException e) {
            respond declarante.errors
            return
        }

        respond declarante, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || declaranteService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
