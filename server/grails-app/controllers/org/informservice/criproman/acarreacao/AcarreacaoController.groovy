package org.informservice.criproman.acarreacao

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

@ReadOnly
@Secured('ROLE_ADMIN')
class AcarreacaoController {

    AcarreacaoService acarreacaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond acarreacaoService.list(params), model:[acarreacaoCount: acarreacaoService.count()]
    }

    def show(Long id) {
        respond acarreacaoService.get(id)
    }

    @Transactional
    def save(Acarreacao acarreacao) {
        if (acarreacao == null) {
            render status: NOT_FOUND
            return
        }
        if (acarreacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond acarreacao.errors
            return
        }

        try {
            acarreacaoService.save(acarreacao)
        } catch (ValidationException e) {
            respond acarreacao.errors
            return
        }

        respond acarreacao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Acarreacao acarreacao) {
        if (acarreacao == null) {
            render status: NOT_FOUND
            return
        }
        if (acarreacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond acarreacao.errors
            return
        }

        try {
            acarreacaoService.save(acarreacao)
        } catch (ValidationException e) {
            respond acarreacao.errors
            return
        }

        respond acarreacao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || acarreacaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
