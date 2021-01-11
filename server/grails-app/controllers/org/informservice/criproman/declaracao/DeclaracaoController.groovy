package org.informservice.criproman.declaracao

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
class DeclaracaoController {

    DeclaracaoService declaracaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond declaracaoService.list(params), model:[declaracaoCount: declaracaoService.count()]
    }

    def show(Long id) {
        respond declaracaoService.get(id)
    }

    @Transactional
    def save(Declaracao declaracao) {
        if (declaracao == null) {
            render status: NOT_FOUND
            return
        }
        if (declaracao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond declaracao.errors
            return
        }

        try {
            declaracaoService.save(declaracao)
        } catch (ValidationException e) {
            respond declaracao.errors
            return
        }

        respond declaracao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Declaracao declaracao) {
        if (declaracao == null) {
            render status: NOT_FOUND
            return
        }
        if (declaracao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond declaracao.errors
            return
        }

        try {
            declaracaoService.save(declaracao)
        } catch (ValidationException e) {
            respond declaracao.errors
            return
        }

        respond declaracao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || declaracaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
