package org.informservice.criproman.distibuicaoAdministrativa

import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpMethod.*

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
@Secured('ROLE_ADMIN')
class ProvinciaController {

    ProvinciaService provinciaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [index: "GET", save: "POST", update: "PUT", delete: "DELETE"]


    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond provinciaService.list(params), model:[provinciaCount: provinciaService.count()]
    }

    def show(Long id) {
        respond provinciaService.get(id)
    }

    @Transactional
    def save(Provincia provincia) {
        if (provincia == null) {
            render status: NOT_FOUND
            return
        }
        if (provincia.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond provincia.errors
            return
        }

        try {
            provinciaService.save(provincia)
        } catch (ValidationException e) {
            respond provincia.errors
            return
        }

        respond provincia, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Provincia provincia) {
        if (provincia == null) {
            render status: NOT_FOUND
            return
        }
        if (provincia.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond provincia.errors
            return
        }

        try {
            provinciaService.save(provincia)
        } catch (ValidationException e) {
            respond provincia.errors
            return
        }

        respond provincia, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || provinciaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
