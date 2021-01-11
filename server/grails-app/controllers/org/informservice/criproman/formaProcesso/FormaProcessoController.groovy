package org.informservice.criproman.formaProcesso

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
class FormaProcessoController {

    FormaProcessoService formaProcessoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond formaProcessoService.list(params), model:[formaProcessoCount: formaProcessoService.count()]
    }

    def show(Long id) {
        respond formaProcessoService.get(id)
    }

    @Transactional
    def save(FormaProcesso formaProcesso) {
        if (formaProcesso == null) {
            render status: NOT_FOUND
            return
        }
        if (formaProcesso.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond formaProcesso.errors
            return
        }

        try {
            formaProcessoService.save(formaProcesso)
        } catch (ValidationException e) {
            respond formaProcesso.errors
            return
        }

        respond formaProcesso, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(FormaProcesso formaProcesso) {
        if (formaProcesso == null) {
            render status: NOT_FOUND
            return
        }
        if (formaProcesso.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond formaProcesso.errors
            return
        }

        try {
            formaProcessoService.save(formaProcesso)
        } catch (ValidationException e) {
            respond formaProcesso.errors
            return
        }

        respond formaProcesso, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || formaProcessoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
