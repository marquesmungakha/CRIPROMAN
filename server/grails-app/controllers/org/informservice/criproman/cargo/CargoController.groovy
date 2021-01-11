package org.informservice.criproman.cargo

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
class CargoController {

    CargoService cargoService

    static responseFormats = ['json', 'xml']
    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond cargoService.list(params), model:[cargoCount: cargoService.count()]
    }

    def show(Long id) {
        respond cargoService.get(id)
    }

    @Transactional
    def save(Cargo cargo) {
        if (cargo == null) {
            render status: NOT_FOUND
            return
        }
        if (cargo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cargo.errors
            return
        }

        try {
            cargoService.save(cargo)
        } catch (ValidationException e) {
            respond cargo.errors
            return
        }

        respond cargo, [status: CREATED, view:"show"]
    }

    @Transactional
    def update(Cargo cargo) {
        if (cargo == null) {
            render status: NOT_FOUND
            return
        }
        if (cargo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond cargo.errors
            return
        }

        try {
            cargoService.save(cargo)
        } catch (ValidationException e) {
            respond cargo.errors
            return
        }

        respond cargo, [status: OK, view:"show"]
    }

    @Transactional
    def delete(Long id) {
        if (id == null || cargoService.delete(id) == null) {
            render status: NOT_FOUND
            return
        }

        render status: NO_CONTENT
    }
}
