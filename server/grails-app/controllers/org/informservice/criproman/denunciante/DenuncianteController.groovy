package org.informservice.criproman.denunciante

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
class DenuncianteController {

    DenuncianteService denuncianteService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond denuncianteService.list(params), model:[denuncianteCount: denuncianteService.count()]
    }

    def show(Long id) {
        respond denuncianteService.get(id)
    }

    @Transactional
    def save(Denunciante denunciante) {
        if (denunciante == null) {
            render status: NOT_FOUND
            return
        }
        if (denunciante.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond denunciante.errors
            return
        }

        try {
            denuncianteService.save(denunciante)
        } catch (ValidationException e) {
            respond denunciante.errors
            return
        }

        respond denunciante, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Denunciante denunciante) {
        if (denunciante == null) {
            render status: NOT_FOUND
            return
        }
        if (denunciante.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond denunciante.errors
            return
        }

        try {
            denuncianteService.save(denunciante)
        } catch (ValidationException e) {
            respond denunciante.errors
            return
        }

        respond denunciante, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || denuncianteService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
