package org.informservice.criproman.distibuicaoAdministrativa

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
class PostoAdministrativoController {

    PostoAdministrativoService postoAdministrativoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond postoAdministrativoService.list(params), model:[postoAdministrativoCount: postoAdministrativoService.count()]
    }

    def show(Long id) {
        respond postoAdministrativoService.get(id)
    }

    @Transactional
    def save(PostoAdministrativo postoAdministrativo) {
        if (postoAdministrativo == null) {
            render status: NOT_FOUND
            return
        }
        if (postoAdministrativo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond postoAdministrativo.errors
            return
        }

        try {
            postoAdministrativoService.save(postoAdministrativo)
        } catch (ValidationException e) {
            respond postoAdministrativo.errors
            return
        }

        respond postoAdministrativo, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(PostoAdministrativo postoAdministrativo) {
        if (postoAdministrativo == null) {
            render status: NOT_FOUND
            return
        }
        if (postoAdministrativo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond postoAdministrativo.errors
            return
        }

        try {
            postoAdministrativoService.save(postoAdministrativo)
        } catch (ValidationException e) {
            respond postoAdministrativo.errors
            return
        }

        respond postoAdministrativo, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || postoAdministrativoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
