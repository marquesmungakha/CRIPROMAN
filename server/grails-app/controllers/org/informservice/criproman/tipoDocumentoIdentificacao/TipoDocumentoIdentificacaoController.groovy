package org.informservice.criproman.tipoDocumentoIdentificacao

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
class TipoDocumentoIdentificacaoController {

    TipoDocumentoIdentificacaoService tipoDocumentoIdentificacaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tipoDocumentoIdentificacaoService.list(params), model:[tipoDocumentoIdentificacaoCount: tipoDocumentoIdentificacaoService.count()]
    }

    def show(Long id) {
        respond tipoDocumentoIdentificacaoService.get(id)
    }

    @Transactional
    def save(TipoDocumentoIdentificacao tipoDocumentoIdentificacao) {
        if (tipoDocumentoIdentificacao == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoDocumentoIdentificacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDocumentoIdentificacao.errors
            return
        }

        try {
            tipoDocumentoIdentificacaoService.save(tipoDocumentoIdentificacao)
        } catch (ValidationException e) {
            respond tipoDocumentoIdentificacao.errors
            return
        }

        respond tipoDocumentoIdentificacao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TipoDocumentoIdentificacao tipoDocumentoIdentificacao) {
        if (tipoDocumentoIdentificacao == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoDocumentoIdentificacao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDocumentoIdentificacao.errors
            return
        }

        try {
            tipoDocumentoIdentificacaoService.save(tipoDocumentoIdentificacao)
        } catch (ValidationException e) {
            respond tipoDocumentoIdentificacao.errors
            return
        }

        respond tipoDocumentoIdentificacao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || tipoDocumentoIdentificacaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
