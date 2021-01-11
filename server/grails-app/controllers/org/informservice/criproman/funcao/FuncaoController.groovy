package org.informservice.criproman.funcao

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
class FuncaoController {

    FuncaoService funcaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond funcaoService.list(params), model:[funcaoCount: funcaoService.count()]
    }

    def show(Long id) {
        respond funcaoService.get(id)
    }

    @Transactional
    def save(Funcao funcao) {
        if (funcao == null) {
            render status: NOT_FOUND
            return
        }
        if (funcao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond funcao.errors
            return
        }

        try {
            funcaoService.save(funcao)
        } catch (ValidationException e) {
            respond funcao.errors
            return
        }

        respond funcao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Funcao funcao) {
        if (funcao == null) {
            render status: NOT_FOUND
            return
        }
        if (funcao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond funcao.errors
            return
        }

        try {
            funcaoService.save(funcao)
        } catch (ValidationException e) {
            respond funcao.errors
            return
        }

        respond funcao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || funcaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
