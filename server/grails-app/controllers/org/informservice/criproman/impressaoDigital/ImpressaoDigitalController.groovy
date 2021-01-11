package org.informservice.criproman.impressaoDigital

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
class ImpressaoDigitalController {

    ImpressaoDigitalService impressaoDigitalService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond impressaoDigitalService.list(params), model:[impressaoDigitalCount: impressaoDigitalService.count()]
    }

    def show(Long id) {
        respond impressaoDigitalService.get(id)
    }

    @Transactional
    def save(ImpressaoDigital impressaoDigital) {
        if (impressaoDigital == null) {
            render status: NOT_FOUND
            return
        }
        if (impressaoDigital.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond impressaoDigital.errors
            return
        }

        try {
            impressaoDigitalService.save(impressaoDigital)
        } catch (ValidationException e) {
            respond impressaoDigital.errors
            return
        }

        respond impressaoDigital, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ImpressaoDigital impressaoDigital) {
        if (impressaoDigital == null) {
            render status: NOT_FOUND
            return
        }
        if (impressaoDigital.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond impressaoDigital.errors
            return
        }

        try {
            impressaoDigitalService.save(impressaoDigital)
        } catch (ValidationException e) {
            respond impressaoDigital.errors
            return
        }

        respond impressaoDigital, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || impressaoDigitalService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
