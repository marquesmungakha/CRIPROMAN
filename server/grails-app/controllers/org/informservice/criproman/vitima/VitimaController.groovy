package org.informservice.criproman.vitima

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
class VitimaController {

    VitimaService vitimaService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond vitimaService.list(params), model:[vitimaCount: vitimaService.count()]
    }

    def show(Long id) {
        respond vitimaService.get(id)
    }

    @Transactional
    def save(Vitima vitima) {
        if (vitima == null) {
            render status: NOT_FOUND
            return
        }
        if (vitima.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vitima.errors
            return
        }

        try {
            vitimaService.save(vitima)
        } catch (ValidationException e) {
            respond vitima.errors
            return
        }

        respond vitima, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Vitima vitima) {
        if (vitima == null) {
            render status: NOT_FOUND
            return
        }
        if (vitima.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond vitima.errors
            return
        }

        try {
            vitimaService.save(vitima)
        } catch (ValidationException e) {
            respond vitima.errors
            return
        }

        respond vitima, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || vitimaService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
