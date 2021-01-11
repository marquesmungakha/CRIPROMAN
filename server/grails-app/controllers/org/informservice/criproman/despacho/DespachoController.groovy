package org.informservice.criproman.despacho

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
class DespachoController {

    DespachoService despachoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond despachoService.list(params), model:[despachoCount: despachoService.count()]
    }

    def show(Long id) {
        respond despachoService.get(id)
    }

    @Transactional
    def save(Despacho despacho) {
        if (despacho == null) {
            render status: NOT_FOUND
            return
        }
        if (despacho.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond despacho.errors
            return
        }

        try {
            despachoService.save(despacho)
        } catch (ValidationException e) {
            respond despacho.errors
            return
        }

        respond despacho, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Despacho despacho) {
        if (despacho == null) {
            render status: NOT_FOUND
            return
        }
        if (despacho.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond despacho.errors
            return
        }

        try {
            despachoService.save(despacho)
        } catch (ValidationException e) {
            respond despacho.errors
            return
        }

        respond despacho, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || despachoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
