package org.informservice.criproman.apreensao

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
class ApreensaoController {

    ApreensaoService apreensaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond apreensaoService.list(params), model:[apreensaoCount: apreensaoService.count()]
    }

    def show(Long id) {
        respond apreensaoService.get(id)
    }

    @Transactional
    def save(Apreensao apreensao) {
        if (apreensao == null) {
            render status: NOT_FOUND
            return
        }
        if (apreensao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond apreensao.errors
            return
        }

        try {
            apreensaoService.save(apreensao)
        } catch (ValidationException e) {
            respond apreensao.errors
            return
        }

        respond apreensao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Apreensao apreensao) {
        if (apreensao == null) {
            render status: NOT_FOUND
            return
        }
        if (apreensao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond apreensao.errors
            return
        }

        try {
            apreensaoService.save(apreensao)
        } catch (ValidationException e) {
            respond apreensao.errors
            return
        }

        respond apreensao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || apreensaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
