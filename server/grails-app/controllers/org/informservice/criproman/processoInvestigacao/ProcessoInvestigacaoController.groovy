package org.informservice.criproman.processoInvestigacao

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
class ProcessoInvestigacaoController {

    ProcessoInvestigacaoService processoInvestigacaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond processoInvestigacaoService.list(params), model:[processoInvestigacaoCount: processoInvestigacaoService.count()]
    }

    def show(Long id) {
        respond processoInvestigacaoService.get(id)
    }

    @Transactional
    def save(ProcessoInvestigacao processoInvestigacao) {
        if (processoInvestigacao == null) {
            render status: NOT_FOUND
            return
        }
        if (processoInvestigacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond processoInvestigacao.errors
            return
        }

        try {
            processoInvestigacaoService.save(processoInvestigacao)
        } catch (ValidationException e) {
            respond processoInvestigacao.errors
            return
        }

        respond processoInvestigacao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ProcessoInvestigacao processoInvestigacao) {
        if (processoInvestigacao == null) {
            render status: NOT_FOUND
            return
        }
        if (processoInvestigacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond processoInvestigacao.errors
            return
        }

        try {
            processoInvestigacaoService.save(processoInvestigacao)
        } catch (ValidationException e) {
            respond processoInvestigacao.errors
            return
        }

        respond processoInvestigacao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || processoInvestigacaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
