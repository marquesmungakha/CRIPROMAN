package org.informservice.criproman.processoInstrucaoPreparatoria

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
class ProcessoInstrucaoPreparatoriaController {

    ProcessoInstrucaoPreparatoriaService processoInstrucaoPreparatoriaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond processoInstrucaoPreparatoriaService.list(params), model:[processoInstrucaoPreparatoriaCount: processoInstrucaoPreparatoriaService.count()]
    }

    def show(Long id) {
        respond processoInstrucaoPreparatoriaService.get(id)
    }

    @Transactional
    def save(ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria) {
        if (processoInstrucaoPreparatoria == null) {
            render status: NOT_FOUND
            return
        }
        if (processoInstrucaoPreparatoria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond processoInstrucaoPreparatoria.errors
            return
        }

        try {
            processoInstrucaoPreparatoriaService.save(processoInstrucaoPreparatoria)
        } catch (ValidationException e) {
            respond processoInstrucaoPreparatoria.errors
            return
        }

        respond processoInstrucaoPreparatoria, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ProcessoInstrucaoPreparatoria processoInstrucaoPreparatoria) {
        if (processoInstrucaoPreparatoria == null) {
            render status: NOT_FOUND
            return
        }
        if (processoInstrucaoPreparatoria.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond processoInstrucaoPreparatoria.errors
            return
        }

        try {
            processoInstrucaoPreparatoriaService.save(processoInstrucaoPreparatoria)
        } catch (ValidationException e) {
            respond processoInstrucaoPreparatoria.errors
            return
        }

        respond processoInstrucaoPreparatoria, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || processoInstrucaoPreparatoriaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
