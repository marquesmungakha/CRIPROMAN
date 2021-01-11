package org.informservice.criproman.alocacao

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
class AlocacaoController {

    AlocacaoService alocacaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond alocacaoService.list(params), model:[alocacaoCount: alocacaoService.count()]
    }

    def show(Long id) {
        respond alocacaoService.get(id)
    }

    @Transactional
    def save(Alocacao alocacao) {
        if (alocacao == null) {
            render status: NOT_FOUND
            return
        }
        if (alocacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond alocacao.errors
            return
        }

        try {
            alocacaoService.save(alocacao)
        } catch (ValidationException e) {
            respond alocacao.errors
            return
        }

        respond alocacao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Alocacao alocacao) {
        if (alocacao == null) {
            render status: NOT_FOUND
            return
        }
        if (alocacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond alocacao.errors
            return
        }

        try {
            alocacaoService.save(alocacao)
        } catch (ValidationException e) {
            respond alocacao.errors
            return
        }

        respond alocacao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || alocacaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
