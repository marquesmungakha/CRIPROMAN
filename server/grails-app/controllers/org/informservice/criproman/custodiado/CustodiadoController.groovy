package org.informservice.criproman.custodiado

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
class CustodiadoController {

    CustodiadoService custodiadoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond custodiadoService.list(params), model:[custodiadoCount: custodiadoService.count()]
    }

    def show(Long id) {
        respond custodiadoService.get(id)
    }

    @Transactional
    def save(Custodiado custodiado) {
        if (custodiado == null) {
            render status: NOT_FOUND
            return
        }
        if (custodiado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond custodiado.errors
            return
        }

        try {
            custodiadoService.save(custodiado)
        } catch (ValidationException e) {
            respond custodiado.errors
            return
        }

        respond custodiado, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Custodiado custodiado) {
        if (custodiado == null) {
            render status: NOT_FOUND
            return
        }
        if (custodiado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond custodiado.errors
            return
        }

        try {
            custodiadoService.save(custodiado)
        } catch (ValidationException e) {
            respond custodiado.errors
            return
        }

        respond custodiado, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || custodiadoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
