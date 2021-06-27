package org.informservice.criproman.tipoParecerAuto

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
class TipoParecerAutoController {

    TipoParecerAutoService tipoParecerAutoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tipoParecerAutoService.list(params), model:[tipoParecerAutoCount: tipoParecerAutoService.count()]
    }

    def show(Long id) {
        respond tipoParecerAutoService.get(id)
    }

    @Transactional
    def save(TipoParecerAuto tipoParecerAuto) {
        if (tipoParecerAuto == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoParecerAuto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoParecerAuto.errors
            return
        }

        try {
            tipoParecerAutoService.save(tipoParecerAuto)
        } catch (ValidationException e) {
            respond tipoParecerAuto.errors
            return
        }

        respond tipoParecerAuto, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TipoParecerAuto tipoParecerAuto) {
        if (tipoParecerAuto == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoParecerAuto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoParecerAuto.errors
            return
        }

        try {
            tipoParecerAutoService.save(tipoParecerAuto)
        } catch (ValidationException e) {
            respond tipoParecerAuto.errors
            return
        }

        respond tipoParecerAuto, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || tipoParecerAutoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
