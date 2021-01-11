package org.informservice.criproman.crimes

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
class CrimeController {

    CrimeService crimeService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond crimeService.list(params), model:[crimeCount: crimeService.count()]
    }

    def show(Long id) {
        respond crimeService.get(id)
    }

    @Transactional
    def save(Crime crime) {
        if (crime == null) {
            render status: NOT_FOUND
            return
        }
        if (crime.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond crime.errors
            return
        }

        try {
            crimeService.save(crime)
        } catch (ValidationException e) {
            respond crime.errors
            return
        }

        respond crime, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Crime crime) {
        if (crime == null) {
            render status: NOT_FOUND
            return
        }
        if (crime.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond crime.errors
            return
        }

        try {
            crimeService.save(crime)
        } catch (ValidationException e) {
            respond crime.errors
            return
        }

        respond crime, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || crimeService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
