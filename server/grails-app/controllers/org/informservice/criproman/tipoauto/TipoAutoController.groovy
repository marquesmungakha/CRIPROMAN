package org.informservice.criproman.tipoauto

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
class TipoAutoController {

    TipoAutoService tipoAutoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tipoAutoService.list(params), model:[tipoAutoCount: tipoAutoService.count()]
    }

    def show(Long id) {
        respond tipoAutoService.get(id)
    }

    @Transactional
    def save(TipoAuto tipoAuto) {
        if (tipoAuto == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoAuto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoAuto.errors
            return
        }

        try {
            tipoAutoService.save(tipoAuto)
        } catch (ValidationException e) {
            respond tipoAuto.errors
            return
        }

        respond tipoAuto, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TipoAuto tipoAuto) {
        if (tipoAuto == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoAuto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoAuto.errors
            return
        }

        try {
            tipoAutoService.save(tipoAuto)
        } catch (ValidationException e) {
            respond tipoAuto.errors
            return
        }

        respond tipoAuto, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || tipoAutoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
