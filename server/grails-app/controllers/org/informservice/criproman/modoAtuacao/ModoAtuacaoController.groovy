package org.informservice.criproman.modoAtuacao

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
class ModoAtuacaoController {

    ModoAtuacaoService modoAtuacaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond modoAtuacaoService.list(params), model:[modoAtuacaoCount: modoAtuacaoService.count()]
    }

    def show(Long id) {
        respond modoAtuacaoService.get(id)
    }

    @Transactional
    def save(ModoAtuacao modoAtuacao) {
        if (modoAtuacao == null) {
            render status: NOT_FOUND
            return
        }
        if (modoAtuacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond modoAtuacao.errors
            return
        }

        try {
            modoAtuacaoService.save(modoAtuacao)
        } catch (ValidationException e) {
            respond modoAtuacao.errors
            return
        }

        respond modoAtuacao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ModoAtuacao modoAtuacao) {
        if (modoAtuacao == null) {
            render status: NOT_FOUND
            return
        }
        if (modoAtuacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond modoAtuacao.errors
            return
        }

        try {
            modoAtuacaoService.save(modoAtuacao)
        } catch (ValidationException e) {
            respond modoAtuacao.errors
            return
        }

        respond modoAtuacao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || modoAtuacaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
