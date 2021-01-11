package org.informservice.criproman.individuo

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
class IndividuoController {

    IndividuoService individuoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond individuoService.list(params), model:[individuoCount: individuoService.count()]
    }

    def show(Long id) {
        respond individuoService.get(id)
    }

    @Transactional
    def save(Individuo individuo) {
        if (individuo == null) {
            render status: NOT_FOUND
            return
        }
        if (individuo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond individuo.errors
            return
        }

        try {
            individuoService.save(individuo)
        } catch (ValidationException e) {
            respond individuo.errors
            return
        }

        respond individuo, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Individuo individuo) {
        if (individuo == null) {
            render status: NOT_FOUND
            return
        }
        if (individuo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond individuo.errors
            return
        }

        try {
            individuoService.save(individuo)
        } catch (ValidationException e) {
            respond individuo.errors
            return
        }

        respond individuo, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || individuoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
