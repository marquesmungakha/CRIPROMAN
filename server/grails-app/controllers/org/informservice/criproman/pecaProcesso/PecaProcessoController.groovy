package org.informservice.criproman.pecaProcesso

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
class PecaProcessoController {

    PecaProcessoService pecaProcessoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond pecaProcessoService.list(params), model:[pecaProcessoCount: pecaProcessoService.count()]
    }

    def show(Long id) {
        respond pecaProcessoService.get(id)
    }

    @Transactional
    def save(PecaProcesso pecaProcesso) {
        if (pecaProcesso == null) {
            render status: NOT_FOUND
            return
        }
        if (pecaProcesso.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pecaProcesso.errors
            return
        }

        try {
            pecaProcessoService.save(pecaProcesso)
        } catch (ValidationException e) {
            respond pecaProcesso.errors
            return
        }

        respond pecaProcesso, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PecaProcesso pecaProcesso) {
        if (pecaProcesso == null) {
            render status: NOT_FOUND
            return
        }
        if (pecaProcesso.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pecaProcesso.errors
            return
        }

        try {
            pecaProcessoService.save(pecaProcesso)
        } catch (ValidationException e) {
            respond pecaProcesso.errors
            return
        }

        respond pecaProcesso, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || pecaProcessoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
