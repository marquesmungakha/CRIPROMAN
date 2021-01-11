package org.informservice.criproman.denuncia

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
class DenunciaController {

    DenunciaService denunciaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond denunciaService.list(params), model:[denunciaCount: denunciaService.count()]
    }

    def show(Long id) {
        respond denunciaService.get(id)
    }

    @Transactional
    def save(Denuncia denuncia) {
        if (denuncia == null) {
            render status: NOT_FOUND
            return
        }
        if (denuncia.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond denuncia.errors
            return
        }

        try {
            denunciaService.save(denuncia)
        } catch (ValidationException e) {
            respond denuncia.errors
            return
        }

        respond denuncia, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Denuncia denuncia) {
        if (denuncia == null) {
            render status: NOT_FOUND
            return
        }
        if (denuncia.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond denuncia.errors
            return
        }

        try {
            denunciaService.save(denuncia)
        } catch (ValidationException e) {
            respond denuncia.errors
            return
        }

        respond denuncia, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || denunciaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
