package org.informservice.criproman.meioUtilizado

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
class MeioUtilizadoController {

    MeioUtilizadoService meioUtilizadoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond meioUtilizadoService.list(params), model:[meioUtilizadoCount: meioUtilizadoService.count()]
    }

    def show(Long id) {
        respond meioUtilizadoService.get(id)
    }

    @Transactional
    def save(MeioUtilizado meioUtilizado) {
        if (meioUtilizado == null) {
            render status: NOT_FOUND
            return
        }
        if (meioUtilizado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond meioUtilizado.errors
            return
        }

        try {
            meioUtilizadoService.save(meioUtilizado)
        } catch (ValidationException e) {
            respond meioUtilizado.errors
            return
        }

        respond meioUtilizado, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(MeioUtilizado meioUtilizado) {
        if (meioUtilizado == null) {
            render status: NOT_FOUND
            return
        }
        if (meioUtilizado.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond meioUtilizado.errors
            return
        }

        try {
            meioUtilizadoService.save(meioUtilizado)
        } catch (ValidationException e) {
            respond meioUtilizado.errors
            return
        }

        respond meioUtilizado, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || meioUtilizadoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
