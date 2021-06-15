package org.informservice.criproman.autoEntrada

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class AutoEntradaDenuncianteController {

    AutoEntradaDenuncianteService autoEntradaDenuncianteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond autoEntradaDenuncianteService.list(params), model:[autoEntradaDenuncianteCount: autoEntradaDenuncianteService.count()]
    }

    def show(Long id) {
        respond autoEntradaDenuncianteService.get(id)
    }

    @Transactional
    def save(AutoEntradaDenunciante autoEntradaDenunciante) {
        if (autoEntradaDenunciante == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaDenunciante.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaDenunciante.errors
            return
        }

        try {
            autoEntradaDenuncianteService.save(autoEntradaDenunciante)
        } catch (ValidationException e) {
            respond autoEntradaDenunciante.errors
            return
        }

        respond autoEntradaDenunciante, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(AutoEntradaDenunciante autoEntradaDenunciante) {
        if (autoEntradaDenunciante == null) {
            render status: NOT_FOUND
            return
        }
        if (autoEntradaDenunciante.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond autoEntradaDenunciante.errors
            return
        }

        try {
            autoEntradaDenuncianteService.save(autoEntradaDenunciante)
        } catch (ValidationException e) {
            respond autoEntradaDenunciante.errors
            return
        }

        respond autoEntradaDenunciante, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || autoEntradaDenuncianteService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
