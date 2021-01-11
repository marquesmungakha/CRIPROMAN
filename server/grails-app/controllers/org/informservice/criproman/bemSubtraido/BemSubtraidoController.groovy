package org.informservice.criproman.bemSubtraido

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
class BemSubtraidoController {

    BemSubtraidoService bemSubtraidoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bemSubtraidoService.list(params), model:[bemSubtraidoCount: bemSubtraidoService.count()]
    }

    def show(Long id) {
        respond bemSubtraidoService.get(id)
    }

    @Transactional
    def save(BemSubtraido bemSubtraido) {
        if (bemSubtraido == null) {
            render status: NOT_FOUND
            return
        }
        if (bemSubtraido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bemSubtraido.errors
            return
        }

        try {
            bemSubtraidoService.save(bemSubtraido)
        } catch (ValidationException e) {
            respond bemSubtraido.errors
            return
        }

        respond bemSubtraido, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(BemSubtraido bemSubtraido) {
        if (bemSubtraido == null) {
            render status: NOT_FOUND
            return
        }
        if (bemSubtraido.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bemSubtraido.errors
            return
        }

        try {
            bemSubtraidoService.save(bemSubtraido)
        } catch (ValidationException e) {
            respond bemSubtraido.errors
            return
        }

        respond bemSubtraido, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || bemSubtraidoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
