package org.informservice.criproman.profissao

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
class ProfissaoController {

    ProfissaoService profissaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond profissaoService.list(params), model:[profissaoCount: profissaoService.count()]
    }

    def show(Long id) {
        respond profissaoService.get(id)
    }

    @Transactional
    def save(Profissao profissao) {
        if (profissao == null) {
            render status: NOT_FOUND
            return
        }
        if (profissao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond profissao.errors
            return
        }

        try {
            profissaoService.save(profissao)
        } catch (ValidationException e) {
            respond profissao.errors
            return
        }

        respond profissao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Profissao profissao) {
        if (profissao == null) {
            render status: NOT_FOUND
            return
        }
        if (profissao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond profissao.errors
            return
        }

        try {
            profissaoService.save(profissao)
        } catch (ValidationException e) {
            respond profissao.errors
            return
        }

        respond profissao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || profissaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
