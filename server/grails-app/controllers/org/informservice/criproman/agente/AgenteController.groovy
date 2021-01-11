package org.informservice.criproman.agente

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
class AgenteController {

    AgenteService agenteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond agenteService.list(params), model:[agenteCount: agenteService.count()]
    }

    def show(Long id) {
        respond agenteService.get(id)
    }

    @Transactional
    def save(Agente agente) {
        if (agente == null) {
            render status: NOT_FOUND
            return
        }
        if (agente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond agente.errors
            return
        }

        try {
            agenteService.save(agente)
        } catch (ValidationException e) {
            respond agente.errors
            return
        }

        respond agente, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Agente agente) {
        if (agente == null) {
            render status: NOT_FOUND
            return
        }
        if (agente.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond agente.errors
            return
        }

        try {
            agenteService.save(agente)
        } catch (ValidationException e) {
            respond agente.errors
            return
        }

        respond agente, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || agenteService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
