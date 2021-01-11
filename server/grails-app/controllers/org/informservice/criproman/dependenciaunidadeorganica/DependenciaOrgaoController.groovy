package org.informservice.criproman.dependenciaunidadeorganica

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
class DependenciaOrgaoController {

    DependenciaOrgaoService dependenciaOrgaoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond dependenciaOrgaoService.list(params), model:[dependenciaOrgaoCount: dependenciaOrgaoService.count()]
    }

    def show(Long id) {
        respond dependenciaOrgaoService.get(id)
    }

    @Transactional
    def save(DependenciaOrgao dependenciaOrgao) {
        if (dependenciaOrgao == null) {
            render status: NOT_FOUND
            return
        }
        if (dependenciaOrgao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dependenciaOrgao.errors
            return
        }

        try {
            dependenciaOrgaoService.save(dependenciaOrgao)
        } catch (ValidationException e) {
            respond dependenciaOrgao.errors
            return
        }

        respond dependenciaOrgao, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(DependenciaOrgao dependenciaOrgao) {
        if (dependenciaOrgao == null) {
            render status: NOT_FOUND
            return
        }
        if (dependenciaOrgao.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond dependenciaOrgao.errors
            return
        }

        try {
            dependenciaOrgaoService.save(dependenciaOrgao)
        } catch (ValidationException e) {
            respond dependenciaOrgao.errors
            return
        }

        respond dependenciaOrgao, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || dependenciaOrgaoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
