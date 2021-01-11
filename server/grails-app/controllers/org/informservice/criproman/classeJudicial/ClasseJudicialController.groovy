package org.informservice.criproman.classeJudicial

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
class ClasseJudicialController {

    ClasseJudicialService classeJudicialService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond classeJudicialService.list(params), model:[classeJudicialCount: classeJudicialService.count()]
    }

    def show(Long id) {
        respond classeJudicialService.get(id)
    }

    @Transactional
    def save(ClasseJudicial classeJudicial) {
        if (classeJudicial == null) {
            render status: NOT_FOUND
            return
        }
        if (classeJudicial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond classeJudicial.errors
            return
        }

        try {
            classeJudicialService.save(classeJudicial)
        } catch (ValidationException e) {
            respond classeJudicial.errors
            return
        }

        respond classeJudicial, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ClasseJudicial classeJudicial) {
        if (classeJudicial == null) {
            render status: NOT_FOUND
            return
        }
        if (classeJudicial.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond classeJudicial.errors
            return
        }

        try {
            classeJudicialService.save(classeJudicial)
        } catch (ValidationException e) {
            respond classeJudicial.errors
            return
        }

        respond classeJudicial, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || classeJudicialService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
