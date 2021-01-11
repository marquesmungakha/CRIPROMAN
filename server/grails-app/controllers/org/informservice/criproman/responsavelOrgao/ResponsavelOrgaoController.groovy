package org.informservice.criproman.responsavelOrgao

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
class ResponsavelOrgaoController {

    ResponsavelOrgaoService responsavelOrgaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond responsavelOrgaoService.list(params), model:[responsavelOrgaoCount: responsavelOrgaoService.count()]
    }

    def show(Long id) {
        respond responsavelOrgaoService.get(id)
    }

    @Transactional
    def save(ResponsavelOrgao responsavelOrgao) {
        if (responsavelOrgao == null) {
            render status: NOT_FOUND
            return
        }
        if (responsavelOrgao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond responsavelOrgao.errors
            return
        }

        try {
            responsavelOrgaoService.save(responsavelOrgao)
        } catch (ValidationException e) {
            respond responsavelOrgao.errors
            return
        }

        respond responsavelOrgao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ResponsavelOrgao responsavelOrgao) {
        if (responsavelOrgao == null) {
            render status: NOT_FOUND
            return
        }
        if (responsavelOrgao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond responsavelOrgao.errors
            return
        }

        try {
            responsavelOrgaoService.save(responsavelOrgao)
        } catch (ValidationException e) {
            respond responsavelOrgao.errors
            return
        }

        respond responsavelOrgao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || responsavelOrgaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
