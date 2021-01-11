package org.informservice.criproman.quadro

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
class QuadroController {

    QuadroService quadroService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond quadroService.list(params), model:[quadroCount: quadroService.count()]
    }

    def show(Long id) {
        respond quadroService.get(id)
    }

    @Transactional
    def save(Quadro quadro) {
        if (quadro == null) {
            render status: NOT_FOUND
            return
        }
        if (quadro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond quadro.errors
            return
        }

        try {
            quadroService.save(quadro)
        } catch (ValidationException e) {
            respond quadro.errors
            return
        }

        respond quadro, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Quadro quadro) {
        if (quadro == null) {
            render status: NOT_FOUND
            return
        }
        if (quadro.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond quadro.errors
            return
        }

        try {
            quadroService.save(quadro)
        } catch (ValidationException e) {
            respond quadro.errors
            return
        }

        respond quadro, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || quadroService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
