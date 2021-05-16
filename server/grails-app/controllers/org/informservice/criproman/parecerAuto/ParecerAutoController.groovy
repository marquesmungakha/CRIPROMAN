package org.informservice.criproman.parecerAuto

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.NOT_FOUND
import static org.springframework.http.HttpStatus.NO_CONTENT
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY

import grails.gorm.transactions.ReadOnly
import grails.gorm.transactions.Transactional

@ReadOnly
class ParecerAutoController {

    ParecerAutoService parecerAutoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond parecerAutoService.list(params), model:[parecerAutoCount: parecerAutoService.count()]
    }

    def show(Long id) {
        respond parecerAutoService.get(id)
    }

    @Transactional
    def save(ParecerAuto parecerAuto) {
        if (parecerAuto == null) {
            render status: NOT_FOUND
            return
        }
        if (parecerAuto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parecerAuto.errors
            return
        }

        try {
            parecerAutoService.save(parecerAuto)
        } catch (ValidationException e) {
            respond parecerAuto.errors
            return
        }

        respond parecerAuto, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ParecerAuto parecerAuto) {
        if (parecerAuto == null) {
            render status: NOT_FOUND
            return
        }
        if (parecerAuto.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parecerAuto.errors
            return
        }

        try {
            parecerAutoService.save(parecerAuto)
        } catch (ValidationException e) {
            respond parecerAuto.errors
            return
        }

        respond parecerAuto, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || parecerAutoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
