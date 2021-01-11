package org.informservice.criproman.seguranca

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
class SecRoleController {

    SecRoleService secRoleService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond secRoleService.list(params), model:[secRoleCount: secRoleService.count()]
    }

    def show(Long id) {
        respond secRoleService.get(id)
    }

    @Transactional
    def save(SecRole secRole) {
        if (secRole == null) {
            render status: NOT_FOUND
            return
        }
        if (secRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secRole.errors
            return
        }

        try {
            secRoleService.save(secRole)
        } catch (ValidationException e) {
            respond secRole.errors
            return
        }

        respond secRole, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(SecRole secRole) {
        if (secRole == null) {
            render status: NOT_FOUND
            return
        }
        if (secRole.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond secRole.errors
            return
        }

        try {
            secRoleService.save(secRole)
        } catch (ValidationException e) {
            respond secRole.errors
            return
        }

        respond secRole, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || secRoleService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
