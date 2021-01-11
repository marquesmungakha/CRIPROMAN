package org.informservice.criproman.distibuicaoAdministrativa

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
class CidadeController {

    CidadeService cidadeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond cidadeService.list(params), model:[cidadeCount: cidadeService.count()]
    }

    def show(Long id) {
        respond cidadeService.get(id)
    }

    @Transactional
    def save(Cidade cidade) {
        if (cidade == null) {
            render status: NOT_FOUND
            return
        }
        if (cidade.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cidade.errors
            return
        }

        try {
            cidadeService.save(cidade)
        } catch (ValidationException e) {
            respond cidade.errors
            return
        }

        respond cidade, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Cidade cidade) {
        if (cidade == null) {
            render status: NOT_FOUND
            return
        }
        if (cidade.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cidade.errors
            return
        }

        try {
            cidadeService.save(cidade)
        } catch (ValidationException e) {
            respond cidade.errors
            return
        }

        respond cidade, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || cidadeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
