package org.informservice.criproman.marca

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
class MarcaController {

    MarcaService marcaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond marcaService.list(params), model:[marcaCount: marcaService.count()]
    }

    def show(Long id) {
        respond marcaService.get(id)
    }

    @Transactional
    def save(Marca marca) {
        if (marca == null) {
            render status: NOT_FOUND
            return
        }
        if (marca.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond marca.errors
            return
        }

        try {
            marcaService.save(marca)
        } catch (ValidationException e) {
            respond marca.errors
            return
        }

        respond marca, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Marca marca) {
        if (marca == null) {
            render status: NOT_FOUND
            return
        }
        if (marca.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond marca.errors
            return
        }

        try {
            marcaService.save(marca)
        } catch (ValidationException e) {
            respond marca.errors
            return
        }

        respond marca, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || marcaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
