package org.informservice.criproman.parecerProcesso

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
class ParecerProcessoController {

    ParecerProcessoService parecerProcessoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond parecerProcessoService.list(params), model:[parecerProcessoCount: parecerProcessoService.count()]
    }

    def show(Long id) {
        respond parecerProcessoService.get(id)
    }

    @Transactional
    def save(ParecerProcesso parecerProcesso) {
        if (parecerProcesso == null) {
            render status: NOT_FOUND
            return
        }
        if (parecerProcesso.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parecerProcesso.errors
            return
        }

        try {
            parecerProcessoService.save(parecerProcesso)
        } catch (ValidationException e) {
            respond parecerProcesso.errors
            return
        }

        respond parecerProcesso, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(ParecerProcesso parecerProcesso) {
        if (parecerProcesso == null) {
            render status: NOT_FOUND
            return
        }
        if (parecerProcesso.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond parecerProcesso.errors
            return
        }

        try {
            parecerProcessoService.save(parecerProcesso)
        } catch (ValidationException e) {
            respond parecerProcesso.errors
            return
        }

        respond parecerProcesso, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || parecerProcessoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
