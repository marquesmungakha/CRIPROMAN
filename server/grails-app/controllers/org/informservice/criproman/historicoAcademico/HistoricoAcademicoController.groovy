package org.informservice.criproman.historicoAcademico

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
class HistoricoAcademicoController {

    HistoricoAcademicoService historicoAcademicoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond historicoAcademicoService.list(params), model:[historicoAcademicoCount: historicoAcademicoService.count()]
    }

    def show(Long id) {
        respond historicoAcademicoService.get(id)
    }

    @Transactional
    def save(HistoricoAcademico historicoAcademico) {
        if (historicoAcademico == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoAcademico.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoAcademico.errors
            return
        }

        try {
            historicoAcademicoService.save(historicoAcademico)
        } catch (ValidationException e) {
            respond historicoAcademico.errors
            return
        }

        respond historicoAcademico, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(HistoricoAcademico historicoAcademico) {
        if (historicoAcademico == null) {
            render status: NOT_FOUND
            return
        }
        if (historicoAcademico.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond historicoAcademico.errors
            return
        }

        try {
            historicoAcademicoService.save(historicoAcademico)
        } catch (ValidationException e) {
            respond historicoAcademico.errors
            return
        }

        respond historicoAcademico, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || historicoAcademicoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
