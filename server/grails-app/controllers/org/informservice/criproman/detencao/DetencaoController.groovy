package org.informservice.criproman.detencao

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
class DetencaoController {

    DetencaoService detencaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond detencaoService.list(params), model:[detencaoCount: detencaoService.count()]
    }

    def show(Long id) {
        respond detencaoService.get(id)
    }

    @Transactional
    def save(Detencao detencao) {
        if (detencao == null) {
            render status: NOT_FOUND
            return
        }
        if (detencao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond detencao.errors
            return
        }

        try {
            detencaoService.save(detencao)
        } catch (ValidationException e) {
            respond detencao.errors
            return
        }

        respond detencao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Detencao detencao) {
        if (detencao == null) {
            render status: NOT_FOUND
            return
        }
        if (detencao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond detencao.errors
            return
        }

        try {
            detencaoService.save(detencao)
        } catch (ValidationException e) {
            respond detencao.errors
            return
        }

        respond detencao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || detencaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
