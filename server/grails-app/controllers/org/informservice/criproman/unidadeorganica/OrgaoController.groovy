package org.informservice.criproman.unidadeorganica

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.informservice.criproman.orgao.Orgao
import org.informservice.criproman.orgao.OrgaoService

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
@Secured('ROLE_ADMIN')
class OrgaoController {

    OrgaoService orgaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond orgaoService.list(params), model:[orgaoCount: orgaoService.count()]
    }

    def show(Long id) {
        respond orgaoService.get(id)
    }

    @Transactional
    def save(Orgao orgao) {
        if (orgao == null) {
            render status: NOT_FOUND
            return
        }
        if (orgao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orgao.errors
            return
        }

        try {
            orgaoService.save(orgao)
        } catch (ValidationException e) {
            respond orgao.errors
            return
        }

        respond orgao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Orgao orgao) {
        if (orgao == null) {
            render status: NOT_FOUND
            return
        }
        if (orgao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond orgao.errors
            return
        }

        try {
            orgaoService.save(orgao)
        } catch (ValidationException e) {
            respond orgao.errors
            return
        }

        respond orgao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || orgaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
