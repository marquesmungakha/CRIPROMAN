package org.informservice.criproman.tipoOrgao

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
class TipoOrgaoController {

    TipoOrgaoService tipoOrgaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tipoOrgaoService.list(params), model:[tipoOrgaoCount: tipoOrgaoService.count()]
    }

    def show(Long id) {
        respond tipoOrgaoService.get(id)
    }

    @Transactional
    def save(TipoOrgao tipoOrgao) {
        if (tipoOrgao == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoOrgao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoOrgao.errors
            return
        }

        try {
            tipoOrgaoService.save(tipoOrgao)
        } catch (ValidationException e) {
            respond tipoOrgao.errors
            return
        }

        respond tipoOrgao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TipoOrgao tipoOrgao) {
        if (tipoOrgao == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoOrgao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoOrgao.errors
            return
        }

        try {
            tipoOrgaoService.save(tipoOrgao)
        } catch (ValidationException e) {
            respond tipoOrgao.errors
            return
        }

        respond tipoOrgao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || tipoOrgaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
