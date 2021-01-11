package org.informservice.criproman.tipoMeio

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
class TipoMeioController {

    TipoMeioService tipoMeioService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tipoMeioService.list(params), model:[tipoMeioCount: tipoMeioService.count()]
    }

    def show(Long id) {
        respond tipoMeioService.get(id)
    }

    @Transactional
    def save(TipoMeio tipoMeio) {
        if (tipoMeio == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoMeio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoMeio.errors
            return
        }

        try {
            tipoMeioService.save(tipoMeio)
        } catch (ValidationException e) {
            respond tipoMeio.errors
            return
        }

        respond tipoMeio, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TipoMeio tipoMeio) {
        if (tipoMeio == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoMeio.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoMeio.errors
            return
        }

        try {
            tipoMeioService.save(tipoMeio)
        } catch (ValidationException e) {
            respond tipoMeio.errors
            return
        }

        respond tipoMeio, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || tipoMeioService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
