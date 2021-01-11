package org.informservice.criproman.motivoDetencao

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
class MotivoDetencaoController {

    MotivoDetencaoService motivoDetencaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond motivoDetencaoService.list(params), model:[motivoDetencaoCount: motivoDetencaoService.count()]
    }

    def show(Long id) {
        respond motivoDetencaoService.get(id)
    }

    @Transactional
    def save(MotivoDetencao motivoDetencao) {
        if (motivoDetencao == null) {
            render status: NOT_FOUND
            return
        }
        if (motivoDetencao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond motivoDetencao.errors
            return
        }

        try {
            motivoDetencaoService.save(motivoDetencao)
        } catch (ValidationException e) {
            respond motivoDetencao.errors
            return
        }

        respond motivoDetencao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MotivoDetencao motivoDetencao) {
        if (motivoDetencao == null) {
            render status: NOT_FOUND
            return
        }
        if (motivoDetencao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond motivoDetencao.errors
            return
        }

        try {
            motivoDetencaoService.save(motivoDetencao)
        } catch (ValidationException e) {
            respond motivoDetencao.errors
            return
        }

        respond motivoDetencao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || motivoDetencaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
