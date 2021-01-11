package org.informservice.criproman.tipoDedo

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
class TipoDedoController {

    TipoDedoService tipoDedoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond tipoDedoService.list(params), model:[tipoDedoCount: tipoDedoService.count()]
    }

    def show(Long id) {
        respond tipoDedoService.get(id)
    }

    @Transactional
    def save(TipoDedo tipoDedo) {
        if (tipoDedo == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoDedo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDedo.errors
            return
        }

        try {
            tipoDedoService.save(tipoDedo)
        } catch (ValidationException e) {
            respond tipoDedo.errors
            return
        }

        respond tipoDedo, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(TipoDedo tipoDedo) {
        if (tipoDedo == null) {
            render status: NOT_FOUND
            return
        }
        if (tipoDedo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond tipoDedo.errors
            return
        }

        try {
            tipoDedoService.save(tipoDedo)
        } catch (ValidationException e) {
            respond tipoDedo.errors
            return
        }

        respond tipoDedo, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || tipoDedoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
