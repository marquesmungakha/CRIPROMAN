package org.informservice.criproman.historicoProfissional

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
class HistoricoProfissionalController {

    HistoricoProfissionalService historicoProfissionalService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond historicoProfissionalService.list(params), model:[historicoProfissionalCount: historicoProfissionalService.count()]
    }

    def show(Long id) {
        respond historicoProfissionalService.get(id)
    }

    @Transactional
    def save(HistoricoProfissional historicoProfissional) {
        if (historicoProfissional == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoProfissional.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoProfissional.errors
            return
        }

        try {
            historicoProfissionalService.save(historicoProfissional)
        } catch (ValidationException e) {
            respond historicoProfissional.errors
            return
        }

        respond historicoProfissional, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(HistoricoProfissional historicoProfissional) {
        if (historicoProfissional == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoProfissional.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoProfissional.errors
            return
        }

        try {
            historicoProfissionalService.save(historicoProfissional)
        } catch (ValidationException e) {
            respond historicoProfissional.errors
            return
        }

        respond historicoProfissional, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || historicoProfissionalService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
